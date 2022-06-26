package dev.xkmc.l2rpgcommon.content.profession.prof;

import dev.xkmc.l2rpgcommon.content.common.capability.player.LLPlayerData;
import dev.xkmc.l2rpgcommon.content.profession.Profession;
import dev.xkmc.l2rpgcommon.init.special.MagicRegistry;

public class ArcaneProfession extends Profession {

	@Override
	public void init(LLPlayerData handler) {
		handler.abilityPoints.arcane += 2;
		handler.abilityPoints.general += 2;
		handler.magicHolder.addElementalMastery(MagicRegistry.ELEM_QUINT.get());
		handler.abilityPoints.element++;
	}

	@Override
	public void levelUp(LLPlayerData handler) {
		handler.abilityPoints.general++;
		if (handler.abilityPoints.level <= 10)
			handler.abilityPoints.general++;
		handler.abilityPoints.element++;
	}

}
