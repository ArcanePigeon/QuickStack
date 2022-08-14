package org.cloudwarp.naturalspawns.registry;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.cloudwarp.naturalspawns.NaturalSpawns;
import org.cloudwarp.naturalspawns.trinket.*;
import org.cloudwarp.naturalspawns.utils.NSItemTypes;

import java.util.*;

public class NSItems {
	public static final ItemGroup NAUTRAL_SPAWNS_GROUP = FabricItemGroupBuilder.create(
					new Identifier(NaturalSpawns.MOD_ID, "general"))
			.icon(() -> NSItemTypes.NATURAL_GLOVE.itemStack())
			.build();
	public static final HashMap<String, Item> ITEMS = new HashMap<>();
	public static final HashSet<Identifier> ENABLED_ITEMS = new HashSet<>();
	static Random rand = new Random();


	private static Item registerItem (boolean configEntry, String id, Item item) {
		if(configEntry){
			ENABLED_ITEMS.add(NaturalSpawns.id(id));
		}
		return ITEMS.put(id, Registry.register(Registry.ITEM, NaturalSpawns.id(id), item));
	}
	public static void registerItems () {
		if (! ITEMS.isEmpty()) {
			return;}

		registerItem(NaturalSpawns.loadedConfig.enableNaturalGlove, NSItemTypes.NATURAL_GLOVE.name, new NaturalGlove(new Item.Settings().group(NAUTRAL_SPAWNS_GROUP)));
		registerItem(NaturalSpawns.loadedConfig.enableUnnaturalGlove, NSItemTypes.UNNATURAL_GLOVE.name, new UnnaturalGlove(new Item.Settings().group(NAUTRAL_SPAWNS_GROUP)));

	}

	public static Item get (String id) {
		return ITEMS.getOrDefault(id, Items.AIR);
	}

}
