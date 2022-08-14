package org.cloudwarp.naturalspawns.mixin;

import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.cloudwarp.naturalspawns.components.NSChunkComponent;
import org.cloudwarp.naturalspawns.registry.NSComponents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashSet;

@Mixin(Block.class)
public abstract class BlockMixin {

	@Inject(method = "onPlaced", at = @At("HEAD"))
	private void naturalSpawnsSetPlaced (World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack, CallbackInfo ci) {
		if(!world.isClient()) {
			NSChunkComponent chunkComponent = NSComponents.NATURAL_SPAWNS_CHUNK.get(world.getChunk(pos));
			HashSet<BlockPos> placed = chunkComponent.getPlaced();
			if (! placed.contains(pos)) {
				System.out.println("Placed");
				placed.add(pos);
				chunkComponent.save();
			}
		}
	}
	@Inject(method = "onBreak", at = @At("HEAD"))
	private void naturalSpawnsOnBreak(World world, BlockPos pos, BlockState state, PlayerEntity player, CallbackInfo ci){
		if(!world.isClient()) {
			NSChunkComponent chunkComponent = NSComponents.NATURAL_SPAWNS_CHUNK.get(world.getChunk(pos));
			HashSet<BlockPos> placed = chunkComponent.getPlaced();
			if (placed.contains(pos)) {
				placed.remove(pos);
				chunkComponent.save();
				System.out.println("Removed");
			}
		}
	}
}
