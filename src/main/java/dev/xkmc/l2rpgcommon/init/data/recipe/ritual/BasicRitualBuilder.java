package dev.xkmc.l2rpgcommon.init.data.recipe.ritual;

import dev.xkmc.l2rpgcommon.content.magic.ritual.BasicRitualRecipe;
import dev.xkmc.l2rpgcommon.init.registrate.LLRecipes;

public class BasicRitualBuilder extends AbstractRitualBuilder<BasicRitualBuilder, BasicRitualRecipe> {

	public BasicRitualBuilder() {
		super(LLRecipes.RS_DEF.get());
	}

}
