package org.cloudwarp.naturalspawns.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import org.cloudwarp.naturalspawns.registry.NSProperties;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
	public abstract class BlockMixin {
	@Shadow protected abstract void setDefaultState (BlockState state);

	@Shadow @Final protected StateManager<Block, BlockState> stateManager;
	private static BooleanProperty NS_NATURAL = NSProperties.NS_NATURAL;

	@ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;appendProperties(Lnet/minecraft/state/StateManager$Builder;)V"))
	private StateManager.Builder<Block, BlockState> naturalSpawnsAppendNaturalProperty(StateManager.Builder<Block, BlockState> builder){
		return builder.add(NS_NATURAL);
	}
	@ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;setDefaultState(Lnet/minecraft/block/BlockState;)V"))
	private BlockState naturalSpawnsSetNaturalDefaultState(BlockState state){
		return state.with(NS_NATURAL,false);
	}
	@Inject(method = "getPlacementState", at = @At("RETURN"), cancellable = true)
	private void naturalSpawnsSetNaturalState(ItemPlacementContext ctx, CallbackInfoReturnable<BlockState> cir){
		if(ctx.getPlayer() != null){
			cir.setReturnValue(cir.getReturnValue().with(NS_NATURAL, true));
		}
	}
}
