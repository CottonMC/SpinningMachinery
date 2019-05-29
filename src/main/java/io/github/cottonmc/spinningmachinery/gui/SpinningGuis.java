package io.github.cottonmc.spinningmachinery.gui;

import io.github.cottonmc.spinningmachinery.SpinningMachinery;
import io.github.cottonmc.spinningmachinery.gui.controller.GrinderController;
import io.github.cottonmc.spinningmachinery.gui.screen.GrinderScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.container.BlockContext;
import net.minecraft.util.Identifier;

public final class SpinningGuis {
    public static final Identifier GRINDER = SpinningMachinery.id("grinder");

    public static void init() {
        ContainerProviderRegistry.INSTANCE.registerFactory(
                GRINDER,
                (syncId, id, player, buf) -> new GrinderController(
                        syncId,
                        player.inventory,
                        BlockContext.create(player.world, buf.readBlockPos()),
                        buf.readTextComponent()
                )
        );
    }

    @Environment(EnvType.CLIENT)
    public static void initClient() {
        ScreenProviderRegistry.INSTANCE.<GrinderController>registerFactory(
                GRINDER,
                menu -> new GrinderScreen(menu, menu.getPlayerInventory().player)
        );
    }
}
