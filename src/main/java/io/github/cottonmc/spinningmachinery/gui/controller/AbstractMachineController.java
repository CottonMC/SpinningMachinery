package io.github.cottonmc.spinningmachinery.gui.controller;

import io.github.cottonmc.cotton.gui.CottonScreenController;
import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.widget.WPanel;
import io.github.cottonmc.spinningmachinery.config.MachineGuiConfig;
import net.minecraft.container.BlockContext;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.chat.Component;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Nameable;

import java.awt.Color;

public abstract class AbstractMachineController extends CottonScreenController implements Nameable {
    private final Component title;
    protected final MachineGuiConfig guiConfig;

    protected AbstractMachineController(RecipeType<?> recipeType, int syncId, PlayerInventory playerInventory,
                                        BlockContext context, Component title, MachineGuiConfig guiConfig) {
        super(recipeType, syncId, playerInventory, getBlockInventory(context), getBlockPropertyDelegate(context));
        this.title = title;
        this.guiConfig = guiConfig;
        if (isDarkColor(guiConfig.backgroundColor)) {
            titleColor = 0xF0F0D8;
        }
    }

    @Override
    public Component getName() {
        return title;
    }

    public PlayerInventory getPlayerInventory() {
        return playerInventory;
    }

    @Override
    public void addPainters() {
        rootPanel.setBackgroundPainter(BackgroundPainter.createColorful(0xFF000000 | guiConfig.backgroundColor));
    }

    private static boolean isDarkColor(int rgb) {
        return Color.RGBtoHSB((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF, null)[2] <= 0.6f;
    }
}
