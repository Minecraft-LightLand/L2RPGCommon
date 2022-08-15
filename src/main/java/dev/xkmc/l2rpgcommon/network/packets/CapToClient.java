package dev.xkmc.l2rpgcommon.network.packets;

import dev.xkmc.l2library.serial.SerialClass;
import dev.xkmc.l2library.serial.codec.TagCodec;
import dev.xkmc.l2library.util.Proxy;
import dev.xkmc.l2library.util.code.Wrappers;
import dev.xkmc.l2magic.content.common.capability.MagicAbility;
import dev.xkmc.l2rpgcommon.content.common.capability.player.AbilityPoints;
import dev.xkmc.l2rpgcommon.content.common.capability.player.CapProxy;
import dev.xkmc.l2rpgcommon.content.common.capability.player.LLPlayerData;
import dev.xkmc.l2rpgcommon.content.common.capability.player.SkillCap;
import dev.xkmc.l2rpgcommon.network.LLSerialPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Consumer;
import java.util.function.Function;

@SerialClass
public class CapToClient extends LLSerialPacket {

	@SerialClass.SerialField
	public Action action;

	@SerialClass.SerialField
	public CompoundTag tag;

	@Deprecated
	public CapToClient() {

	}

	public CapToClient(Action action, LLPlayerData handler) {
		this.action = action;
		this.tag = action.server.apply(handler);
	}

	public void handle(NetworkEvent.Context context) {
		if (action != Action.ALL && action != Action.CLONE && !Proxy.getClientPlayer().isAlive())
			return;
		action.client.accept(tag);
	}

	public static void reset(ServerPlayer e, LLPlayerData.Reset reset) {
		CapToClient msg = new CapToClient(Action.RESET, null);
		msg.tag.putInt("ordinal", reset.ordinal());
		msg.toClientPlayer(e);
	}

	public enum Action {
		DEBUG((m) -> TagCodec.toTag(new CompoundTag(), m), (tag) -> {
			LLPlayerData m = CapProxy.getHandler();
			CompoundTag comp = Wrappers.get(() -> TagCodec.toTag(new CompoundTag(), LLPlayerData.class, m, f -> true));
			CapToServer.sendDebugInfo(tag, comp);
		}),
		ALL((m) -> {
			m.getMagicAbility().time_after_sync = 0;
			return TagCodec.toTag(new CompoundTag(), m);
		}, tag -> LLPlayerData.HOLDER.cacheSet(tag, false)),
		CLONE((m) -> {
			m.getMagicAbility().time_after_sync = 0;
			return TagCodec.toTag(new CompoundTag(), m);
		}, tag -> LLPlayerData.HOLDER.cacheSet(tag, true)),
		ARCANE_TYPE((m) -> m.getMagicAbility().arcane_type, (tag) -> {
			MagicAbility abi = CapProxy.getHandler().getMagicAbility();
			abi.arcane_type = tag;
		}),
		ABILITY_POINT((m) -> TagCodec.toTag(new CompoundTag(), m.abilityPoints), (tag) -> {
			LLPlayerData h = CapProxy.getHandler();
			h.abilityPoints = new AbilityPoints(h);
			Wrappers.run(() -> TagCodec.fromTag(tag, AbilityPoints.class, h.abilityPoints, f -> true));
			h.reInit();
		}),
		RESET(m -> new CompoundTag(), tag -> {
			LLPlayerData h = CapProxy.getHandler();
			h.reset(LLPlayerData.Reset.values()[tag.getInt("ordinal")]);
		}),
		SKILL(m -> TagCodec.toTag(new CompoundTag(), m.skillCap), tag -> {
			LLPlayerData h = CapProxy.getHandler();
			h.skillCap = new SkillCap(h);
			Wrappers.run(() -> TagCodec.fromTag(tag, SkillCap.class, h.skillCap, f -> true));
		});

		public final Function<LLPlayerData, CompoundTag> server;
		public final Consumer<CompoundTag> client;


		Action(Function<LLPlayerData, CompoundTag> server, Consumer<CompoundTag> client) {
			this.server = server;
			this.client = client;
		}
	}

}
