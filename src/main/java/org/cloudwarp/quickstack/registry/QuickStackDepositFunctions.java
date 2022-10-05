package org.cloudwarp.quickstack.registry;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.cloudwarp.quickstack.QuickStack;
import org.cloudwarp.quickstack.utils.QSConfig;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class QuickStackDepositFunctions {
	private static Set<Identifier> chestBlocks = new HashSet<>();
	public static void findNearbyChests (ServerPlayerEntity player, boolean quickStack) {
		World world = player.getWorld();
		Inventory playerInventory = player.getInventory();
		chestBlocks = new HashSet<>();
		QSConfig config = QuickStack.getConfig();
		AtomicInteger chestsDepositedInto = new AtomicInteger();
		AtomicInteger itemsDeposited = new AtomicInteger();
		BlockPos.iterateOutwards(player.getBlockPos(),config.searchRadius,config.searchRadius,config.searchRadius).forEach(blockPos -> {
			if(chestBlocks.size() >= config.maxContainersPerDump){
				return;
			}
			Block block = world.getBlockState(blockPos).getBlock();
			if (! (block instanceof AbstractChestBlock)) {
				return;
			}
			addChestBlock(block);
			Inventory inventory = getInventoryAt(world, blockPos.getX(), blockPos.getY(), blockPos.getZ());
			if(inventory == null){
				return;
			}
			if (isInventoryFull(inventory)) {
				return;
			}
			boolean didDeposit = false;
			for (int j = 0; j < playerInventory.size(); ++ j) {
				if (config.ignoreHotbar && j < 9) {
					continue;
				}
				if (! playerInventory.getStack(j).isEmpty()) {
					ItemStack itemStack = playerInventory.getStack(j).copy();
					Set<Item> itemSet = new HashSet<>();
					itemSet.add(itemStack.getItem());
					if(quickStack && !inventory.containsAny(itemSet)){
						continue;
					}
					ItemStack itemStack2 = transfer(playerInventory, inventory, playerInventory.removeStack(j, itemStack.getCount()));
					//markDirty(world,blockPos,world.getBlockState(blockPos));
					if (itemStack2.isEmpty()) {
						inventory.markDirty();
						itemsDeposited.addAndGet((itemStack.getCount() - itemStack2.getCount()));
						didDeposit = true;
						continue;
					}
					itemsDeposited.addAndGet((itemStack.getCount() - itemStack2.getCount()));
					didDeposit = true;

					playerInventory.setStack(j, itemStack);
				}
			}
			if(didDeposit){
				chestsDepositedInto.addAndGet(1);
			}
		});
		if(config.enableQuickStackOutputInfo) {
			String message = (itemsDeposited.toString() + "  items deposited into " + chestsDepositedInto.toString() + " chests");
			player.sendMessage(Text.literal(message), false);
		}
	}

	public static ItemStack transfer (@Nullable Inventory from, Inventory to, ItemStack stack) {
		int j = to.size();
		for (int k = 0; k < j && ! stack.isEmpty(); ++ k) {
			stack = transfer(from, to, stack, k);
		}
		return stack;
	}

	private static ItemStack transfer (@Nullable Inventory from, Inventory to, ItemStack stack, int slot) {
		ItemStack itemStack = to.getStack(slot);
		if (canInsert(to, stack, slot)) {
			boolean bl = false;
			if (itemStack.isEmpty()) {
				to.setStack(slot, stack);
				stack = ItemStack.EMPTY;
				bl = true;
			} else if (canMergeItems(itemStack, stack)) {
				int i = stack.getMaxCount() - itemStack.getCount();
				int j = Math.min(stack.getCount(), i);
				stack.decrement(j);
				itemStack.increment(j);
				bl = j > 0;
			}

			if (bl) {
				to.markDirty();
			}
		}

		return stack;
	}

	private static boolean canMergeItems (ItemStack first, ItemStack second) {
		if (! first.isOf(second.getItem())) {
			return false;
		} else if (first.getDamage() != second.getDamage()) {
			return false;
		} else if (first.getCount() > first.getMaxCount()) {
			return false;
		} else {
			return ItemStack.areNbtEqual(first, second);
		}
	}

	private static IntStream getAvailableSlots (Inventory inventory) {
		return IntStream.range(0, inventory.size());
	}

	private static boolean isInventoryFull (Inventory inventory) {
		return getAvailableSlots(inventory).allMatch((slot) -> {
			ItemStack itemStack = inventory.getStack(slot);
			return itemStack.getCount() >= itemStack.getMaxCount();
		});
	}

	public static void addChestBlock (Block block) {
		chestBlocks.add(Registry.BLOCK.getId(block));
	}

	@Nullable
	private static Inventory getInventoryAt (World world, double x, double y, double z) {
		Inventory inventory = null;
		BlockPos blockPos = new BlockPos(x, y, z);
		BlockState blockState = world.getBlockState(blockPos);
		Block block = blockState.getBlock();
		if (block instanceof InventoryProvider) {
			inventory = ((InventoryProvider) block).getInventory(blockState, world, blockPos);
		} else if (blockState.hasBlockEntity()) {
			BlockEntity blockEntity = world.getBlockEntity(blockPos);
			if (blockEntity instanceof Inventory) {
				inventory = (Inventory) blockEntity;
			}
		}
		if (inventory == null) {
			List<Entity> list = world.getOtherEntities((Entity) null, new Box(x - 0.5, y - 0.5, z - 0.5, x + 0.5, y + 0.5, z + 0.5), EntityPredicates.VALID_INVENTORIES);
			if (! list.isEmpty()) {
				inventory = (Inventory) list.get(world.random.nextInt(list.size()));
			}
		}

		return (Inventory) inventory;
	}

	private static boolean canInsert (Inventory inventory, ItemStack stack, int slot) {
		return inventory.isValid(slot, stack);
	}
}
