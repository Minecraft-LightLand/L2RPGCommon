package dev.xkmc.l2rpgcommon.init.data.configs;

import dev.xkmc.l2foundation.init.registrate.LFEffects;
import dev.xkmc.l2library.serial.network.BaseConfig;
import dev.xkmc.l2rpgcommon.content.skill.EffectSkill.Effect;
import dev.xkmc.l2rpgcommon.content.skill.EffectSkill.EffectSkillBuilder;
import dev.xkmc.l2rpgcommon.content.skill.EffectSkill.RangeEffect;
import dev.xkmc.l2rpgcommon.content.skill.ImpactSkill;
import dev.xkmc.l2rpgcommon.content.skill.MovementSkill;
import dev.xkmc.l2rpgcommon.init.special.SkillRegistry;
import dev.xkmc.l2rpgcommon.network.config.SkillDataConfig;
import net.minecraft.world.effect.MobEffects;

import java.util.function.BiConsumer;

public class SkillConfigGen {

	public static void addSkill(BiConsumer<String, BaseConfig> adder) {
		adder.accept("effects_berserker", new SkillDataConfig()
				.add(SkillRegistry.NO_KB.get(), new EffectSkillBuilder(3, lv -> 2400)
						.addEffect(lv -> new Effect(LFEffects.NO_KB.get(), 300 * (lv + 2), 0))
						.build())
				.add(SkillRegistry.BLOOD_THIRST.get(), new EffectSkillBuilder(3, lv -> 1800)
						.addEffect(lv -> new Effect(LFEffects.BLOOD_THURST.get(), 600, lv))
						.build())
				.add(SkillRegistry.ARMOR_BREAKER.get(), new EffectSkillBuilder(3, lv -> 1200)
						.addEffect(lv -> new Effect(LFEffects.ARMOR_BREAKER.get(), 600, lv))
						.addEffect(lv -> new Effect(LFEffects.ARMOR_REDUCE.get(), 600, 0))
						.build())
		);
		adder.accept("effects_archer", new SkillDataConfig()
				.add(SkillRegistry.QUICK_PULL.get(), new EffectSkillBuilder(3, lv -> 1800)
						.addEffect(lv -> new Effect(MobEffects.MOVEMENT_SPEED, 600, Math.min(lv, 1)))
						.addEffect(lv -> new Effect(MobEffects.JUMP, 600, Math.min(lv, 1)))
						.addEffect(lv -> new Effect(LFEffects.QUICK_PULL.get(), 600, lv))
						.addEffect(lv -> lv < 2 ? null : new Effect(LFEffects.RUN_BOW.get(), 600, 0))
						.build())
		);

		adder.accept("effects_target", new SkillDataConfig()
				.add(SkillRegistry.TARGET_HIDE.get(), new EffectSkillBuilder(3, lv -> 2400)
						.addEffect(lv -> new Effect(LFEffects.T_HIDE.get(), lv == 0 ? 600 : 900, lv < 2 ? 0 : 1))
						.build())
				.add(SkillRegistry.TARGET_ATTRACT.get(), new EffectSkillBuilder(3, lv -> 1200)
						.addEffect(lv -> new Effect(LFEffects.T_SINK.get(), 600 + lv * 150, lv))
						.addRangeEffect(lv -> new RangeEffect(LFEffects.T_SOURCE.get(),
								600 + lv * 150, lv, 10 + lv * 4, true))
						.build())
		);

		adder.accept("impacts", new SkillDataConfig()
				.add(SkillRegistry.IMPACT_ATTRACT.get(), new ImpactSkill.ImpactSkillBuilder(
						3, lv -> 200, lv -> 6 + lv * 2, lv -> -1 - lv, lv -> 10 + lv * 5
				).build())
				.add(SkillRegistry.IMPACT_REPEL.get(), new ImpactSkill.ImpactSkillBuilder(
						3, lv -> 200, lv -> 6 + lv * 2, lv -> 1 + lv, lv -> 10 + lv * 5
				).build())
		);

		adder.accept("movements", new SkillDataConfig()
				.add(SkillRegistry.QUICK_MOVE.get(), new MovementSkill.MovementSkillBuilder(
						3, lv -> 200, lv -> 16 >> lv, lv -> 0.5 * (1 << lv)
				).build())
		);
	}

}
