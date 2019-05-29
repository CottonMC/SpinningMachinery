package io.github.cottonmc.spinningmachinery;

import io.github.cottonmc.spinningmachinery.block.SpinningBlocks;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public final class SpinningMachinery implements ModInitializer {
    public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(
            id("items"),
            () -> new ItemStack(SpinningBlocks.GRINDER)
    );

    @Override
    public void onInitialize() {
        SpinningBlocks.init();
    }

    public static Identifier id(String path) {
        return new Identifier("spinning-machinery", path);
    }
}
