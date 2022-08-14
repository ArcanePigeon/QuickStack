package org.cloudwarp.naturalspawns.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import org.cloudwarp.naturalspawns.registry.NSProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin {
	@Inject(method = "canMobSpawn", at = @At("RETURN"), cancellable = true)
	private static void unnaturalSpawnCheck (EntityType<? extends MobEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random, CallbackInfoReturnable<Boolean> cir){
		if(!world.getBlockState(pos).get(NSProperties.NS_NATURAL)){
			cir.setReturnValue(false);
		}
	}
}
