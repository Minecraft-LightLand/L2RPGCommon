package dev.xkmc.l2rpgcommon.init;

import dev.xkmc.l2magic.init.L2Magic;
import dev.xkmc.l2rpgcommon.compat.GeneralCompatHandler;
import dev.xkmc.l2rpgcommon.content.common.render.ItemNameOverlay;
import dev.xkmc.l2rpgcommon.content.common.render.LLOverlay;
import dev.xkmc.l2rpgcommon.init.data.LangData;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;

import java.util.HashSet;
import java.util.Set;

public class ClientRegister {

	public static final Set<ResourceLocation> REMOVED = new HashSet<>();

	@OnlyIn(Dist.CLIENT)
	public static void registerItemProperties() {
	}

	@OnlyIn(Dist.CLIENT)
	public static void disableOverlays(RenderGuiOverlayEvent.Pre event) {
		if (REMOVED.contains(event.getOverlay().id())) {
			event.setCanceled(true);
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static void registerOverlays(RegisterGuiOverlaysEvent event) {
		REMOVED.add(new ResourceLocation(L2Magic.MODID, "spell_bar"));
		REMOVED.add(VanillaGuiOverlay.EXPERIENCE_BAR.id());
		REMOVED.add(VanillaGuiOverlay.JUMP_BAR.id());
		REMOVED.add(VanillaGuiOverlay.ITEM_NAME.id());
		REMOVED.add(VanillaGuiOverlay.PLAYER_HEALTH.id());
		REMOVED.add(VanillaGuiOverlay.ARMOR_LEVEL.id());
		REMOVED.add(VanillaGuiOverlay.AIR_LEVEL.id());
		REMOVED.add(VanillaGuiOverlay.FOOD_LEVEL.id());
		REMOVED.add(VanillaGuiOverlay.MOUNT_HEALTH.id());
		event.registerAbove(VanillaGuiOverlay.HOTBAR.id(), "lightland_main", new LLOverlay());
		event.registerAbove(VanillaGuiOverlay.ITEM_NAME.id(), "item_shifted", new ItemNameOverlay());
		GeneralCompatHandler.handle(GeneralCompatHandler.Stage.OVERLAY);
	}

	@OnlyIn(Dist.CLIENT)
	public static void registerKeys(RegisterKeyMappingsEvent event) {
		event.register(LangData.Keys.SKILL_1.map);
		event.register(LangData.Keys.SKILL_2.map);
		event.register(LangData.Keys.SKILL_3.map);
	}

}
