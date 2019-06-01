package io.github.cottonmc.spinningmachinery.compat.rei.loot;

import io.github.cottonmc.spinningmachinery.api.AllItemsLootEntry;
import io.github.cottonmc.spinningmachinery.mixin.loot.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.loot.LootSupplier;
import net.minecraft.world.loot.entry.LootEntry;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Environment(EnvType.CLIENT)
public final class LootUtils {
    public static List<ItemStack> getAllStacks(Identifier lootTable) {
        LootSupplier supplier = MinecraftClient.getInstance().getServer().getLootManager().getSupplier(lootTable);
        return Stream.of(((LootSupplierAccessor) supplier).getPools())
                .flatMap(pool -> Stream.of(((LootPoolAccessor) pool).getEntries()))
                .flatMap(entry -> getAllStacks(entry).stream())
                .collect(Collectors.toList());
    }

    private static List<ItemStack> getAllStacks(LootEntry entry) {
        if (entry instanceof AllItemsLootEntry) {
            return ((AllItemsLootEntry) entry).spinning_getAllPossibleStacks();
        } else {
            throw new IllegalArgumentException("Unsupported loot entry type: " + entry.getClass().getName());
        }
    }
}
