package org.cloudwarp.quickstack;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cloudwarp.quickstack.networking.QuickStackMessages;
import org.cloudwarp.quickstack.registry.*;
import org.cloudwarp.quickstack.utils.QSConfig;

public class QuickStack implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MOD_ID = "quickstack";

	public static Identifier id (String path) {
		return new Identifier(MOD_ID, path);
	}
	public static ConfigHolder<QSConfig> configHolder;


	@Override
	public void onInitialize () {
		AutoConfig.register(QSConfig.class, Toml4jConfigSerializer::new);
		configHolder = AutoConfig.getConfigHolder(QSConfig.class);
		QSEventHandler.registerEvents();
		QuickStackMessages.registerC2S();
	}

	public static QSConfig getConfig() {
		return configHolder.getConfig();
	}
}
