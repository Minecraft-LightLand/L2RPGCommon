package dev.xkmc.l2rpgcommon.init.data.recipe.ritual;

import dev.xkmc.l2rpgcommon.content.magic.ritual.PotionCoreRecipe;
import dev.xkmc.l2rpgcommon.init.registrate.LLRecipes;

public class PotionCoreBuilder extends AbstractRitualBuilder<PotionCoreBuilder, PotionCoreRecipe> {

	public PotionCoreBuilder() {
		super(LLRecipes.RSP_CORE.get());
	}

}
