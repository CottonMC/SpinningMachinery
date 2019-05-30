package io.github.cottonmc.spinningmachinery.gui;

import io.github.cottonmc.cotton.gui.CottonScreenController;
import io.github.cottonmc.spinningmachinery.SpinningMachinery;
import io.github.cottonmc.spinningmachinery.gui.controller.AbstractMachineController;
import io.github.cottonmc.spinningmachinery.gui.controller.GrinderController;
import io.github.cottonmc.spinningmachinery.gui.controller.PlatePressController;
import io.github.cottonmc.spinningmachinery.gui.screen.MachineScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.container.BlockContext;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Identifier;

public final class SpinningGuis {
    public static final Identifier GRINDER = SpinningMachinery.id("grinder");
    public static final Identifier PLATE_PRESS = SpinningMachinery.id("plate_press");

    public static void init() {
        registerMenu(GRINDER, GrinderController::new);
        registerMenu(PLATE_PRESS, PlatePressController::new);
    }

    @Environment(EnvType.CLIENT)
    public static void initClient() {
        ScreenProviderRegistry.INSTANCE.<GrinderController>registerFactory(
                GRINDER,
                menu -> new MachineScreen<>(menu, menu.getPlayerInventory().player)
        );

        ScreenProviderRegistry.INSTANCE.<AbstractMachineController>registerFactory(
                PLATE_PRESS,
                menu -> new MachineScreen<>(menu, menu.getPlayerInventory().player)
        );
    }

    private static <M extends CottonScreenController> void registerMenu(Identifier id, MenuFactory<M> factory) {
        ContainerProviderRegistry.INSTANCE.registerFactory(
                id,
                (syncId, identifier, player, buf) -> factory.createMenu(
                        syncId,
                        player.inventory,
                        BlockContext.create(player.world, buf.readBlockPos()),
                        buf.readTextComponent()
                )
        );
    }

    @FunctionalInterface
    private interface MenuFactory<M extends CottonScreenController> {
        M createMenu(int syncId, PlayerInventory playerInv, BlockContext context, Component title);
    }
}
