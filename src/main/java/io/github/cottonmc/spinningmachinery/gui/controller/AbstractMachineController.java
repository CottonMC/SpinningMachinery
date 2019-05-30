package io.github.cottonmc.spinningmachinery.gui.controller;

import io.github.cottonmc.cotton.gui.CottonScreenController;
import net.minecraft.container.BlockContext;
import net.minecraft.container.PropertyDelegate;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.network.chat.Component;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Nameable;

public abstract class AbstractMachineController extends CottonScreenController implements Nameable {
    private final Component title;

    public AbstractMachineController(RecipeType<?> recipeType, int syncId, PlayerInventory playerInventory,
                                     BlockContext context, Component title) {
        super(recipeType, syncId, playerInventory, getBlockInventory(context), getBlockPropertyDelegate(context));
        this.title = title;
    }

    @Override
    public Component getName() {
        return title;
    }

    public PlayerInventory getPlayerInventory() {
        return playerInventory;
    }
}
