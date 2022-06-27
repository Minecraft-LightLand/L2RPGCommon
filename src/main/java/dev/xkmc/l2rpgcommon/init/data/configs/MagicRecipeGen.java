package dev.xkmc.l2rpgcommon.init.data.configs;

import dev.xkmc.l2library.serial.network.BaseConfig;
import dev.xkmc.l2rpgcommon.content.magic.products.info.DisplayInfo;
import dev.xkmc.l2rpgcommon.content.magic.products.recipe.DefMagicRecipe;
import dev.xkmc.l2rpgcommon.init.special.ArcaneRegistry;
import dev.xkmc.l2rpgcommon.init.special.MagicRegistry;

import java.util.function.BiConsumer;

public class MagicRecipeGen {

	public static void addRecipe(BiConsumer<String, BaseConfig> adder) {
		adder.accept("arcane/marker",new DefMagicRecipe(
				MagicRegistry.MPT_ARCANE.get(),
				ArcaneRegistry.ALKAID_MARKER.getId(),
				new DisplayInfo(3,0)
		)
	}

}
