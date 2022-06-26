package dev.xkmc.l2rpgcommon.content.magic.ritual;

import dev.xkmc.l2library.serial.SerialClass;
import dev.xkmc.l2rpgcommon.content.magic.block.RitualCore;
import dev.xkmc.l2rpgcommon.init.registrate.LightlandRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@SerialClass
@ParametersAreNonnullByDefault
public class PotionBoostRecipe extends AbstractLevelRitualRecipe<PotionBoostRecipe> {

	@SerialClass.SerialField
	public ResourceLocation effect;

	@SerialClass.SerialField
	public int modify_level;

	public PotionBoostRecipe(ResourceLocation id) {
		super(id, LightlandRecipe.RSP_BOOST.get());
	}

	public void assemble(RitualCore.Inv inv, int level) {
		ItemStack stack = inv.core.getItem(0).copy();
		List<MobEffectInstance> list = new ArrayList<>();
		for (MobEffectInstance ins : PotionUtils.getCustomEffects(stack)) {
			ResourceLocation rl = ForgeRegistries.MOB_EFFECTS.getKey(ins.getEffect());
			assert rl != null;
			if (rl.equals(effect)) {
				if (ins.getAmplifier() < level) {
					if (modify_level == -1)
						continue;
					list.add(new MobEffectInstance(ins.getEffect(), ins.getDuration(), level - 1));
					continue;
				}
			}
			list.add(ins);
		}
		PotionUtils.setCustomEffects(stack, list);
		assemble(inv);
		inv.setItem(5, stack);
	}

}
