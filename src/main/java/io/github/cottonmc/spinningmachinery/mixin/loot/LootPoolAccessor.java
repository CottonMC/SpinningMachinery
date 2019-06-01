package io.github.cottonmc.spinningmachinery.mixin.loot;

import net.minecraft.world.loot.LootPool;
import net.minecraft.world.loot.entry.LootEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LootPool.class)
public interface LootPoolAccessor {
    @Accessor
    LootEntry[] getEntries();
}
