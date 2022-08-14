package dev.xkmc.l2rpgcommon.content.common.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.GuiOverlayManager;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;

public class ItemNameOverlay implements IGuiOverlay {
	@Override
	public void render(ForgeGui gui, PoseStack mStack, float partialTicks, int width, int height) {
		mStack.pushPose();
		mStack.translate(0, -30, 0);
		GuiOverlayManager.findOverlay(VanillaGuiOverlay.ITEM_NAME.id()).overlay()
				.render(gui, mStack, partialTicks, width, height);
		mStack.popPose();
	}
}
