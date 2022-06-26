package dev.xkmc.l2rpgcommon.content.magic.products.instance;

import dev.xkmc.l2library.serial.NBTObj;
import dev.xkmc.l2rpgcommon.content.common.capability.player.LLPlayerData;
import dev.xkmc.l2rpgcommon.content.magic.products.MagicProduct;
import dev.xkmc.l2rpgcommon.content.magic.products.recipe.IMagicRecipe;
import dev.xkmc.l2rpgcommon.content.magic.spell.internal.Spell;
import dev.xkmc.l2rpgcommon.init.special.MagicRegistry;
import net.minecraft.resources.ResourceLocation;

public class SpellMagic extends MagicProduct<Spell<?, ?>, SpellMagic> {

	public SpellMagic(LLPlayerData player, NBTObj tag, ResourceLocation rl, IMagicRecipe r) {
		super(MagicRegistry.MPT_SPELL.get(), player, tag, rl, r);
	}

}
