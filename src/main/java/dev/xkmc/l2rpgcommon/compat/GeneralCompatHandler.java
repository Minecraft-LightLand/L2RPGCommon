package dev.xkmc.l2rpgcommon.compat;

import dev.xkmc.l2library.util.code.Wrappers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.fml.ModList;

public class GeneralCompatHandler {

	public enum Stage {
		INIT,
		OVERLAY
	}

	public static void handle(Stage stage) {
		if (ModList.get().isLoaded("create")) {
			//TODO Wrappers.ignore(() -> CreateCompatHandler.handleCompat(stage));
		}
		if (ModList.get().isLoaded("appleskin")) {
			Wrappers.ignore(() -> AppleSkinCompatHandler.handleCompat(stage));
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static OverlayRegistry.OverlayEntry getOverlay(String str) {
		return OverlayRegistry.orderedEntries().stream().filter(e -> e.getDisplayName().equals(str)).findFirst().orElse(null);
	}

}
