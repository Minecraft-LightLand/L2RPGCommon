package dev.xkmc.l2rpgcommon.network.config;

import dev.xkmc.l2library.serial.SerialClass;
import dev.xkmc.l2library.serial.network.BaseConfig;
import dev.xkmc.l2library.util.annotation.DataGenOnly;
import dev.xkmc.l2rpgcommon.content.magic.products.MagicProductType;
import dev.xkmc.l2rpgcommon.content.magic.products.info.TypeConfig;
import dev.xkmc.l2rpgcommon.network.ConfigType;
import dev.xkmc.l2rpgcommon.network.NetworkManager;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.HashMap;

@SerialClass
public class ProductTypeConfig extends BaseConfig {

	@SerialClass.SerialField
	public HashMap<String, TypeConfig> map = new HashMap<>();

	@Nullable
	@SuppressWarnings({"unsafe"})
	public static TypeConfig getConfig(ResourceLocation rl) {
		ProductTypeConfig base = NetworkManager.getConfig(ConfigType.PRODUCT_TYPE);
		return base.map.get(rl.toString());
	}

	@DataGenOnly
	public ProductTypeConfig add(MagicProductType<?, ?> type, TypeConfig config) {
		map.put(type.getID(), config);
		return this;
	}

}
