package io.github.cottonmc.spinningmachinery.block;

import io.github.cottonmc.spinningmachinery.SpinningMachinery;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public final class SpinningBlocks {
    public static final Block GRINDER = register(
            "grinder",
            new GrinderBlock(Block.Settings.copy(Blocks.FURNACE)),
            new Item.Settings()
    );

    public static void init() {}

    private static <T extends Block> T register(String id, T block, Item.Settings itemSettings) {
        Registry.register(Registry.BLOCK, SpinningMachinery.id(id), block);
        Registry.register(Registry.ITEM, SpinningMachinery.id(id), new BlockItem(block, itemSettings));

        return block;
    }
}
