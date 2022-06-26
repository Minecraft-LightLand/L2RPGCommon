package dev.xkmc.l2rpgcommon.network;

import dev.xkmc.l2library.serial.network.BaseConfig;
import dev.xkmc.l2library.serial.network.PacketHandlerWithConfig;
import dev.xkmc.l2rpgcommon.init.LightLand;
import dev.xkmc.l2rpgcommon.network.packets.CapToClient;
import dev.xkmc.l2rpgcommon.network.packets.CapToServer;
import dev.xkmc.l2rpgcommon.network.packets.EmptyRightClickToServer;
import dev.xkmc.l2rpgcommon.network.packets.SkillToServer;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;
import java.util.stream.Stream;

import static net.minecraftforge.network.NetworkDirection.PLAY_TO_CLIENT;
import static net.minecraftforge.network.NetworkDirection.PLAY_TO_SERVER;

public class NetworkManager {

	static final PacketHandlerWithConfig HANDLER = new PacketHandlerWithConfig(
			new ResourceLocation(LightLand.MODID, "main"), 1, "lightland_config",
			e -> e.create(CapToClient.class, PLAY_TO_CLIENT),
			e -> e.create(CapToServer.class, PLAY_TO_SERVER),
			e -> e.create(EmptyRightClickToServer.class, PLAY_TO_SERVER),
			e -> e.create(SkillToServer.class, PLAY_TO_SERVER)
	);

	public static Stream<Map.Entry<String, BaseConfig>> getConfigs(ConfigType type) {
		return HANDLER.getConfigs(type.getID());
	}

	public static <T extends BaseConfig> T getConfig(ConfigType type) {
		return HANDLER.getCachedConfig(type.getID());
	}

	public static void register() {

	}

}
