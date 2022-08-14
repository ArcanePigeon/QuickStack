package org.cloudwarp.naturalspawns.registry;

import dev.onyxstudios.cca.api.v3.chunk.ChunkComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.chunk.ChunkComponentInitializer;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import org.cloudwarp.naturalspawns.NaturalSpawns;
import org.cloudwarp.naturalspawns.components.NSChunkComponent;

public class NSComponents implements ChunkComponentInitializer {
	public static final ComponentKey<NSChunkComponent> NATURAL_SPAWNS_CHUNK = ComponentRegistry.getOrCreate(NaturalSpawns.id("ns_chunk"), NSChunkComponent.class);

	@Override
	public void registerChunkComponentFactories (ChunkComponentFactoryRegistry registry) {
		registry.register(NATURAL_SPAWNS_CHUNK, NSChunkComponent::new);
	}
}
