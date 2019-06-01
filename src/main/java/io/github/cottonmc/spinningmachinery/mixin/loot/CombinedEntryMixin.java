package io.github.cottonmc.spinningmachinery.mixin.loot;

import io.github.cottonmc.spinningmachinery.api.AllItemsLootEntry;
import net.minecraft.item.ItemStack;
import net.minecraft.world.loot.entry.CombinedEntry;
import net.minecraft.world.loot.entry.LootEntry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mixin(CombinedEntry.class)
public class CombinedEntryMixin implements AllItemsLootEntry {
    @Shadow @Final protected LootEntry[] children;

    @Override
    public List<ItemStack> spinning_getAllPossibleStacks() {
        return Stream.of(children)
                .filter(child -> child instanceof AllItemsLootEntry)
                .flatMap(child -> ((AllItemsLootEntry) child).spinning_getAllPossibleStacks().stream())
                .collect(Collectors.toList());
    }
}
