package org.cloudwarp.naturalspawns.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import org.cloudwarp.naturalspawns.NaturalSpawns;

@Environment(EnvType.CLIENT)
public class NaturalSpawnsClient implements ClientModInitializer {

	@Override
	public void onInitializeClient () {
		ClientPlayNetworking.registerGlobalReceiver(NaturalSpawns.id("doodads_config_packet"), (client, networkHandler, data, sender) -> {
			NbtCompound tag = data.readNbt();
			client.execute(() -> NaturalSpawns.loadedConfig = NaturalSpawns.nbtToConfig(tag));
		});
	}
}
