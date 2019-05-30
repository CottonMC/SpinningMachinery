package io.github.cottonmc.spinningmachinery.recipe;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

public interface ProcessingInventory extends Inventory {
    void insertProcessingBonus(ItemStack stack);
}
