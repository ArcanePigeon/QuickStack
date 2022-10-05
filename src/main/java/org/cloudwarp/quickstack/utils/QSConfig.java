package org.cloudwarp.quickstack.utils;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
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

}
