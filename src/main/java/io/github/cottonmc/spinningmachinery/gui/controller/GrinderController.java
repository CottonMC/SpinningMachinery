package io.github.cottonmc.spinningmachinery.gui.controller;

import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.widget.WBar;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.spinningmachinery.SpinningMachinery;
import io.github.cottonmc.spinningmachinery.config.MachineGuiConfig;
import io.github.cottonmc.spinningmachinery.gui.Textures;
import io.github.cottonmc.spinningmachinery.recipe.SpinningRecipes;
import net.minecraft.container.BlockContext;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.chat.Component;

public final class GrinderController extends AbstractMachineController {
    public GrinderController(int syncId, PlayerInventory playerInventory, BlockContext context, Component title) {
        super(SpinningRecipes.GRINDING, syncId, playerInventory, context, title);

        WPlainPanel panel = new WPlainPanel();

        // 18 is the width / height of one slot/WGridPanel unit
        panel.add(WItemSlot.of(blockInventory, 0), 18, 18);
        panel.add(new WItemSlot(blockInventory, 1, 1, 1, true, false), 5 * 18, 18);
        panel.add(WItemSlot.of(blockInventory, 2), 6 * 18 + 10, 18);
        panel.add(new WBar(Textures.PROGRESS_BAR_BG, Textures.PROGRESS_BAR, 0, 1, WBar.Direction.RIGHT), 2 * 18 + 5, 18, 3 * 18 - 15, 18);

        panel.add(createPlayerInventoryPanel(), 0, 3 * 18);

        panel.validate(this);
        setRootPanel(panel);

        MachineGuiConfig guiConfig = SpinningMachinery.CONFIG.get().machineScreens.grinder;
        if (guiConfig.enabled) {
            panel.setBackgroundPainter(BackgroundPainter.createColorful(0xFF000000 | guiConfig.backgroundColor));
        }
    }

    @Override
    public int getCraftingResultSlotIndex() {
        return 1;
    }
}
