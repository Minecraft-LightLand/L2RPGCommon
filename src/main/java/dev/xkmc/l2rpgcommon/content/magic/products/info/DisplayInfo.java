package dev.xkmc.l2rpgcommon.content.magic.products.info;

import dev.xkmc.l2library.serial.SerialClass;
import net.minecraft.advancements.FrameType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public record DisplayInfo(int x, int y, FrameType type, Item icon) {

	public static final float SCALE = 1;

	public float getX() {
		return x * SCALE;
	}

	public float getY() {
		return y * SCALE;
	}

	public FrameType getFrame() {
		return type;
	}

	public ItemStack getIcon() {
		return icon.getDefaultInstance();
	}
}
