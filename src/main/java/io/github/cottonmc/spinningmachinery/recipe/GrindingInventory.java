package io.github.cottonmc.spinningmachinery.recipe;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

public interface GrindingInventory extends Inventory {
    void insertProcessingBonus(ItemStack stack);
}
