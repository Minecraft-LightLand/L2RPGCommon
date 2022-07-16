package dev.xkmc.l2rpgcommon.init;

import dev.xkmc.l2library.serial.codec.PacketCodec;
import dev.xkmc.l2library.serial.codec.TagCodec;
import dev.xkmc.l2library.serial.network.BaseConfig;
import dev.xkmc.l2rpgcommon.content.magic.products.recipe.DefMagicRecipe;
import dev.xkmc.l2rpgcommon.init.data.configs.MagicRecipeGen;
import io.netty.buffer.Unpooled;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import java.util.HashMap;

public class UnitTest {

	public static void test() {
		HashMap<String, BaseConfig> map = new HashMap<>();
		MagicRecipeGen.addRecipe(map::put);
		map.forEach((str, config) -> {
			try {
				DefMagicRecipe obj = (DefMagicRecipe) config;
				CompoundTag reference = TagCodec.toTag(new CompoundTag(), obj);
				LogManager.getLogger().info(reference.toString());

				FriendlyByteBuf test = new FriendlyByteBuf(Unpooled.buffer());
				PacketCodec.to(test, obj);
				FriendlyByteBuf reversed = new FriendlyByteBuf(test.copy());
				Object packet = PacketCodec.from(reversed, obj.getClass(), null);
				CompoundTag parsed = TagCodec.toTag(new CompoundTag(), packet);
				if (!reference.equals(parsed)) {
					LogManager.getLogger().error("parsed tag not equal for " + str);
					LogManager.getLogger().error(reference.toString());
					LogManager.getLogger().error(parsed.toString());
				} else {
					LogManager.getLogger().error("parsed tag equal for " + str);
				}
			} catch (Exception e) {
				LogManager.getLogger().throwing(Level.ERROR, e);
			}
		});
	}

}
