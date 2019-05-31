package io.github.cottonmc.spinningmachinery;

import io.github.cottonmc.spinningmachinery.block.SpinningBlocks;
import io.github.cottonmc.spinningmachinery.compat.autoconfig.SpinningAutoConfig;
import io.github.cottonmc.spinningmachinery.config.SpinningConfig;
import io.github.cottonmc.spinningmachinery.gui.SpinningGuis;
import io.github.cottonmc.spinningmachinery.item.SpinningItems;
import io.github.cottonmc.spinningmachinery.recipe.SpinningRecipes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;

public final class SpinningMachinery implements ModInitializer {
    public static final String NAMESPACE = "spinning-machinery";
    public static SpinningConfig config;

    @Override
    public void onInitialize() {
        SpinningBlocks.init();
        SpinningItems.init();
        SpinningRecipes.init();
        SpinningGuis.init();

        if (FabricLoader.getInstance().isModLoaded("autoconfig1")) {
            SpinningAutoConfig.init();
        }

        config = SpinningConfig.getInstance();
    }

    @Environment(EnvType.CLIENT)
    public static void initClient() {
        SpinningGuis.initClient();
    }

    public static Identifier id(String path) {
        return new Identifier(NAMESPACE, path);
    }
}
