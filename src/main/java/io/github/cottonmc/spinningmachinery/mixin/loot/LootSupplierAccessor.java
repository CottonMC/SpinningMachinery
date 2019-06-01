package io.github.cottonmc.spinningmachinery.mixin.loot;

import net.minecraft.world.loot.LootPool;
import net.minecraft.world.loot.LootSupplier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LootSupplier.class)
public interface LootSupplierAccessor {
    @Accessor
    LootPool[] getPools();
}
