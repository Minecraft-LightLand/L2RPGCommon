package dev.xkmc.l2rpgcommon.network.packets;

import dev.xkmc.l2library.serial.SerialClass;
import dev.xkmc.l2magic.content.arcane.internal.ArcaneType;
import dev.xkmc.l2magic.content.magic.products.MagicElement;
import dev.xkmc.l2magic.init.special.MagicRegistry;
import dev.xkmc.l2rpgcommon.content.common.capability.player.AbilityPoints;
import dev.xkmc.l2rpgcommon.content.common.capability.player.LLPlayerData;
import dev.xkmc.l2rpgcommon.content.profession.Profession;
import dev.xkmc.l2rpgcommon.init.special.LightLandRegistry;
import dev.xkmc.l2rpgcommon.network.LLSerialPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;

import java.util.function.BiConsumer;

@SerialClass
public class CapToServer extends LLSerialPacket {

	public enum Action {
		DEBUG((handler, tag) -> {
			LogManager.getLogger().info("server: " + tag.getCompound("server"));
			LogManager.getLogger().info("client: " + tag.getCompound("client"));
		}),
		LEVEL((handler, tag) -> {
			AbilityPoints.LevelType.values()[tag.getInt("type")].doLevelUp(handler);
		}),
		PROFESSION((handler, tag) -> {
			Profession prof = LightLandRegistry.PROFESSION.get().getValue(new ResourceLocation(tag.getString("id")));
			if (prof == null)
				return;
			handler.abilityPoints.setProfession(prof);
		}),
		ELEMENTAL((handler, tag) -> {
			MagicElement elem = MagicRegistry.ELEMENT.get().getValue(new ResourceLocation(tag.getString("id")));
			if (elem == null)
				return;
			if (handler.abilityPoints.canLevelElement() && handler.getMagicHolder().addElementalMastery(elem))
				handler.abilityPoints.levelElement();
		}),
		ARCANE((handler, tag) -> {
			ArcaneType type = MagicRegistry.ARCANE_TYPE.get().getValue(new ResourceLocation(tag.getString("id")));
			if (type == null)
				return;
			if (handler.abilityPoints.canLevelArcane() && !handler.getMagicAbility().isArcaneTypeUnlocked(type)) {
				handler.getMagicAbility().unlockArcaneType(type, false);
			}
		});

		private final BiConsumer<LLPlayerData, CompoundTag> cons;

		Action(BiConsumer<LLPlayerData, CompoundTag> cons) {
			this.cons = cons;
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static void sendDebugInfo(CompoundTag s, CompoundTag c) {
		CompoundTag tag = new CompoundTag();
		tag.put("server", s);
		tag.put("client", c);
		new CapToServer(Action.DEBUG, tag).toServer();
	}

	@OnlyIn(Dist.CLIENT)
	public static void levelUpAbility(AbilityPoints.LevelType type) {
		CompoundTag tag = new CompoundTag();
		tag.putInt("type", type.ordinal());
		new CapToServer(Action.LEVEL, tag).toServer();
	}

	@OnlyIn(Dist.CLIENT)
	public static void setProfession(Profession prof) {
		CompoundTag tag = new CompoundTag();
		tag.putString("id", prof.getID());
		new CapToServer(Action.PROFESSION, tag).toServer();
	}

	@OnlyIn(Dist.CLIENT)
	public static void unlockArcaneType(ArcaneType type) {
		CompoundTag tag = new CompoundTag();
		tag.putString("id", type.getID());
		new CapToServer(Action.ARCANE, tag).toServer();
	}

	@OnlyIn(Dist.CLIENT)
	public static void addElemMastery(MagicElement elem) {
		CompoundTag tag = new CompoundTag();
		tag.putString("id", elem.getID());
		new CapToServer(Action.ELEMENTAL, tag).toServer();
	}

	@SerialClass.SerialField
	public Action action;
	@SerialClass.SerialField
	public CompoundTag tag;

	@Deprecated
	public CapToServer() {

	}

	private CapToServer(Action act, CompoundTag tag) {
		this.action = act;
		this.tag = tag;
	}

	public void handle(NetworkEvent.Context ctx) {
		ServerPlayer player = ctx.getSender();
		if (player == null || !player.isAlive())
			return;
		LLPlayerData handler = LLPlayerData.get(player);
		action.cons.accept(handler, tag);
	}

}
