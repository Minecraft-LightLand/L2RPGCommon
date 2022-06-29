package dev.xkmc.l2rpgcommon.content.common.effect.skill;

import dev.xkmc.l2library.base.effects.api.InherentEffect;
import dev.xkmc.l2rpgcommon.content.common.effect.SkillEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class BloodThurstEffect extends InherentEffect implements SkillEffect<BloodThurstEffect> {

	public BloodThurstEffect(MobEffectCategory type, int color) {
		super(type, color);
	}

}
