package org.cloudwarp.quickstack.networking;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import org.cloudwarp.quickstack.QuickStack;

public class QuickStackMessages {
	public static final Identifier QUICK_STACK_ID = QuickStack.id("quick_stack");
	public static final Identifier DUMP_ID = QuickStack.id("dump");
	public static void registerC2S(){
		ServerPlayNetworking.registerGlobalReceiver(QUICK_STACK_ID,QuickStackC2SPacket::receive);
		ServerPlayNetworking.registerGlobalReceiver(DUMP_ID,DumpC2SPacket::receive);
	}
	public static void registerS2C(){

	}
}
