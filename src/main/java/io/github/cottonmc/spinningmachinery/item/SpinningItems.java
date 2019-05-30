package io.github.cottonmc.spinningmachinery.item;

import io.github.cottonmc.spinningmachinery.SpinningMachinery;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

public final class SpinningItems {
    public static final Item HAMMER = register("hammer", new HammerItem(new Item.Settings().itemGroup(ItemGroup.TOOLS).durability(80)));

    public static void init() {}

    private static <I extends Item> I register(String id, I item) {
        return Registry.register(Registry.ITEM, SpinningMachinery.id(id), item);
    }
}
