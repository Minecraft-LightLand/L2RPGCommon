package dev.xkmc.l2rpgcommon.content.skill.internal;

import dev.xkmc.l2library.serial.SerialClass;
import dev.xkmc.l2library.util.annotation.DataGenOnly;
import dev.xkmc.l2rpgcommon.init.LightLand;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;

@SerialClass
public class SkillConfig<T extends SkillData> {

	@SerialClass.SerialField
	public int[] cooldown;

	@SerialClass.SerialField
	public int max_level;

	public int getCooldown(T data) {
		int lv = Math.min(data.level, cooldown.length - 1);
		return cooldown[lv];
	}

	public boolean isValid() {
		if (max_level <= 0) {
			LightLand.LOGGER.error("max_level must be positive");
			return false;
		}
		if (cooldown.length != max_level) {
			LightLand.LOGGER.error("cooldown length must be the same as max_level");
			return false;
		}
		for (int val : cooldown) {
			if (val <= 0) {
				LightLand.LOGGER.error("cooldown must be positive");
				return false;
			}
		}
		return true;
	}

	@DataGenOnly
	public static abstract class ConfigLevelBuilder<C extends SkillConfig<T>, T extends SkillData> {

		protected final int maxLevel;
		private final Int2IntFunction cooldown;

		public ConfigLevelBuilder(int maxLevel, Int2IntFunction cooldown) {
			this.maxLevel = maxLevel;
			this.cooldown = cooldown;
		}

		protected C build(C config) {
			config.max_level = maxLevel;
			config.cooldown = new int[maxLevel];
			for (int i = 0; i < maxLevel; i++) {
				build(config, i);
			}
			return config;
		}

		protected void build(C config, int lv) {
			config.cooldown[lv] = cooldown.applyAsInt(lv);
		}

		public abstract C build();

	}

}
