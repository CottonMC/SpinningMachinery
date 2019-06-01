package io.github.cottonmc.spinningmachinery.mixin.loot;

import io.github.cottonmc.spinningmachinery.api.AllItemsLootEntry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.loot.entry.ItemEntry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Collections;
import java.util.List;

@Mixin(ItemEntry.class)
public class ItemEntryMixin implements AllItemsLootEntry {
    @Shadow @Final private Item item;

    @Override
    public List<ItemStack> spinning_getAllPossibleStacks() {
        return Collections.singletonList(new ItemStack(item));
    }
}
