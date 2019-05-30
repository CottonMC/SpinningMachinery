package io.github.cottonmc.spinningmachinery;

import io.github.cottonmc.spinningmachinery.block.SpinningBlocks;
import io.github.cottonmc.spinningmachinery.gui.SpinningGuis;
import io.github.cottonmc.spinningmachinery.item.SpinningItems;
import io.github.cottonmc.spinningmachinery.recipe.SpinningRecipes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public final class SpinningMachinery implements ModInitializer {
    public static final String NAMESPACE = "spinning-machinery";

    @Override
    public void onInitialize() {
        SpinningBlocks.init();
        SpinningItems.init();
        SpinningRecipes.init();
        SpinningGuis.init();
    }

    @Environment(EnvType.CLIENT)
    public static void initClient() {
        SpinningGuis.initClient();
    }

    public static Identifier id(String path) {
        return new Identifier(NAMESPACE, path);
    }
}
