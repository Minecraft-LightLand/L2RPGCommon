package dev.xkmc.l2rpgcommon.init.data.configs;

import dev.xkmc.l2library.serial.network.BaseConfig;
import dev.xkmc.l2rpgcommon.content.questline.mobs.swamp.SlimeProperties;
import dev.xkmc.l2rpgcommon.init.registrate.LightlandItems;
import dev.xkmc.l2rpgcommon.init.registrate.LightlangEffects;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;

import java.util.function.BiConsumer;

public class SlimeConfigGen {

	public static void addPotionDrop(BiConsumer<String, BaseConfig> adder) {
		adder.accept("default", new SlimeProperties()
				.add("poison", new SlimeProperties.SlimeConfig()
						.setEffect(MobEffects.POISON)
						.setDrop(Items.SPIDER_EYE)
						.setWeight(100)
						.setChance(0.1)
						.setDuration(100)
						.setAmplifier(0))
				.add("weakness", new SlimeProperties.SlimeConfig()
						.setEffect(MobEffects.WEAKNESS)
						.setDrop(Items.BROWN_MUSHROOM)
						.setWeight(50)
						.setChance(0.1)
						.setDuration(100)
						.setAmplifier(1))
				.add("slowness", new SlimeProperties.SlimeConfig()
						.setEffect(MobEffects.MOVEMENT_SLOWDOWN)
						.setDrop(Items.RED_MUSHROOM)
						.setWeight(50)
						.setChance(0.1)
						.setDuration(100)
						.setAmplifier(3))
				.add("wither", new SlimeProperties.SlimeConfig()
						.setEffect(MobEffects.WITHER)
						.setDrop(Items.WITHER_ROSE)
						.setWeight(20)
						.setChance(0.02)
						.setDuration(100)
						.setAmplifier(0))
				.add("blindness", new SlimeProperties.SlimeConfig()
						.setEffect(MobEffects.BLINDNESS)
						.setDrop(Items.LAPIS_LAZULI)
						.setWeight(50)
						.setChance(0.06)
						.setDuration(100)
						.setAmplifier(1))
				.add("regeneration", new SlimeProperties.SlimeConfig()
						.setEffect(MobEffects.REGENERATION)
						.setDrop(Items.GHAST_TEAR)
						.setWeight(50)
						.setChance(0.02)
						.setDuration(100)
						.setAmplifier(1))
				.add("armor_reduce", new SlimeProperties.SlimeConfig()
						.setEffect(LightlangEffects.ARMOR_REDUCE.get())
						.setDrop(LightlandItems.ACID_SLIME.get())
						.setWeight(50)
						.setChance(0.1)
						.setDuration(100)
						.setAmplifier(0))
		);
	}

}
