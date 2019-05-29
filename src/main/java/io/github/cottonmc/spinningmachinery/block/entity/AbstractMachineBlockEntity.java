package io.github.cottonmc.spinningmachinery.block.entity;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.DefaultedList;

public abstract class AbstractMachineBlockEntity extends LockableContainerBlockEntity {
    protected final DefaultedList<ItemStack> items;
    
    protected AbstractMachineBlockEntity(BlockEntityType<?> type, DefaultedList<ItemStack> items) {
        super(type);
        this.items = items;
    }

    @Override
    public int getInvSize() {
        return items.size();
    }

    @Override
    public boolean isInvEmpty() {
        return items.stream().anyMatch(stack -> !stack.isEmpty());
    }

    @Override
    public ItemStack getInvStack(int i) {
        return items.get(i);
    }

    @Override
    public ItemStack takeInvStack(int i, int i1) {
        return Inventories.splitStack(items, i, i1);
    }

    @Override
    public ItemStack removeInvStack(int i) {
        return Inventories.removeStack(items, i);
    }

    @Override
    public void setInvStack(int i, ItemStack stack) {
        // TODO: Furnaces seem to do progress resetting here
        items.set(i, stack);
    }

    @Override
    public void clear() {
        items.clear();
    }

    @Override
    public boolean canPlayerUseInv(PlayerEntity player) {
        return true;
    }

    @Override
    public void fromTag(CompoundTag tag) {
        super.fromTag(tag);
        Inventories.fromTag(tag, items);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        Inventories.toTag(tag, items);
        return super.toTag(tag);
    }
}
