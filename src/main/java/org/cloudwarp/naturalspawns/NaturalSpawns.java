package org.cloudwarp.naturalspawns;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cloudwarp.naturalspawns.registry.*;
import org.cloudwarp.naturalspawns.utils.NSConfig;

public class NaturalSpawns implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MOD_ID = "naturalspawns";

	public static Identifier id (String path) {
		return new Identifier(MOD_ID, path);
	}
	public static ConfigHolder<NSConfig> configHolder;
	public static NSConfig loadedConfig;


	@Override
	public void onInitialize () {
		AutoConfig.register(NSConfig.class, Toml4jConfigSerializer::new);
		configHolder = AutoConfig.getConfigHolder(NSConfig.class);
		loadedConfig = getConfig();
		NSEventHandler.registerEvents();
		NSItems.registerItems();
	}

	public static NSConfig getConfig() {
		return configHolder.getConfig();
	}

	public static NbtCompound configToNBT(){
		NSConfig config = getConfig();
		NbtCompound nbt = new NbtCompound();
		// Items
		//nbt.putBoolean("enableSlingshot", config.doodadItems.enableSlingshot);

		return nbt;
	}
	public static NSConfig nbtToConfig(NbtCompound nbt){
		NSConfig config = new NSConfig();
		if(nbt == null){
			return config;
		}
		//Items
		//config.doodadItems.enableSlingshot = nbt.getBoolean("enableSlingshot");
		return config;
	}
}
