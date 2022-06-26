package dev.xkmc.l2rpgcommon.content.profession.prof;

import dev.xkmc.l2rpgcommon.content.common.capability.player.LLPlayerData;
import dev.xkmc.l2rpgcommon.content.profession.Profession;

public abstract class CombatProfession extends Profession {

	@Override
	public final void init(LLPlayerData handler) {
		handler.abilityPoints.general++;
		handler.abilityPoints.body += 3;
	}

	@Override
	public final void levelUp(LLPlayerData handler) {
		handler.abilityPoints.general++;
		if (handler.abilityPoints.level <= 10)
			handler.abilityPoints.body++;
		if (handler.abilityPoints.level % 2 == 0)
			handler.abilityPoints.element++;
	}

}
