package test;

import dev.xkmc.l2library.serial.SerialClass;
import dev.xkmc.l2library.serial.codec.PacketCodec;
import dev.xkmc.l2library.serial.codec.TagCodec;
import dev.xkmc.l2library.serial.network.BaseConfig;
import dev.xkmc.l2rpgcommon.init.data.configs.MagicRecipeGen;
import io.netty.buffer.Unpooled;
import net.minecraft.DetectedVersion;
import net.minecraft.SharedConstants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.Bootstrap;
import org.apache.logging.log4j.LogManager;

import java.util.HashMap;

public class Test {

	@SerialClass
	public static class R {

	}

	@SerialClass
	public static class T extends R {

		@SerialClass.SerialField
		public int a;

		@SerialClass.SerialField
		public String str = "";

		public T() {

		}

	}

	public static void main(String[] args) {
		SharedConstants.setVersion(DetectedVersion.BUILT_IN);
		Bootstrap.bootStrap();

		var buffer = new FriendlyByteBuf(Unpooled.buffer());
		T old = new T();
		old.str = "abc";
		PacketCodec.to(buffer, old);
		buffer = new FriendlyByteBuf(buffer.copy());
		T t = PacketCodec.from(buffer, T.class, null);
		System.out.println(t.str);
	}



}
