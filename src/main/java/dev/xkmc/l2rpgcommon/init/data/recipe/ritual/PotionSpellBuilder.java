package dev.xkmc.l2rpgcommon.init.data.recipe.ritual;

import dev.xkmc.l2rpgcommon.content.magic.ritual.PotionSpellRecipe;
import dev.xkmc.l2rpgcommon.init.registrate.LLRecipes;

public class PotionSpellBuilder extends AbstractRitualBuilder<PotionSpellBuilder, PotionSpellRecipe> {

	public PotionSpellBuilder() {
		super(LLRecipes.RSP_SPELL.get());
	}

}
