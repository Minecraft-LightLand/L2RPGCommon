package dev.xkmc.l2rpgcommon.init.data.configs;

import dev.xkmc.l2library.serial.network.BaseConfig;
import dev.xkmc.l2rpgcommon.content.magic.products.info.DisplayInfo;
import dev.xkmc.l2rpgcommon.content.magic.products.recipe.DefMagicRecipe;
import dev.xkmc.l2rpgcommon.content.magic.products.recipe.IMagicRecipe;
import dev.xkmc.l2rpgcommon.init.special.ArcaneRegistry;
import dev.xkmc.l2rpgcommon.init.special.MagicRegistry;
import net.minecraft.advancements.FrameType;
import net.minecraft.world.item.Items;

import java.util.function.BiConsumer;

public class MagicRecipeGen {

	public static void addRecipe(BiConsumer<String, BaseConfig> adder) {
		adder.accept("arcane/marker", new DefMagicRecipe(
				MagicRegistry.MPT_ARCANE.get(),
				ArcaneRegistry.ALKAID_MARKER.getId(),
				new DisplayInfo(3, 0, FrameType.TASK, Items.PAPER))
				.addElement("A", MagicRegistry.ELEM_FIRE.get())
				.addElement("B", MagicRegistry.ELEM_WATER.get())
				.addElement("C", MagicRegistry.ELEM_QUINT.get())
				.addElement("D", MagicRegistry.ELEM_AIR.get())
				.addElement("E", MagicRegistry.ELEM_EARTH.get())
				.addElement("F", MagicRegistry.ELEM_QUINT.get())
				.setFlow("ABCDEF|")
				.addElemRequirement(new IMagicRecipe.ElementalMastery(MagicRegistry.ELEM_QUINT.get(), 1))
		);

		//TODO
	}

}
