package org.cloudwarp.quickstack.utils;

import io.netty.buffer.Unpooled;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.minecraft.network.PacketByteBuf;
import org.cloudwarp.quickstack.QuickStack;

@Config(name = QuickStack.MOD_ID)
public class QSConfig implements ConfigData {

	public boolean enableDumpFeature           = true;
	public boolean enableQuickStackFeature     = true;
	public boolean ignoreHotbar = true;
	@ConfigEntry.BoundedDiscrete(min = 0,max = 30)
	public int     searchRadius                = 5;
	@ConfigEntry.BoundedDiscrete(min = 0,max = 100)
	public int     maxContainersPerDump        = 50;
	public boolean enableQuickStackOutputInfo  = true;
	public boolean stopAfterFillingStacks      = false;
	//public boolean enableFavorites             = true;

	public PacketByteBuf writeConfig() {
		var buf = new PacketByteBuf(Unpooled.buffer());

		buf.writeBoolean(ignoreHotbar);
		buf.writeInt(searchRadius);
		buf.writeInt(maxContainersPerDump);
		buf.writeBoolean(enableQuickStackOutputInfo);
		buf.writeBoolean(stopAfterFillingStacks);

		return buf;
	}

	public static QSConfig readConfig(PacketByteBuf buf) {
		var config = new QSConfig();

		config.ignoreHotbar = buf.readBoolean();
		config.searchRadius = buf.readInt();
		config.maxContainersPerDump = buf.readInt();
		config.enableQuickStackOutputInfo = buf.readBoolean();
		config.stopAfterFillingStacks = buf.readBoolean();

		return config;
	}
}
