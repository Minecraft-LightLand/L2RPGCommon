package dev.xkmc.l2rpgcommon.content.skill.internal;

import dev.xkmc.l2library.base.NamedEntry;
import dev.xkmc.l2library.util.annotation.DoubleSidedCall;
import dev.xkmc.l2library.util.annotation.ServerOnly;
import dev.xkmc.l2rpgcommon.init.LightLand;
import dev.xkmc.l2rpgcommon.init.special.LightLandRegistry;
import dev.xkmc.l2rpgcommon.network.config.SkillDataConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public abstract class Skill<C extends SkillConfig<D>, D extends SkillData> extends NamedEntry<Skill<?, ?>> {

	public Skill() {
		super(LightLandRegistry.SKILL);
	}

	/**
	 * checked by client first. When client pass the check, check on server and then activate
	 */
	@DoubleSidedCall
	public boolean canActivate(Level level, Player player, D data) {
		return data.cooldown == 0 && getConfig() != null;
	}

	@ServerOnly
	public void activate(Level level, ServerPlayer player, D data) {
		data.cooldown = getConfig().getCooldown(data);
	}

	@DoubleSidedCall
	public C getConfig() {
		C c = SkillDataConfig.getConfig(getRegistryName());
		if (c != null && !c.isValid()) LightLand.LOGGER.error("skill " + getRegistryName() + " has invalid config");
		return c;
	}

	@ServerOnly
	public abstract D genData(Player player);

	public ResourceLocation getIcon() {
		ResourceLocation rl = getRegistryName();
		return new ResourceLocation(rl.getNamespace(), "textures/skill/" + rl.getPath() + ".png");
	}

	@DoubleSidedCall
	public void tick(Player player, D data) {
		if (data.cooldown > 0) {
			data.cooldown--;
		}
	}

}
