package org.cloudwarp.quickstack.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import org.cloudwarp.quickstack.QuickStack;
import org.cloudwarp.quickstack.networking.QuickStackMessages;
import org.cloudwarp.quickstack.registry.QSKeyInputHandler;

@Environment(EnvType.CLIENT)
public class QuickStackClient implements ClientModInitializer {

	@Override
	public void onInitializeClient () {
		QSKeyInputHandler.init();
		QuickStackMessages.registerC2S();
	}
}
