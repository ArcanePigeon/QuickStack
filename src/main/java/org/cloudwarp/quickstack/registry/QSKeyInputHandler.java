package org.cloudwarp.quickstack.registry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.PacketByteBuf;
import org.cloudwarp.quickstack.QuickStack;
import org.cloudwarp.quickstack.networking.QuickStackMessages;
import org.cloudwarp.quickstack.utils.QSConfig;
import org.lwjgl.glfw.GLFW;

import java.awt.im.InputContext;

@Environment(EnvType.CLIENT)
public class QSKeyInputHandler {
	public static final String KEY_CATEGORY_QUICK_STACK = "key.category.quickstack";
	public static final String KEY_QUICK_STACK = "key.quickstack.quick_stack";
	public static final String KEY_DUMP = "key.quickstack.dump";
	public static KeyBinding quickStackKey;
	public static KeyBinding dumpKey;

	public static void registerKeyInputs () {
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.player != null) {
				if (quickStackKey.wasPressed()) {
					client.execute(() -> ClientPlayNetworking.send(QuickStackMessages.QUICK_STACK_ID, PacketByteBufs.empty()));
				}
				if (dumpKey.wasPressed()) {
					client.execute(() -> ClientPlayNetworking.send(QuickStackMessages.DUMP_ID, PacketByteBufs.empty()));
				}
			}
		});
	}

	public static void init () {
		quickStackKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				KEY_QUICK_STACK,
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_V,
				KEY_CATEGORY_QUICK_STACK
		));
		dumpKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				KEY_DUMP,
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_B,
				KEY_CATEGORY_QUICK_STACK
		));
		registerKeyInputs();
	}
}
