package dev.xkmc.l2rpgcommon.network.config;

import dev.xkmc.l2library.serial.SerialClass;
import dev.xkmc.l2library.serial.network.BaseConfig;
import dev.xkmc.l2library.util.code.Wrappers;
import dev.xkmc.l2rpgcommon.content.magic.render.SpellComponent;
import dev.xkmc.l2rpgcommon.network.ConfigType;
import dev.xkmc.l2rpgcommon.network.NetworkManager;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.HashMap;

@SerialClass
public class SpellEntityConfig extends BaseConfig {

	@SerialClass.SerialField
	public HashMap<String, SpellComponent> map = new HashMap<>();

	@Nullable
	public static SpellComponent getConfig(ResourceLocation rl) {
		SpellEntityConfig config = NetworkManager.getConfig(ConfigType.CONFIG_SPELL_ENTITY);
		return Wrappers.cast(config.map.get(rl.toString()));
	}

}
