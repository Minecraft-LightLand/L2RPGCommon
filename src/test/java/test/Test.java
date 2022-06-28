package test;

import dev.xkmc.l2library.serial.SerialClass;
import dev.xkmc.l2library.serial.codec.PacketCodec;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;

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
		var buffer = new FriendlyByteBuf(Unpooled.buffer());
		T old = new T();
		old.str = "abc";
		PacketCodec.to(buffer, old);
		buffer = new FriendlyByteBuf(buffer.copy());
		T t = PacketCodec.from(buffer, T.class, null);
		System.out.println(t.str);
	}

}
