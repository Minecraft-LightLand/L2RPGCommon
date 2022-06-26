package dev.xkmc.l2rpgcommon.content.assassin.item;

import dev.xkmc.l2rpgcommon.init.registrate.LightlangEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class AssassinSwordItem extends SwordItem {

	public AssassinSwordItem(Tier tier, int damage, float speed, Properties props) {
		super(tier, damage, speed, props);
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity user) {
		if (!user.level.isClientSide()) {
			target.addEffect(new MobEffectInstance(LightlangEffects.T_CLEAR.get(), 100));
		}
		return super.hurtEnemy(stack, user, target);
	}
}
