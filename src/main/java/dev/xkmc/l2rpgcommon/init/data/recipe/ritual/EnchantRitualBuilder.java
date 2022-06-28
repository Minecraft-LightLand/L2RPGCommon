package dev.xkmc.l2rpgcommon.init.data.recipe.ritual;

import dev.xkmc.l2rpgcommon.content.magic.ritual.EnchantRitualRecipe;
import dev.xkmc.l2rpgcommon.init.registrate.LLRecipes;
import net.minecraft.resources.ResourceLocation;

public class EnchantRitualBuilder extends AbstractLevelRitualBuilder<EnchantRitualBuilder, EnchantRitualRecipe> {

	public EnchantRitualBuilder(ResourceLocation magic, int... levels) {
		super(LLRecipes.RS_ENCH.get(), magic, levels);
	}

}
