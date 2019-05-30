package io.github.cottonmc.spinningmachinery.gui.screen;

import io.github.cottonmc.cotton.gui.CottonScreenController;
import io.github.cottonmc.cotton.gui.client.CottonScreen;
import io.github.cottonmc.spinningmachinery.gui.controller.GrinderController;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Nameable;

public abstract class BaseMachineScreen<C extends CottonScreenController & Nameable> extends CottonScreen<C> {
    public BaseMachineScreen(C menu, PlayerEntity player) {
        super(menu, player);
    }

    @Override
    public Component getTitle() {
        return ((Nameable) container).getName();
    }
}
