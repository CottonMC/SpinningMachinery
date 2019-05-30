package io.github.cottonmc.spinningmachinery.recipe;

import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.command.arguments.BlockStateArgument;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

public final class HammeringInventory implements Inventory {
    private final CachedBlockPosition cachedPos;

    public HammeringInventory(CachedBlockPosition cachedPos) {
        this.cachedPos = cachedPos;
    }

    boolean matches(BlockStateArgument state) {
        return state.test(cachedPos);
    }

    @Override
    public int getInvSize() {
        return 0;
    }

    @Override
    public boolean isInvEmpty() {
        return true;
    }

    @Override
    public ItemStack getInvStack(int i) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack takeInvStack(int i, int i1) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeInvStack(int i) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setInvStack(int i, ItemStack itemStack) {}

    @Override
    public void markDirty() {}

    @Override
    public boolean canPlayerUseInv(PlayerEntity playerEntity) {
        return true;
    }

    @Override
    public void clear() {}
}
