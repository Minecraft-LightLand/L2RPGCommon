package dev.xkmc.l2rpgcommon.content.questline.block;

import dev.xkmc.l2rpgcommon.content.arcane.internal.ArcaneItemUseHelper;
import dev.xkmc.l2rpgcommon.content.arcane.internal.IArcaneItem;
import dev.xkmc.l2rpgcommon.init.registrate.LLBlocks;
import dev.xkmc.l2rpgcommon.init.registrate.LLItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class LaylineChargerBlock extends Block {

	public LaylineChargerBlock(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
		ItemStack stack = player.getItemInHand(hand);
		BlockState below = level.getBlockState(pos.below());
		if (below.is(LLBlocks.LAYROOT_HEAD.get())) {
			if (stack.is(LLItems.LAYLINE_ORB.get())) {
				stack.shrink(1);
				level.setBlockAndUpdate(pos.below(), LLBlocks.LAYLINE_HEAD.getDefaultState());
				return InteractionResult.SUCCESS;
			}
			if (stack.getItem() instanceof IArcaneItem) {
				if (ArcaneItemUseHelper.getArcaneMana(stack) >= 20) {
					ArcaneItemUseHelper.addArcaneMana(stack, -20);
					level.setBlockAndUpdate(pos.below(), LLBlocks.LAYLINE_HEAD.getDefaultState());
					return InteractionResult.SUCCESS;
				}
				return InteractionResult.FAIL;
			}
		}
		return InteractionResult.PASS;
	}

}
