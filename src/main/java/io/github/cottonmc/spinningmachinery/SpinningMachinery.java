package io.github.cottonmc.spinningmachinery;

import io.github.cottonmc.spinningmachinery.block.SpinningBlocks;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public final class SpinningMachinery implements ModInitializer {
    @Override
    public void onInitialize() {
        SpinningBlocks.init();
    }

    public static Identifier id(String path) {
        return new Identifier("spinning-machinery", path);
    }
}
