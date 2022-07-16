package dev.xkmc.l2rpgcommon.compat;

import dev.xkmc.l2library.util.code.Wrappers;
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
	}

}
