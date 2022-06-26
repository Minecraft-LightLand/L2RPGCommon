package dev.xkmc.l2rpgcommon.network.config;

import dev.xkmc.l2library.serial.network.BaseConfig;
import dev.xkmc.l2library.serial.SerialClass;
import dev.xkmc.l2rpgcommon.content.magic.spell.internal.SpellConfig;
import dev.xkmc.l2rpgcommon.network.NetworkManager;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Objects;

@SerialClass
public class SpellDataConfig extends BaseConfig {

	@SerialClass.SerialField
	public HashMap<String, SpellConfig> map = new HashMap<>();

	@Nullable
	@SuppressWarnings({"unchecked", "unsafe"})
	public static <C extends SpellConfig> C getConfig(ResourceLocation rl) {
		return (C) NetworkManager.getConfigs("config_spell")
				.map(e -> ((SpellDataConfig) e.getValue()).map.get(rl.toString()))
				.filter(Objects::nonNull).findFirst().orElse(null);

	}

}
