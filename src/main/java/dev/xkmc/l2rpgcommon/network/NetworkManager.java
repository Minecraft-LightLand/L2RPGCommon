package dev.xkmc.l2rpgcommon.network;

import dev.xkmc.l2library.serial.network.BaseConfig;
import dev.xkmc.l2library.serial.network.PacketHandlerWithConfig;
import dev.xkmc.l2rpgcommon.content.common.capability.restriction.ArmorEnchant;
import dev.xkmc.l2rpgcommon.content.common.capability.restriction.ArmorWeight;
import dev.xkmc.l2rpgcommon.init.LightLand;
import dev.xkmc.l2rpgcommon.network.config.SkillDataConfig;
import dev.xkmc.l2rpgcommon.network.packets.CapToClient;
import dev.xkmc.l2rpgcommon.network.packets.CapToServer;
import dev.xkmc.l2rpgcommon.network.packets.SkillToServer;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static net.minecraftforge.network.NetworkDirection.PLAY_TO_CLIENT;
import static net.minecraftforge.network.NetworkDirection.PLAY_TO_SERVER;

public class NetworkManager {

	static final PacketHandlerWithConfig HANDLER = new PacketHandlerWithConfig(
			new ResourceLocation(LightLand.MODID, "main"), 1, "lightland_config",
			e -> e.create(CapToClient.class, PLAY_TO_CLIENT),
			e -> e.create(CapToServer.class, PLAY_TO_SERVER),
			e -> e.create(SkillToServer.class, PLAY_TO_SERVER)
	);

	public static Stream<Map.Entry<String, BaseConfig>> getConfigs(ConfigType type) {
		return HANDLER.getConfigs(type.getID());
	}

	public static <T extends BaseConfig> T getConfig(ConfigType type) {
		return HANDLER.getCachedConfig(type.getID());
	}

	public static void register() {
		HANDLER.addCachedConfig(ConfigType.ARMOR_WEIGHT.getID(), s -> {
			List<ArmorWeight> list = s.map(e -> (ArmorWeight) e.getValue()).toList();
			ArmorWeight ans = new ArmorWeight();
			ans.entries = BaseConfig.overrideMap(list, e -> e.entries);
			ans.materials = BaseConfig.overrideMap(list, e -> e.materials);
			ans.suffixes = BaseConfig.collectList(list, e -> e.suffixes);
			return ans;
		});
		addSimpleMapConfig(ConfigType.ARMOR_ENCHANT, ArmorEnchant::new, e -> e.map, (e, map) -> e.map = map);
		addSimpleMapConfig(ConfigType.CONFIG_SKILL, SkillDataConfig::new, e -> e.map, (e, map) -> e.map = map);
	}

	@SuppressWarnings("unchecked")
	private static <C extends BaseConfig, K, T> void addSimpleMapConfig(ConfigType type,
																		Supplier<C> con,
																		Function<C, HashMap<K, T>> get,
																		BiConsumer<C, HashMap<K, T>> set) {
		HANDLER.addCachedConfig(type.getID(), s -> {
			List<C> list = s.map(e -> (C) e.getValue()).toList();
			C ans = con.get();
			set.accept(ans, BaseConfig.overrideMap(list, get));
			return ans;
		});
	}

}
