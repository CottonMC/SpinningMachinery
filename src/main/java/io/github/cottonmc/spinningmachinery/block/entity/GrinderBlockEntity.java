package io.github.cottonmc.spinningmachinery.block.entity;

import io.github.cottonmc.cotton.gui.PropertyDelegateHolder;
import io.github.cottonmc.spinningmachinery.block.AbstractMachineBlock;
import io.github.cottonmc.spinningmachinery.block.SpinningBlocks;
import io.github.cottonmc.spinningmachinery.gui.controller.GrinderController;
import io.github.cottonmc.spinningmachinery.recipe.GrindingInventory;
import io.github.cottonmc.spinningmachinery.recipe.SpinningRecipes;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.container.BlockContext;
import net.minecraft.container.Container;
import net.minecraft.container.PropertyDelegate;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.Direction;

public final class GrinderBlockEntity extends AbstractProcessorBlockEntity<GrindingInventory>
        implements BlockEntityClientSerializable, SidedInventory, PropertyDelegateHolder, GrindingInventory {
    public static final int MAX_PROGRESS = 1000;
    private static final int[] DEFAULT_SLOTS = { 0 };
    private static final int[] DOWN_SLOTS = { 1, 2 };
    private final PropertyDelegate propertyDelegate = new PropertyDelegate() {
        @Override
        public int get(int i) {
            switch (i) {
                case 0:
                    return progress;
                case 1:
                    return MAX_PROGRESS;
                default:
                    return 0;
            }
        }

        @Override
        public void set(int i, int value) {
            switch (i) {
                case 0:
                    progress = value;
                    break;
                case 1:
                    // Ignore the max progress property
                    break;
            }
        }

        @Override
        public int size() {
            return 2;
        }
    };

    public GrinderBlockEntity() {
        super(SpinningBlocks.GRINDER_BLOCK_ENTITY, DefaultedList.create(3, ItemStack.EMPTY), SpinningRecipes.getAllGrindingRecipeTypes());
    }

    @Override
    protected Component getContainerName() {
        return new TranslatableComponent(SpinningBlocks.GRINDER.getTranslationKey());
    }

    @Override
    protected Container createContainer(int syncId, PlayerInventory playerInventory) {
        return new GrinderController(syncId, playerInventory, BlockContext.create(world, pos), getDisplayName());
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

    @Override
    public void fromClientTag(CompoundTag tag) {
        progress = tag.getInt(NBT_PROGRESS);
        Inventories.fromTag(tag, items);
    }

    @Override
    public CompoundTag toClientTag(CompoundTag tag) {
        tag.putInt(NBT_PROGRESS, progress);
        Inventories.toTag(tag, items);
        return tag;
    }

    @Override
    protected int getMaxProgress() {
        return MAX_PROGRESS;
    }

    @Override
    protected void updateActive(boolean newActive) {
        world.setBlockState(pos, world.getBlockState(pos).with(AbstractMachineBlock.ACTIVE, newActive));
    }

    @Override
    public int[] getInvAvailableSlots(Direction direction) {
        if (direction == Direction.DOWN)
            return DOWN_SLOTS;
        else
            return DEFAULT_SLOTS;
    }

    @Override
    public boolean canInsertInvStack(int i, ItemStack stack, Direction direction) {
        return direction != Direction.DOWN && i == 0;
    }

    @Override
    public boolean canExtractInvStack(int i, ItemStack stack, Direction direction) {
        return i != 0;
    }

    @Override
    public PropertyDelegate getPropertyDelegate() {
        return propertyDelegate;
    }

    @Override
    public void insertProcessingBonus(ItemStack stack) {
        if (canInsertIntoSlot(2, stack)) {
            insertIntoSlot(2, stack);
        } else {
            ItemScatterer.spawn(world, pos.up(), DefaultedList.create(ItemStack.EMPTY, stack));
        }
    }

    @Override
    public boolean isValidInvStack(int slot, ItemStack stack) {
        return slot == 0;
    }
}
