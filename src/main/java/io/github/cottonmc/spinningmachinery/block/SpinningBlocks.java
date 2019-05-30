package io.github.cottonmc.spinningmachinery.block;

import com.google.common.collect.ImmutableSet;
import io.github.cottonmc.spinningmachinery.SpinningMachinery;
import io.github.cottonmc.spinningmachinery.block.entity.GrinderBlockEntity;
import io.github.cottonmc.spinningmachinery.block.entity.PlatePressBlockEntity;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tools.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

public final class SpinningBlocks {
    public static final Block GRINDER = register(
            "grinder",
            new GrinderBlock(
                    FabricBlockSettings.copy(Blocks.IRON_BLOCK)
                            .breakByTool(FabricToolTags.PICKAXES)
                            .ticksRandomly()
                            .build()
            ),
            new Item.Settings().itemGroup(ItemGroup.DECORATIONS)
    );

    public static final Block PLATE_PRESS = register(
            "plate_press",
            new PlatePressBlock(
                    FabricBlockSettings.copy(Blocks.IRON_BLOCK)
                            .breakByTool(FabricToolTags.PICKAXES)
                            .ticksRandomly()
                            .build()
            ),
            new Item.Settings().itemGroup(ItemGroup.DECORATIONS)
    );

    public static final BlockEntityType<GrinderBlockEntity> GRINDER_BLOCK_ENTITY = register(
            Registry.BLOCK_ENTITY,
            "grinder",
            new BlockEntityType<>(GrinderBlockEntity::new, ImmutableSet.of(GRINDER), null)
    );

    public static final BlockEntityType<PlatePressBlockEntity> PLATE_PRESS_BLOCK_ENTITY = register(
            Registry.BLOCK_ENTITY,
            "plate_press",
            new BlockEntityType<>(PlatePressBlockEntity::new, ImmutableSet.of(PLATE_PRESS), null)
    );

    public static void init() {}

    private static <T extends Block> T register(String id, T block, Item.Settings itemSettings) {
        Registry.register(Registry.BLOCK, SpinningMachinery.id(id), block);
        Registry.register(Registry.ITEM, SpinningMachinery.id(id), new BlockItem(block, itemSettings));

        return block;
    }

    private static <T> T register(Registry<? super T> registry, String id, T value) {
        Registry.register(registry, SpinningMachinery.id(id), value);
        return value;
    }
}
