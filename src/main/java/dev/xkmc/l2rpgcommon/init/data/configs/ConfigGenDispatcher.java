package dev.xkmc.l2rpgcommon.init.data.configs;

import dev.xkmc.l2library.serial.network.BaseConfig;
import dev.xkmc.l2library.serial.network.ConfigDataProvider;
import dev.xkmc.l2rpgcommon.network.ConfigType;
import net.minecraft.data.DataGenerator;

import java.util.Map;

public class ConfigGenDispatcher extends ConfigDataProvider {

	public ConfigGenDispatcher(DataGenerator generator) {
		super(generator, "data/l2rpgcommon/lightland_config/", "Light Json Config Generator");
	}

	@Override
	public void add(Map<String, BaseConfig> map) {
		ArmorConfigGen.addEnchant((id, config) -> map.put(ConfigType.ARMOR_ENCHANT.getID() + "/" + id, config));
		ArmorConfigGen.addWeight((id, config) -> map.put(ConfigType.ARMOR_WEIGHT.getID() + "/" + id, config));
		SkillConfigGen.addSkill((id, config) -> map.put(ConfigType.CONFIG_SKILL.getID() + "/" + id, config));
	}

}
