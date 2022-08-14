package org.cloudwarp.naturalspawns.components;

import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;

import java.util.HashSet;

public class NSChunkComponent implements ComponentV3 {
	private final Chunk chunk;
	public NSChunkComponent(Chunk chunk){
		this.chunk = chunk;
	}
	public HashSet<BlockPos> placed = new HashSet<>();
	public HashSet<BlockPos> getPlaced(){
		return placed;
	}
	@Override
	public void readFromNbt (NbtCompound tag) {
		for(String key: tag.getKeys()){
			placed.add(BlockPos.fromLong(tag.getLong(key)));
		}
		if(placed.size() > 0) {
			System.out.println("B" + placed.size());
		}
	}

	@Override
	public void writeToNbt (NbtCompound tag) {
		placed.forEach(blockPos -> {
			tag.putLong(blockPos.toShortString(), blockPos.asLong());
		});
		if(placed.size() > 0) {
			System.out.println("A" + placed.size());
		}
	}
	public void save(){
		System.out.println("C" + placed.size());
		this.chunk.setNeedsSaving(true);
	}
}
