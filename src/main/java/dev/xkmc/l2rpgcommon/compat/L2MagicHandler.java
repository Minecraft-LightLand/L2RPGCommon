package dev.xkmc.l2rpgcommon.compat;

import dev.xkmc.l2magic.compat.api.MagicBehaviorListener;
import dev.xkmc.l2rpgcommon.content.common.capability.player.LLPlayerData;
import dev.xkmc.l2rpgcommon.content.common.capability.restriction.ArmorEnchant;
import net.minecraft.world.entity.player.Player;

import static dev.xkmc.l2magic.content.common.capability.MagicAbility.ENCHANT_FACTOR;

public class L2MagicHandler implements MagicBehaviorListener {
	@Override
	public int getArmorLoad(Player player) {
		return (int) Math.ceil(1.0 / ENCHANT_FACTOR * ArmorEnchant.getArmorEnchantLevel(player));
	}

	@Override
	public boolean doLevelArcane(Player player) {
		return LLPlayerData.get(player).abilityPoints.levelArcane();
	}

	@Override
	public boolean unlockAll() {
		return false;
	}

	@Override
	public double getDefaultMana() {
		return 100;
	}

	@Override
	public double getDefaultLoad() {
		return 100;
	}

	@Override
	public int getDefaultSpellSlot() {
		return 0;
	}

	@Override
	public double getDefaultManaRestore() {
		return 0.01;
	}

	@Override
	public double getDefaultLoadRestore() {
		return 0.01;
	}
}
