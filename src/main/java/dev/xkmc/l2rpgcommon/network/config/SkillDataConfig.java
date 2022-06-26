package dev.xkmc.l2rpgcommon.network.config;

import dev.xkmc.l2library.serial.SerialClass;
import dev.xkmc.l2library.serial.network.BaseConfig;
import dev.xkmc.l2library.util.annotation.DataGenOnly;
import dev.xkmc.l2library.util.code.Wrappers;
import dev.xkmc.l2rpgcommon.content.skill.internal.Skill;
import dev.xkmc.l2rpgcommon.content.skill.internal.SkillConfig;
import dev.xkmc.l2rpgcommon.network.ConfigType;
import dev.xkmc.l2rpgcommon.network.NetworkManager;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.HashMap;

@SerialClass
public class SkillDataConfig extends BaseConfig {

	@SerialClass.SerialField
	public HashMap<String, SkillConfig<?>> map = new HashMap<>();

	@Nullable
	public static <C extends SkillConfig<?>> C getConfig(ResourceLocation rl) {
		SkillDataConfig config = NetworkManager.getConfig(ConfigType.CONFIG_SKILL);
		return Wrappers.cast(config.map.get(rl.toString()));
	}

	@DataGenOnly
	public SkillDataConfig add(Skill<?,?> id, SkillConfig<?> config) {
		map.put(id.getID(), config);
		return this;
	}

}
