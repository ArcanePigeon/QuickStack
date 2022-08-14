package org.cloudwarp.naturalspawns.utils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.cloudwarp.naturalspawns.registry.NSItems;

import static org.cloudwarp.naturalspawns.NaturalSpawns.*;

public enum NSItemTypes {
	NATURAL_GLOVE(1,"natural_glove"),
	UNNATURAL_GLOVE(1,"unnatural_glove");

	public final int maxCount;
	public final Identifier id;
	public final String name;

	NSItemTypes (int maxCount, String name) {
		this.maxCount = maxCount;
		this.name = name;
		this.id = id(name);
	}
	public ItemStack itemStack (){
		return new ItemStack(this.item());
	}
	public Item item (){
		return (NSItems.get(this.name));
	}

}
