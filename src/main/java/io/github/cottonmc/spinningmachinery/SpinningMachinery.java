package io.github.cottonmc.spinningmachinery;

import io.github.cottonmc.spinningmachinery.block.SpinningBlocks;
import io.github.cottonmc.spinningmachinery.compat.refinedmachinery.SpinningRefinedMachineryPlugin;
import io.github.cottonmc.spinningmachinery.config.SpinningConfig;
import io.github.cottonmc.spinningmachinery.gui.SpinningGuis;
import io.github.cottonmc.spinningmachinery.item.SpinningItems;
import io.github.cottonmc.spinningmachinery.recipe.SpinningRecipes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import net.minecraft.util.Lazy;

import java.util.List;

public final class SpinningMachinery implements ModInitializer {
    public static final String NAMESPACE = "spinning-machinery";
    public static final Lazy<SpinningConfig> CONFIG = new Lazy<>(SpinningConfig::getInstance);

    @Override
    public void onInitialize() {
        SpinningBlocks.init();
        SpinningItems.init();
        SpinningRecipes.init();
        SpinningGuis.init();
        // Load config
        CONFIG.get();

        List<SpinningMachineryPlugin> plugins = FabricLoader.getInstance().getEntrypoints("spinning-machinery", SpinningMachineryPlugin.class);
        SpinningRecipes.initPlugins(plugins);
    }

    @Environment(EnvType.CLIENT)
    public static void initClient() {
        SpinningGuis.initClient();
    }

    public static void initCotton() {
        if (FabricLoader.getInstance().isModLoaded("refinedmachinery")) {
            SpinningRefinedMachineryPlugin.initCotton();
        }
    }

    public static Identifier id(String path) {
        return new Identifier(NAMESPACE, path);
    }
}
