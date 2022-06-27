package dev.xkmc.l2rpgcommon.content.skill;

import dev.xkmc.l2library.base.effects.EffectUtil;
import dev.xkmc.l2library.serial.SerialClass;
import dev.xkmc.l2library.util.annotation.DataGenOnly;
import dev.xkmc.l2rpgcommon.compat.TeamAccessor;
import dev.xkmc.l2rpgcommon.content.skill.internal.Skill;
import dev.xkmc.l2rpgcommon.content.skill.internal.SkillConfig;
import dev.xkmc.l2rpgcommon.content.skill.internal.SkillData;
import dev.xkmc.l2rpgcommon.init.LightLand;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.IntFunction;

public class EffectSkill extends Skill<EffectSkill.Config, SkillData> {

	public record Effect(MobEffect id, int duration, int amplifier) {

	}

	public record RangeEffect(MobEffect id, int duration, int amplifier, double range, boolean for_enemy) {

	}

	@SerialClass
	public static class Config extends SkillConfig<SkillData> {

		@SerialClass.SerialField
		public Effect[][] effects;
		@SerialClass.SerialField
		public RangeEffect[][] range_effects;

		@SuppressWarnings("ConstantConditions")
		public void activate(ServerPlayer player, SkillData data) {
			int lv = Math.min(data.level, effects.length - 1);
			if (effects != null)
				for (Effect e : effects[lv]) {
					MobEffectInstance ins = new MobEffectInstance(e.id, e.duration, e.amplifier);
					EffectUtil.addEffect(player, ins, EffectUtil.AddReason.SKILL, player);
				}
			if (range_effects != null)
				for (RangeEffect eff : range_effects[lv]) {
					MobEffectInstance ins = new MobEffectInstance(eff.id, eff.duration, eff.amplifier);
					for (Entity e : player.level.getEntities(player, new AABB(player.position(), player.position()).inflate(eff.range))) {
						if (e.distanceToSqr(player) > eff.range * eff.range) continue;
						if (!(e instanceof LivingEntity le)) continue;
						if (eff.for_enemy == TeamAccessor.arePlayerAndEntityInSameTeam(player, le)) continue;
						EffectUtil.addEffect(le, ins, EffectUtil.AddReason.SKILL, player);
					}
				}
		}

		@Override
		public boolean isValid() {
			if (!super.isValid()) return false;
			if (effects != null) {
				if (effects.length != max_level) {
					LightLand.LOGGER.error("effects length must be the same as max_level");
					return false;
				}
				for (int i = 0; i < max_level; i++)
					for (Effect e : effects[i]) {
						if (e.duration <= 0 || e.duration > cooldown[i]) {
							LightLand.LOGGER.error("duration is invalid");
							return false;
						}
						if (e.amplifier < 0) {
							LightLand.LOGGER.error("amplifier is invalid");
							return false;
						}
					}
			}
			if (range_effects != null) {
				if (range_effects.length != max_level) {
					LightLand.LOGGER.error("range_effects length must be the same as max_level");
					return false;
				}
				for (int i = 0; i < max_level; i++)
					for (RangeEffect e : range_effects[i]) {
						if (e.duration <= 0 || e.duration > cooldown[i]) {
							LightLand.LOGGER.error("duration is invalid");
							return false;
						}
						if (e.amplifier < 0) {
							LightLand.LOGGER.error("amplifier is invalid");
							return false;
						}
						if (e.range < 2 || e.range > 64) {
							LightLand.LOGGER.error("range is invalid");
							return false;
						}
					}
			}
			return true;
		}

	}

	@Override
	public void activate(Level level, ServerPlayer player, SkillData data) {
		Config config = getConfig();
		if (config != null) config.activate(player, data);
		super.activate(level, player, data);
	}

	public SkillData genData(Player player) {
		return new SkillData();
	}

	@DataGenOnly
	public static class EffectSkillBuilder extends SkillConfig.ConfigLevelBuilder<Config, SkillData> {

		private final List<IntFunction<Effect>> effects = new ArrayList<>();
		private final List<IntFunction<RangeEffect>> rangeEffects = new ArrayList<>();

		public EffectSkillBuilder(int maxLevel, Int2IntFunction cooldown) {
			super(maxLevel, cooldown);
		}

		public EffectSkillBuilder addEffect(IntFunction<Effect> gen) {
			effects.add(gen);
			return this;
		}

		public EffectSkillBuilder addRangeEffect(IntFunction<RangeEffect> gen) {
			rangeEffects.add(gen);
			return this;
		}

		@Override
		protected Config build(Config config) {
			config.effects = new Effect[maxLevel][];
			config.range_effects = new RangeEffect[maxLevel][];
			return super.build(config);
		}

		@Override
		protected void build(Config config, int lv) {
			super.build(config, lv);
			config.effects[lv] = effects.stream().map(e -> e.apply(lv)).filter(Objects::nonNull).toArray(Effect[]::new);
			config.range_effects[lv] = rangeEffects.stream().map(e -> e.apply(lv)).filter(Objects::nonNull).toArray(RangeEffect[]::new);
		}

		@Override
		public Config build() {
			Config ans = new Config();
			build(ans);
			return ans;
		}
	}

}
