package org.cloudwarp.naturalspawns.registry;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import org.cloudwarp.naturalspawns.NaturalSpawns;

public class NSEventHandler {

	public static void registerEvents(){
		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
			PacketByteBuf data = PacketByteBufs.create();
			data.writeNbt(NaturalSpawns.configToNBT());
			ServerPlayNetworking.send(handler.player, NaturalSpawns.id("doodads_config_packet"), data);
		});
	}
}
