package io.github.cottonmc.spinningmachinery.item;

import io.github.cottonmc.spinningmachinery.SpinningMachinery;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

public final class SpinningItems {
    public static final Item MACHINE_CASING = register("machine_casing", new Item(new Item.Settings().itemGroup(ItemGroup.MISC)));

    public static void init() {}

    private static <I extends Item> I register(String id, I item) {
        return Registry.register(Registry.ITEM, SpinningMachinery.id(id), item);
    }
}
