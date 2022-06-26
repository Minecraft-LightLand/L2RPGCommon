package dev.xkmc.l2rpgcommon.init.data.configs;

import dev.xkmc.l2library.serial.network.BaseConfig;
import dev.xkmc.l2rpgcommon.content.common.capability.restriction.ArmorEnchant;
import dev.xkmc.l2rpgcommon.content.common.capability.restriction.ArmorWeight;
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
				.add("l2rpgcommon:medicine_leather", 30, 0)
				.add("l2rpgcommon:king_leather", 30, 0)
				.add("l2rpgcommon:steel", 70, 0)
				.add("l2rpgcommon:layroot", 500, 0)
				.add("l2rpgcommon:layline", 50, 0)
				.add("l2rpgcommon:oldroot", 50, 0)
				.add("l2rpgcommon:knightsteel", 70, 0)
				.add("l2rpgcommon:dispellium", 70, 0)
				.add("l2rpgcommon:heavysteel", 100, 0)
				.add("l2rpgcommon:ethernium", 70, 0));
	}

}
