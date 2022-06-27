package dev.xkmc.l2rpgcommon.init.data.recipe;

import dev.xkmc.l2library.base.recipe.CustomShapedBuilder;
import dev.xkmc.l2rpgcommon.content.berserker.recipe.MedArmorRecipe;
import dev.xkmc.l2rpgcommon.init.registrate.LLRecipes;
import net.minecraft.world.level.ItemLike;

public class MedArmorBuilder extends CustomShapedBuilder<MedArmorRecipe> {

	public MedArmorBuilder(ItemLike result, int count) {
		super(LLRecipes.RSC_MED_ARMOR, result, count);
	}

}
