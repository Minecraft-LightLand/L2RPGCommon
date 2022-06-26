package dev.xkmc.l2rpgcommon.init;

import dev.xkmc.l2rpgcommon.compat.GeneralCompatHandler;
import dev.xkmc.l2rpgcommon.content.common.render.ItemNameOverlay;
import dev.xkmc.l2rpgcommon.content.common.render.LLOverlay;
import dev.xkmc.l2rpgcommon.content.common.render.MagicWandOverlay;
import dev.xkmc.l2rpgcommon.init.data.LangData;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.OverlayRegistry;

public class ClientRegister {

	@OnlyIn(Dist.CLIENT)
	public static void registerItemProperties() {
	}

	@OnlyIn(Dist.CLIENT)
	public static void registerOverlays() {
		OverlayRegistry.enableOverlay(ForgeIngameGui.HOTBAR_ELEMENT, false);
		OverlayRegistry.enableOverlay(ForgeIngameGui.EXPERIENCE_BAR_ELEMENT, false);
		OverlayRegistry.enableOverlay(ForgeIngameGui.JUMP_BAR_ELEMENT, false);
		OverlayRegistry.enableOverlay(ForgeIngameGui.ITEM_NAME_ELEMENT, false);
		OverlayRegistry.enableOverlay(ForgeIngameGui.PLAYER_HEALTH_ELEMENT, false);
		OverlayRegistry.enableOverlay(ForgeIngameGui.ARMOR_LEVEL_ELEMENT, false);
		OverlayRegistry.enableOverlay(ForgeIngameGui.CROSSHAIR_ELEMENT, false);
		OverlayRegistry.enableOverlay(ForgeIngameGui.AIR_LEVEL_ELEMENT, false);
		OverlayRegistry.enableOverlay(ForgeIngameGui.FOOD_LEVEL_ELEMENT, false);
		OverlayRegistry.enableOverlay(ForgeIngameGui.MOUNT_HEALTH_ELEMENT, false);
		OverlayRegistry.registerOverlayAbove(ForgeIngameGui.CROSSHAIR_ELEMENT, "MagicWand", MagicWandOverlay.INSTANCE);
		OverlayRegistry.registerOverlayAbove(ForgeIngameGui.HOTBAR_ELEMENT, "lightland main", new LLOverlay());
		OverlayRegistry.registerOverlayAbove(ForgeIngameGui.ITEM_NAME_ELEMENT, "ItemShifted", new ItemNameOverlay());
		GeneralCompatHandler.handle(GeneralCompatHandler.Stage.OVERLAY);
	}

	@OnlyIn(Dist.CLIENT)
	public static void registerKeys() {
		ClientRegistry.registerKeyBinding(LangData.Keys.SKILL_1.map);
		ClientRegistry.registerKeyBinding(LangData.Keys.SKILL_2.map);
		ClientRegistry.registerKeyBinding(LangData.Keys.SKILL_3.map);

	}

}
