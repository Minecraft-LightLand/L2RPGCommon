package dev.xkmc.l2rpgcommon.events;

import dev.xkmc.l2library.base.effects.EffectUtil;
import dev.xkmc.l2library.base.effects.api.InherentEffect;
import dev.xkmc.l2library.util.Proxy;
import dev.xkmc.l2rpgcommon.content.common.capability.restriction.ArmorEnchant;
import dev.xkmc.l2rpgcommon.content.common.capability.restriction.ArmorWeight;
import dev.xkmc.l2rpgcommon.content.common.render.MagicWandOverlay;
import dev.xkmc.l2rpgcommon.init.data.LangData;
import dev.xkmc.l2rpgcommon.init.data.Lore;
import dev.xkmc.l2rpgcommon.init.registrate.LLEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ArmorItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class MiscEventHandler {

	@SubscribeEvent
	public static void onTargetSet(LivingSetAttackTargetEvent event) {
		if (event.getTarget() != null && (event.getEntity().hasEffect(LLEffects.T_CLEAR.get()) ||
				event.getTarget().hasEffect(LLEffects.T_HIDE.get()))) {
			((Mob) event.getEntity()).setTarget(null);
		}
	}

	@SubscribeEvent
	public static void onVisibilityGet(LivingEvent.LivingVisibilityEvent event) {
		if (event.getEntity().hasEffect(LLEffects.T_HIDE.get()))
			event.modifyVisibility(0);
	}

	@SubscribeEvent
	public static void onEntityKnockBack(LivingKnockBackEvent event) {
		if (event.getEntity().hasEffect(LLEffects.NO_KB.get()))
			event.setCanceled(true);
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void keyEvent(InputEvent.Key event) {
		if (Minecraft.getInstance().screen == null && Proxy.getClientPlayer() != null && MagicWandOverlay.has_magic_wand) {
			MagicWandOverlay.input(event.getKey(), event.getAction());
		}
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onTooltipEvent(ItemTooltipEvent event) {
		if (event.getItemStack().getItem() instanceof ArmorItem) {
			int weight = ArmorWeight.getWeight(event.getItemStack());
			double load = ArmorEnchant.getItemArmorEnchantLevel(event.getItemStack());
			boolean tooHeavy = !ArmorWeight.canPutOn(Proxy.getClientPlayer(), event.getItemStack());
			boolean tooMagic = !ArmorEnchant.canPutOn(Proxy.getClientPlayer(), event.getItemStack());
			if (weight > 0) {
				MutableComponent comp = LangData.IDS.ARMOR_WEIGHT.get(weight);
				if (tooHeavy)
					comp.withStyle(ChatFormatting.RED);
				event.getToolTip().add(comp);
			}
			if (load > 0) {
				MutableComponent comp = LangData.IDS.ARMOR_ENCHANT.get(load);
				if (tooMagic)
					comp.withStyle(ChatFormatting.RED);
				event.getToolTip().add(comp);
			}
			if (ArmorEnchant.isCursed(event.getItemStack())) {
				event.getToolTip().add(Lore.ENCHANT_LOAD.get().withStyle(ChatFormatting.RED));
			}
		}
	}

	@SubscribeEvent
	public static void onPotionTest(MobEffectEvent.Applicable event) {
		boolean flag = event.getEntity().hasEffect(LLEffects.CLEANSE.get());
		flag |= event.getEntity().hasEffect(LLEffects.DISPELL.get());
		if (flag) {
			if (event.getEffectInstance().getEffect() instanceof InherentEffect)
				return;
			if (EffectUtil.getReason() == EffectUtil.AddReason.FORCE)
				return;
			if (EffectUtil.getReason() == EffectUtil.AddReason.SELF)
				return;
			if (EffectUtil.getReason() == EffectUtil.AddReason.SKILL)
				return;
			if (event.getEffectInstance().getEffect() == LLEffects.CLEANSE.get())
				return;
			if (event.getEffectInstance().getEffect() == LLEffects.DISPELL.get())
				return;
			event.setResult(Event.Result.DENY);
		}
	}

	@SubscribeEvent
	public static void onPotionAdded(MobEffectEvent.Added event) {
		boolean flag = event.getEffectInstance().getEffect() == LLEffects.CLEANSE.get();
		flag |= event.getEffectInstance().getEffect() == LLEffects.DISPELL.get();
		if (flag) {
			List<MobEffectInstance> list = new ArrayList<>(event.getEntity().getActiveEffects());
			for (MobEffectInstance ins : list) {
				if (ins.getEffect() instanceof InherentEffect)
					continue;
				if (ins.getEffect() == LLEffects.CLEANSE.get())
					continue;
				if (ins.getEffect() == LLEffects.DISPELL.get())
					continue;
				event.getEntity().removeEffect(ins.getEffect());
			}
		}
	}

}
