package dev.xkmc.l2rpgcommon.content.magic.products.instance;

import dev.xkmc.l2library.serial.NBTObj;
import dev.xkmc.l2rpgcommon.content.common.capability.player.LLPlayerData;
import dev.xkmc.l2rpgcommon.content.magic.products.MagicProduct;
import dev.xkmc.l2rpgcommon.content.magic.products.recipe.IMagicRecipe;
import dev.xkmc.l2rpgcommon.init.special.MagicRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class CraftMagic extends MagicProduct<Item, CraftMagic> {

	public CraftMagic(LLPlayerData player, NBTObj tag, ResourceLocation rl, IMagicRecipe r) {
		super(MagicRegistry.MPT_CRAFT.get(), player, tag, rl, r);
	}

}
