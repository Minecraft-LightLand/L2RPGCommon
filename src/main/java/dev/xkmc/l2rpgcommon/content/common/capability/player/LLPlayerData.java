package dev.xkmc.l2rpgcommon.content.common.capability.player;

import dev.xkmc.l2library.capability.player.PlayerCapabilityHolder;
import dev.xkmc.l2library.capability.player.PlayerCapabilityNetworkHandler;
import dev.xkmc.l2library.capability.player.PlayerCapabilityTemplate;
import dev.xkmc.l2library.serial.SerialClass;
import dev.xkmc.l2magic.content.common.capability.MagicAbility;
import dev.xkmc.l2magic.content.common.capability.MagicData;
import dev.xkmc.l2magic.content.common.capability.MagicHolder;
import dev.xkmc.l2rpgcommon.init.LightLand;
import dev.xkmc.l2rpgcommon.network.packets.CapToClient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

import java.util.function.Consumer;

@SerialClass
public class LLPlayerData extends PlayerCapabilityTemplate<LLPlayerData> {

	public static final Capability<LLPlayerData> CAPABILITY = CapabilityManager.get(new CapabilityToken<LLPlayerData>() {
	});

	public static final PlayerCapabilityHolder<LLPlayerData> HOLDER = new PlayerCapabilityHolder<>(
			new ResourceLocation(LightLand.MODID, "player_data"), CAPABILITY,
			LLPlayerData.class, LLPlayerData::new, holder -> new PlayerCapabilityNetworkHandler<>(holder) {

		@Override
		public void toClientSyncAll(ServerPlayer e) {
			new CapToClient(CapToClient.Action.ALL, HOLDER.get(e)).toClientPlayer(e);
		}

		@Override
		public void toClientSyncClone(ServerPlayer e) {
			new CapToClient(CapToClient.Action.CLONE, HOLDER.get(e)).toClientPlayer(e);
		}
	});

	public static LLPlayerData get(Player player) {
		return HOLDER.get(player);
	}

	public static boolean isProper(Player player) {
		return HOLDER.isProper(player);
	}

	public static void register() {

	}

	@SerialClass.SerialField
	public State state = State.PREINJECT;
	@SerialClass.SerialField
	public AbilityPoints abilityPoints = new AbilityPoints(this);
	@SerialClass.SerialField
	public SkillCap skillCap = new SkillCap(this);

	public void tick() {
		skillCap.tick();
	}

	public void reset(Reset reset) {
		reset.cons.accept(this);
	}

	public void init() {
		if (state == null) {
			reset(Reset.FOR_INJECT);
		}
		if (state != State.ACTIVE) {
			abilityPoints.updateAttribute();
			state = State.ACTIVE;
		}
	}

	public void reInit() {
		state = State.PREINIT;
		check();
	}

	public LLPlayerData check() {
		if (state != State.ACTIVE)
			init();
		return this;
	}

	@Override
	public void onClone(boolean isWasDeath) {
		if (isWasDeath) {
			skillCap.list.forEach(e -> e.data.cooldown = 0);
		}
	}

	@Deprecated
	@Override
	public void preInject() {
		reset(Reset.FOR_INJECT);
	}

	@Deprecated
	@SuppressWarnings("unused")
	@SerialClass.OnInject
	public void onInject() {
		if (state == State.PREINJECT || state == State.ACTIVE)
			state = State.PREINIT;
	}

	public MagicAbility getMagicAbility() {
		return MagicData.get(player).magicAbility;
	}

	public MagicHolder getMagicHolder() {
		return MagicData.get(player).magicHolder;
	}

	public enum State {
		PREINJECT, PREINIT, ACTIVE
	}

	public enum Reset {
		ABILITY((h) -> {
			h.abilityPoints = new AbilityPoints(h);
			h.abilityPoints.updateAttribute();
		}),
		SKILL(h -> {
			h.skillCap = new SkillCap(h);
		}),
		ALL((h) -> {
			ABILITY.cons.accept(h);
			SKILL.cons.accept(h);
		}), FOR_INJECT((h) -> {
			h.state = State.PREINJECT;
			h.abilityPoints = new AbilityPoints(h);
			h.skillCap = new SkillCap(h);
		});

		final Consumer<LLPlayerData> cons;

		Reset(Consumer<LLPlayerData> cons) {
			this.cons = cons;
		}
	}

}
