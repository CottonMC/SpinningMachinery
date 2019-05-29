package io.github.cottonmc.spinningmachinery.gui.screen;

import io.github.cottonmc.cotton.gui.client.CottonScreen;
import io.github.cottonmc.spinningmachinery.gui.controller.GrinderController;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.chat.Component;

public final class GrinderScreen extends CottonScreen<GrinderController> {
    public GrinderScreen(GrinderController menu, PlayerEntity player) {
        super(menu, player);
    }

    @Override
    public Component getTitle() {
        return ((GrinderController) container).getTitle();
    }
}
