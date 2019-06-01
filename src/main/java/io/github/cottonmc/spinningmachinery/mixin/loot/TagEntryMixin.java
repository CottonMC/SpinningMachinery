package io.github.cottonmc.spinningmachinery.mixin.loot;

import io.github.cottonmc.spinningmachinery.api.AllItemsLootEntry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.Tag;
import net.minecraft.world.loot.entry.TagEntry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import java.util.stream.Collectors;

@Mixin(TagEntry.class)
public class TagEntryMixin implements AllItemsLootEntry {
    @Shadow @Final private Tag<Item> name;

    @Override
    public List<ItemStack> spinning_getAllPossibleStacks() {
        return name.values().stream().map(ItemStack::new).collect(Collectors.toList());
    }
}
