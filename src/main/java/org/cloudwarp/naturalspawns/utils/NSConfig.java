package org.cloudwarp.naturalspawns.utils;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import org.cloudwarp.naturalspawns.NaturalSpawns;

@Config(name = NaturalSpawns.MOD_ID)
public class NSConfig implements ConfigData {

		public boolean autoMarkBlocksUnnatural           = true;
		public boolean enableNaturalGlove                = true;
		public boolean enableUnnaturalGlove              = true;

}
