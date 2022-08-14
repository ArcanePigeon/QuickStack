package org.cloudwarp.naturalspawns.item;

import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBlockTags;
import net.minecraft.block.*;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.cloudwarp.naturalspawns.utils.NSItemTypes;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NaturalWandItem extends Item {
	public NSItemTypes type;


	public NaturalWandItem (Settings settings, NSItemTypes type) {
		super(settings);
		this.type = type;
	}


	@Override
	public void appendTooltip (ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		if (Screen.hasShiftDown()) {
			tooltip.add(new TranslatableText("item.doodads." + type.name + ".tooltip.shift"));
		} else {
			tooltip.add(new TranslatableText("item.doodads.generic_tooltip"));
		}
	}

	public ActionResult useOnBlock (ItemUsageContext context) {
		World world = context.getWorld();
		BlockPos pos = context.getBlockPos();
		BlockState state = world.getBlockState(pos);
		PlayerEntity player = context.getPlayer();
		ItemStack itemStack = context.getStack();
		boolean success = false;
		if(success){
			return ActionResult.SUCCESS;
		}
		return ActionResult.PASS;
	}

	public void playSound (World world, PlayerEntity player, BlockPos pos) {
		world.playSound(player, pos, SoundEvents.BLOCK_HONEY_BLOCK_BREAK, SoundCategory.BLOCKS, 0.65f, 0.8f);
	}
}

