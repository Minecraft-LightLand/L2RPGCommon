package dev.xkmc.l2rpgcommon.content.profession.prof;

import dev.xkmc.l2magic.init.special.MagicRegistry;
import dev.xkmc.l2rpgcommon.content.common.capability.player.LLPlayerData;
import dev.xkmc.l2rpgcommon.content.profession.Profession;

public class MagicianProfession extends Profession {

	@Override
	public void init(LLPlayerData handler) {
		handler.getMagicHolder().addElementalMastery(MagicRegistry.ELEM_EARTH.get());
		handler.getMagicHolder().addElementalMastery(MagicRegistry.ELEM_WATER.get());
		handler.getMagicHolder().addElementalMastery(MagicRegistry.ELEM_AIR.get());
		handler.getMagicHolder().addElementalMastery(MagicRegistry.ELEM_FIRE.get());
		handler.getMagicAbility().spell_level += 2;
		handler.abilityPoints.magic++;
		handler.abilityPoints.general++;
	}

	@Override
	public void levelUp(LLPlayerData handler) {
		handler.abilityPoints.general++;
		handler.abilityPoints.magic++;
		handler.abilityPoints.element++;
	}

}
