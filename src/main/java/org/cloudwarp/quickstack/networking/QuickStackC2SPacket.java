package org.cloudwarp.quickstack.networking;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.cloudwarp.quickstack.registry.QuickStackDepositFunctions;

public class QuickStackC2SPacket {
	public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender){
		server.execute(() -> {
			QuickStackDepositFunctions.findNearbyChests(player, true);
			return;
		});
	}
}
