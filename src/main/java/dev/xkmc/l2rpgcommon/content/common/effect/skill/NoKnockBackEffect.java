package dev.xkmc.l2rpgcommon.content.common.effect.skill;

import dev.xkmc.l2rpgcommon.content.common.effect.SkillEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class NoKnockBackEffect extends MobEffect implements SkillEffect {

	public NoKnockBackEffect(MobEffectCategory type, int color) {
		super(type, color);
	}
}
