package dev.xkmc.l2rpgcommon.content.magic.ritual;

import dev.xkmc.l2library.serial.SerialClass;
import dev.xkmc.l2rpgcommon.init.registrate.LightlandRecipe;
import net.minecraft.resources.ResourceLocation;

@SerialClass
public class BasicRitualRecipe extends AbstractRitualRecipe<BasicRitualRecipe> {

	public BasicRitualRecipe(ResourceLocation id) {
		super(id, LightlandRecipe.RS_DEF.get());
	}
}
