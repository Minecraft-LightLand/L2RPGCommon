package dev.xkmc.l2rpgcommon.init.data.recipe.ritual;

import dev.xkmc.l2rpgcommon.content.magic.ritual.PotionModifyRecipe;
import dev.xkmc.l2rpgcommon.init.registrate.LLRecipes;

public class PotionModifyBuilder extends AbstractRitualBuilder<PotionModifyBuilder, PotionModifyRecipe> {

	public PotionModifyBuilder() {
		super(LLRecipes.RSP_MODIFY.get());
	}

}
