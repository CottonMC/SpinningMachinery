package io.github.cottonmc.spinningmachinery.gui.screen;

import io.github.cottonmc.spinningmachinery.gui.controller.GrinderController;
import net.minecraft.entity.player.PlayerEntity;

public final class GrinderScreen extends BaseMachineScreen<GrinderController> {
    public GrinderScreen(GrinderController menu, PlayerEntity player) {
        super(menu, player);
    }
}
