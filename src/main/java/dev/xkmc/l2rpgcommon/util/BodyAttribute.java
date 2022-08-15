package dev.xkmc.l2rpgcommon.util;

import dev.xkmc.l2library.util.math.MathHelper;
import dev.xkmc.l2rpgcommon.content.common.capability.player.AbilityPoints;
import dev.xkmc.l2rpgcommon.init.special.LightLandRegistry;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

public class BodyAttribute {

	private static double sq(double v) {
		return v * (v + 1) / 2d;
	}

	private static double exp(double e, double v) {
		return Math.pow(e, v) - 1;
	}

	public static void resetModifiers(AbilityPoints ability, Player player) {
		for (Attr attr : Attr.values()) {
			AttributeInstance ins = player.getAttribute(attr.attr.get());
			if (ins != null) {
				ins.removeModifier(attr.id);
				ins.addTransientModifier(attr.get(ability));
			}
		}
	}

	public enum Attr {
		HEALTH("l2rpgcommon.health", () -> Attributes.MAX_HEALTH,
				AttributeModifier.Operation.ADDITION, a -> sq(a.health) * 2),
		ARMOR("l2rpgcommon.armor", () -> Attributes.ARMOR,
				AttributeModifier.Operation.ADDITION, a -> sq(a.health)),
		TOUGH("l2rpgcommon.toughness", () -> Attributes.ARMOR_TOUGHNESS,
				AttributeModifier.Operation.ADDITION, a -> sq(a.health)),
		DAMAGE("l2rpgcommon.damage", () -> Attributes.ATTACK_DAMAGE,
				AttributeModifier.Operation.ADDITION, a -> sq(a.strength)),
		ATK_SPEED("l2rpgcommon.atk_speed", () -> Attributes.ATTACK_SPEED,
				AttributeModifier.Operation.MULTIPLY_TOTAL, a -> exp(1.05, a.strength) + exp(1.05, a.speed)),
		MOVE_SPEED("l2rpgcommon.move_speed", () -> Attributes.MOVEMENT_SPEED,
				AttributeModifier.Operation.MULTIPLY_TOTAL, a -> exp(1.1, a.speed)),
		MANA("l2rpgcommon.mana", LightLandRegistry.MAX_MANA, AttributeModifier.Operation.ADDITION,
				a -> sq(a.parent.getMagicAbility().magic_level) * 100d),
		SPELL_LOAD("l2rpgcommon.spell_load", LightLandRegistry.MAX_SPELL_LOAD, AttributeModifier.Operation.ADDITION,
				a -> sq(a.parent.getMagicAbility().spell_load) * 100d);

		public final String name;
		public final UUID id;
		public final Supplier<Attribute> attr;
		public final AttributeModifier.Operation op;
		public final Function<AbilityPoints, Double> getter;

		Attr(String name, Supplier<Attribute> attr, AttributeModifier.Operation op, Function<AbilityPoints, Double> getter) {
			this.name = name;
			this.id = MathHelper.getUUIDFromString(name);
			this.attr = attr;
			this.op = op;
			this.getter = getter;
		}

		public AttributeModifier get(AbilityPoints ability) {
			return new AttributeModifier(id, name, getter.apply(ability), op);
		}

	}

}
