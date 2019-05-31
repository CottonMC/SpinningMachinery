package io.github.cottonmc.spinningmachinery.gui.controller;

import io.github.cottonmc.cotton.gui.widget.WBar;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.spinningmachinery.SpinningMachinery;
import io.github.cottonmc.spinningmachinery.gui.Textures;
import io.github.cottonmc.spinningmachinery.recipe.SpinningRecipes;
import net.minecraft.container.BlockContext;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.chat.Component;

public final class PlatePressController extends AbstractMachineController {
    public PlatePressController(int syncId, PlayerInventory playerInventory, BlockContext context, Component title) {
        super(SpinningRecipes.PRESSING, syncId, playerInventory, context, title, SpinningMachinery.CONFIG.get().machineScreens.platePress);

        WPlainPanel panel = new WPlainPanel();

        // 18 is the width / height of one slot/WGridPanel unit
        panel.add(WItemSlot.of(blockInventory, 0), 2 * 18, 18);
        panel.add(new WItemSlot(blockInventory, 1, 1, 1, true, false), 6 * 18, 18);
        panel.add(new WBar(Textures.PROGRESS_BAR_BG, Textures.PROGRESS_BAR, 0, 1, WBar.Direction.RIGHT), 3 * 18 + 5, 18, 3 * 18 - 15, 18);

        panel.add(createPlayerInventoryPanel(), 0, 3 * 18);

        panel.validate(this);
        setRootPanel(panel);
    }

    @Override
    public int getCraftingResultSlotIndex() {
        return 1;
    }
}
