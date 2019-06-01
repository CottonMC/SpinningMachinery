package io.github.cottonmc.spinningmachinery.api;

import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Can be implemented on a {@code LootEntry} to provide all possible item stacks that the entry can provide.
 *
 * Implemented by default on {@code ItemEntry}, {@code TagEntry} and {@code CombinedEntry}.
 */
public interface AllItemsLootEntry {
    List<ItemStack> spinning_getAllPossibleStacks();
}
