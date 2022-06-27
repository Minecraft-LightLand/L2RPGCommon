package dev.xkmc.l2rpgcommon.init.data.configs;

import dev.xkmc.l2library.serial.network.BaseConfig;
import dev.xkmc.l2rpgcommon.content.common.capability.restriction.ArmorEnchant;
import dev.xkmc.l2rpgcommon.content.common.capability.restriction.ArmorWeight;
import dev.xkmc.l2rpgcommon.init.data.templates.GenItem;
import dev.xkmc.l2rpgcommon.init.registrate.LLItems;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.function.BiConsumer;

public class ArmorConfigGen {

	public static void addEnchant(BiConsumer<String, BaseConfig> adder) {
		adder.accept("vanilla", new ArmorEnchant()
				.add(Enchantments.ALL_DAMAGE_PROTECTION, 4)
				.add(Enchantments.BLAST_PROTECTION, 3)
				.add(Enchantments.PROJECTILE_PROTECTION, 3)
				.add(Enchantments.FALL_PROTECTION, 2)
				.add(Enchantments.UNBREAKING, 2)
				.add(Enchantments.THORNS, 2)
				.add(Enchantments.RESPIRATION, 1)
				.add(Enchantments.AQUA_AFFINITY, 1)
				.add(Enchantments.DEPTH_STRIDER, 1)
				.add(Enchantments.FROST_WALKER, 1)
				.add(Enchantments.SOUL_SPEED, 1)
				.add(Enchantments.MENDING, 5)
		);
	}

	public static void addWeight(BiConsumer<String, BaseConfig> adder) {
		adder.accept("vanilla", new ArmorWeight()
				.add("minecraft:leather", 30, 0)
				.add("minecraft:chainmail", 50, 0)
				.add("minecraft:iron", 70, 0)
				.add("minecraft:golden", 100, 0)
				.add("minecraft:diamond", 80, 0)
				.add("minecraft:netherite", 80, 30)
				.add("minecraft:turtle_helmet", 0, 200));
		adder.accept("lightland", new ArmorWeight()
				.add(LLItems.MEDICINE_ARMOR.prefix, 30, 0)
				.add(LLItems.KING_MED_ARMOR.prefix, 30, 0)
				.add(GenItem.Mats.STEEL.armorPrefix(), 70, 0)
				.add(GenItem.Mats.LAYROOT.armorPrefix(), 50, 0)
				.add(GenItem.Mats.LAYLINE.armorPrefix(), 50, 0)
				.add(GenItem.Mats.OLDROOT.armorPrefix(), 60, 0)
				.add(GenItem.Mats.KNIGHTSTEEL.armorPrefix(), 70, 0)
				.add(GenItem.Mats.DISPELLIUM.armorPrefix(), 70, 0)
				.add(GenItem.Mats.HEAVYSTEEL.armorPrefix(), 100, 0)
				.add(GenItem.Mats.ETHERNIUM.armorPrefix(), 70, 0));
	}

}
