package dev.xkmc.l2rpgcommon.util;

import dev.xkmc.l2library.base.effects.EffectSyncEvents;
import dev.xkmc.l2library.base.effects.EffectUtil;
import dev.xkmc.l2rpgcommon.init.registrate.LightlandVanillaMagic;
import dev.xkmc.l2rpgcommon.init.special.ArcaneRegistry;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class EffectAddUtil {

	public static void addArcane(LivingEntity target, Entity source) {
		EffectUtil.addEffect(target, new MobEffectInstance(LightlandVanillaMagic.ARCANE.get(), ArcaneRegistry.ARCANE_TIME), EffectUtil.AddReason.SKILL, source);
	}

	public static void init() {
		EffectSyncEvents.TRACKED.add(LightlandVanillaMagic.ARCANE.get());
		EffectSyncEvents.TRACKED.add(LightlandVanillaMagic.WATER_TRAP.get());
		EffectSyncEvents.TRACKED.add(LightlandVanillaMagic.FLAME.get());
		EffectSyncEvents.TRACKED.add(LightlandVanillaMagic.EMERALD.get());
		EffectSyncEvents.TRACKED.add(LightlandVanillaMagic.ICE.get());
	}

}
