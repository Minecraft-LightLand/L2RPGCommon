package dev.xkmc.l2rpgcommon.init.special;

import dev.xkmc.l2library.repack.registrate.util.entry.RegistryEntry;
import dev.xkmc.l2library.repack.registrate.util.nullness.NonNullSupplier;
import dev.xkmc.l2rpgcommon.content.skill.EffectSkill;
import dev.xkmc.l2rpgcommon.content.skill.ImpactSkill;
import dev.xkmc.l2rpgcommon.content.skill.MovementSkill;
import dev.xkmc.l2rpgcommon.content.skill.internal.Skill;
import dev.xkmc.l2rpgcommon.init.LightLand;

public class SkillRegistry {

	public static final int MAX = 3;

	public static final RegistryEntry<EffectSkill> NO_KB = reg("no_knockback", EffectSkill::new);
	public static final RegistryEntry<EffectSkill> BLOOD_THIRST = reg("blood_thirst", EffectSkill::new);
	public static final RegistryEntry<MovementSkill> QUICK_MOVE = reg("quick_move", MovementSkill::new);
	public static final RegistryEntry<EffectSkill> QUICK_PULL = reg("quick_pull", EffectSkill::new);
	public static final RegistryEntry<EffectSkill> TARGET_HIDE = reg("target_hide", EffectSkill::new);
	public static final RegistryEntry<EffectSkill> TARGET_ATTRACT = reg("target_attract", EffectSkill::new);
	public static final RegistryEntry<EffectSkill> ARMOR_BREAKER = reg("armor_breaker", EffectSkill::new);
	public static final RegistryEntry<ImpactSkill> IMPACT_ATTRACT = reg("impact_attract", ImpactSkill::new);
	public static final RegistryEntry<ImpactSkill> IMPACT_REPEL = reg("impact_repel", ImpactSkill::new);

	public static <T extends Skill<?, ?>> RegistryEntry<T> reg(String id, NonNullSupplier<T> sup) {
		return LightLand.REGISTRATE.generic(LightLandRegistry.SKILL, id, sup).defaultLang().register();
	}

	public static void register() {
	}

}
