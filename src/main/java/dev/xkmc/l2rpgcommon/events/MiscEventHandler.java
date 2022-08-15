package dev.xkmc.l2rpgcommon.events;

import dev.xkmc.l2library.util.Proxy;
import dev.xkmc.l2rpgcommon.content.common.capability.restriction.ArmorEnchant;
import dev.xkmc.l2rpgcommon.content.common.capability.restriction.ArmorWeight;
import dev.xkmc.l2rpgcommon.init.data.LangData;
import dev.xkmc.l2rpgcommon.init.data.Lore;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ArmorItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MiscEventHandler {

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

}
