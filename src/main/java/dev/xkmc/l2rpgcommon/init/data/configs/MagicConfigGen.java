package dev.xkmc.l2rpgcommon.init.data.configs;

import dev.xkmc.l2library.serial.network.BaseConfig;
import dev.xkmc.l2rpgcommon.content.magic.products.info.TypeConfig;
import dev.xkmc.l2rpgcommon.init.LightLand;
import dev.xkmc.l2rpgcommon.init.registrate.LightlandItems;
import dev.xkmc.l2rpgcommon.init.special.MagicRegistry;
import dev.xkmc.l2rpgcommon.network.config.ProductTypeConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

import java.util.function.BiConsumer;

public class MagicConfigGen {

	public static void addProductType(BiConsumer<String, BaseConfig> adder) {
		adder.accept("default", new ProductTypeConfig()
				.add(MagicRegistry.MPT_ENCH.get(), new TypeConfig(Items.ENCHANTED_BOOK,
						new ResourceLocation(LightLand.MODID,
								"textures/block/ritual_iron.png")))
				.add(MagicRegistry.MPT_EFF.get(), new TypeConfig(Items.GLASS_BOTTLE,
						new ResourceLocation(LightLand.MODID,
								"textures/block/ritual_iron.png")))
				.add(MagicRegistry.MPT_ARCANE.get(), new TypeConfig(LightlandItems.ARCANE_AXE_GILDED.get(),
						new ResourceLocation(LightLand.MODID,
								"textures/block/ritual_iron.png")))
				.add(MagicRegistry.MPT_SPELL.get(), new TypeConfig(LightlandItems.SPELL_CARD.get(),
						new ResourceLocation(LightLand.MODID,
								"textures/block/ritual_iron.png")))
				.add(MagicRegistry.MPT_CRAFT.get(), new TypeConfig(Items.CRAFTING_TABLE,
						new ResourceLocation(LightLand.MODID,
								"textures/block/ritual_iron.png")))
		);
	}

}
