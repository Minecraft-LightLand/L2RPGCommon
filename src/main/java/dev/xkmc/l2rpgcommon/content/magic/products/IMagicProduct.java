package dev.xkmc.l2rpgcommon.content.magic.products;

import dev.xkmc.l2rpgcommon.init.LightLand;
import net.minecraft.resources.ResourceLocation;

public class IMagicProduct<I, P extends MagicProduct<I, P>> {

	public final MagicProductType<I, P> type;
	public final ResourceLocation rl;
	public final I item;

	public IMagicProduct(MagicProductType<I, P> type, ResourceLocation rl) {
		this.type = type;
		this.rl = rl;
		this.item = type.getter.apply(rl);
		if (item == null) {
			LightLand.LOGGER.error("magic product " + type.getRegistryName() + " does not have " + rl);
		}
	}

	public String getDescriptionID() {
		return type.namer.apply(item);
	}

	public static IMagicProduct<?, ?> getInstance(MagicProductType<?, ?> type, ResourceLocation rl) {
		return type.fac.get(null, null, rl, null);
	}

}
