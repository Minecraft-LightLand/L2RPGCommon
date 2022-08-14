package dev.xkmc.l2rpgcommon.content.magic.ritual;

import dev.xkmc.l2library.serial.SerialClass;
import dev.xkmc.l2rpgcommon.content.magic.block.RitualCore;
import dev.xkmc.l2rpgcommon.content.magic.item.MagicScroll;
import dev.xkmc.l2rpgcommon.init.registrate.LLRecipes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;

@SerialClass
public class PotionModifyRecipe extends AbstractRitualRecipe<PotionModifyRecipe> {

	public PotionModifyRecipe(ResourceLocation id) {
		super(id, LLRecipes.RSP_MODIFY.get());
	}

	@Override
	public void assemble(RitualCore.Inv inv, int level) {
		ItemStack core = inv.core.getItem(0).copy();
		assemble(inv);
		if (target != null) {
			MagicScroll.setTarget(target, core);
		}
		if (radius > 0) {
			MagicScroll.setRadius(radius, core);
		}
		inv.setItem(5, core);
	}

	@SerialClass.SerialField
	public MagicScroll.TargetType target;

	@SerialClass.SerialField
	public double radius;

}
