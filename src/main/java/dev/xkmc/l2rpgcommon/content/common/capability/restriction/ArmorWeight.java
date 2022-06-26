package dev.xkmc.l2rpgcommon.content.common.capability.restriction;

import dev.xkmc.l2library.serial.SerialClass;
import dev.xkmc.l2library.serial.network.BaseConfig;
import dev.xkmc.l2library.util.annotation.DataGenOnly;
import dev.xkmc.l2rpgcommon.content.common.capability.player.LLPlayerData;
import dev.xkmc.l2rpgcommon.network.ConfigType;
import dev.xkmc.l2rpgcommon.network.NetworkManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.HashMap;

@SerialClass
public class ArmorWeight extends BaseConfig {

	@Nullable
	public static ArmorWeight getInstance() {
		return NetworkManager.getConfig(ConfigType.ARMOR_WEIGHT);
	}

	public static boolean canPutOn(Player player, ItemStack stack) {
		if (player == null || !LLPlayerData.isProper(player))
			return true;
		ArmorWeight weight = getInstance();
		if (weight == null)
			return true;
		int ans = 0;
		for (EquipmentSlot slot : EquipmentSlot.values()) {
			if (slot.getType() != EquipmentSlot.Type.ARMOR) continue;
			ItemStack armor = player.getItemBySlot(slot);
			if (armor.isEmpty()) continue;
			if (slot == ((ArmorItem) stack.getItem()).getSlot()) continue;
			ans += getWeight(armor);
		}
		ans += getWeight(stack);
		return ans <= LLPlayerData.get(player).abilityPoints.getWeightAble();
	}

	public static int getArmorWeight(Player player) {
		ArmorWeight weight = getInstance();
		if (weight == null)
			return 0;
		int ans = 0;
		for (ItemStack stack : player.getArmorSlots()) {
			ans += getWeight(stack);
		}
		return ans;
	}

	@SerialClass.SerialField
	public final HashMap<String, Entry> entries = new HashMap<>();

	@SerialClass.SerialField
	public HashMap<String, String> materials = new HashMap<>();

	@SerialClass.SerialField
	public String[] suffixes;

	@SerialClass
	public static class Entry {

		@SerialClass.SerialField
		public int ingredient_weight;

		@SerialClass.SerialField
		public int extra_weight;

		@Deprecated
		public Entry() {

		}

		private Entry(int ingredient, int extra) {
			ingredient_weight = ingredient;
			extra_weight = extra;
		}

	}

	public static int getWeight(ItemStack stack) {
		ArmorWeight ins = getInstance();
		if (ins == null) return 0;
		int weight = ins.getItemWeight(stack);
		int lv = 0;//TODO stack.getEnchantmentLevel(LightlandVanillaMagic.HEAVY.get());
		return (int) (weight * (1 + 0.1 * lv));
	}

	private int getItemWeight(ItemStack stack) {
		if (stack.getItem() instanceof ArmorItem armor) {
			int slot_factor = getSlotFactor(armor.getSlot());
			int weight_factor = 0;
			int weight_extra = 0;
			ResourceLocation rl = ForgeRegistries.ITEMS.getKey(armor);
			if (rl != null) {
				String id = rl.toString();
				Entry entry = entries.get(id);
				if (entry == null) {
					String cut = cut(id);
					id = materials.get(id);
					if (id == null)
						id = cut;
					entry = entries.get(id);
				}
				if (entry != null) {
					weight_factor = entry.ingredient_weight;
					weight_extra = entry.extra_weight;
				}
			}
			int weight = slot_factor * weight_factor + weight_extra;
			if (weight > 0)
				return weight;
			return armor.getDefense() * 100;
		}
		return 0;
	}

	private String cut(String id) {
		for (String suf : suffixes) {
			if (id.endsWith("_" + suf))
				return id.substring(0, id.length() - 1 - suf.length());
		}
		return id;
	}

	public static int getSlotFactor(EquipmentSlot slot) {
		if (slot == EquipmentSlot.HEAD) {
			return 5;
		} else if (slot == EquipmentSlot.CHEST) {
			return 8;
		} else if (slot == EquipmentSlot.LEGS) {
			return 7;
		} else if (slot == EquipmentSlot.FEET) {
			return 4;
		}
		return 0;
	}

	@DataGenOnly
	public ArmorWeight add(String id, int ingredient, int extra) {
		entries.put(id, new Entry(ingredient, extra));
		return this;
	}

}
