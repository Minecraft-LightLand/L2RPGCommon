package dev.xkmc.l2rpgcommon.content.common.effect.skill;

import dev.xkmc.l2library.base.effects.api.InherentEffect;
import dev.xkmc.l2rpgcommon.content.common.effect.SkillEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class RunBowEffect extends InherentEffect implements SkillEffect<RunBowEffect> {

	public RunBowEffect(MobEffectCategory type, int color) {
		super(type, color);
	}

}
