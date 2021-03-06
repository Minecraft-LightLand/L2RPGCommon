package dev.xkmc.l2rpgcommon.content.assassin.effect;

import dev.xkmc.l2rpgcommon.init.registrate.LLEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;

public class TargetRemoveEffect extends MobEffect {

	public TargetRemoveEffect(MobEffectCategory type, int color) {
		super(type, color);
	}

	@Override
	public boolean isDurationEffectTick(int tick, int level) {
		return tick % 4 == 0;
	}

	@Override
	public void applyEffectTick(LivingEntity self, int level) {
		if (self instanceof Mob mob) {
			LivingEntity old = mob.getTarget();
			if (old != null && !old.hasEffect(LLEffects.T_SINK.get())) {
				if (level > 0 || old.hasEffect(LLEffects.T_HIDE.get()))
					mob.setTarget(null);
			}
		}
	}

}
