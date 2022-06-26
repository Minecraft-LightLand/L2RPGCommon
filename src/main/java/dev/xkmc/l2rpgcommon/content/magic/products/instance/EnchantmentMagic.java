package dev.xkmc.l2rpgcommon.content.magic.products.instance;

import dev.xkmc.l2library.serial.NBTObj;
import dev.xkmc.l2rpgcommon.content.common.capability.player.LLPlayerData;
import dev.xkmc.l2rpgcommon.content.magic.products.MagicProduct;
import dev.xkmc.l2rpgcommon.content.magic.products.recipe.IMagicRecipe;
import dev.xkmc.l2rpgcommon.init.special.MagicRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;

public class EnchantmentMagic extends MagicProduct<Enchantment, EnchantmentMagic> {

	public EnchantmentMagic(LLPlayerData player, NBTObj nbtManager, ResourceLocation rl, IMagicRecipe<?> r) {
		super(MagicRegistry.MPT_ENCH.get(), player, nbtManager, rl, r);
	}

}
