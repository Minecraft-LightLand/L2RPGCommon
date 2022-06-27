package dev.xkmc.l2rpgcommon.init.data.recipe;

import dev.xkmc.l2library.base.recipe.BaseRecipe;
import dev.xkmc.l2library.base.recipe.BaseRecipeBuilder;
import dev.xkmc.l2rpgcommon.content.magic.block.RitualCore;
import dev.xkmc.l2rpgcommon.content.magic.ritual.AbstractRitualRecipe;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;

public class AbstractRitualBuilder<B extends AbstractRitualBuilder<B, R>, R extends AbstractRitualRecipe<R>>
		extends BaseRecipeBuilder<B, R, AbstractRitualRecipe<?>, RitualCore.Inv> {

	public AbstractRitualBuilder(BaseRecipe.RecType<R, AbstractRitualRecipe<?>, RitualCore.Inv> type) {
		super(type);
		recipe.core = new AbstractRitualRecipe.Entry();
		recipe.side = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			new AbstractRitualRecipe.Entry();
		}
	}

	public B setCore(ItemStack in, ItemStack out) {
		recipe.core.input = in;
		recipe.core.output = out;
		return getThis();
	}

	public B setSide(ItemStack in, ItemStack out, int index) {
		recipe.side.get(index).input = in;
		recipe.side.get(index).output = out;
		return getThis();
	}

}
