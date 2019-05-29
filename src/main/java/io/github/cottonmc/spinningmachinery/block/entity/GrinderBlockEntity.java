package io.github.cottonmc.spinningmachinery.block.entity;

import com.jamieswhiteshirt.clotheslinefabric.api.NetworkManagerProvider;
import com.jamieswhiteshirt.clotheslinefabric.api.NetworkNode;
import io.github.cottonmc.cotton.gui.PropertyDelegateHolder;
import io.github.cottonmc.spinningmachinery.block.SpinningBlocks;
import io.github.cottonmc.spinningmachinery.gui.controller.GrinderController;
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
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public final class GrinderBlockEntity extends AbstractMachineBlockEntity implements Tickable, BlockEntityClientSerializable, SidedInventory, PropertyDelegateHolder {
    private static final String NBT_PROGRESS = "Progress";
    public static final int MAX_PROGRESS = 400;
    private static final int[] DEFAULT_SLOTS = { 0 };
    private static final int[] DOWN_SLOTS = { 1, 2 };
    private int progress = 0;
    private final PropertyDelegate propertyDelegate = new PropertyDelegate() {
        @Override
        public int get(int i) {
            switch (i) {
                case 0:
                    return progress;
                default:
                    return 0;
            }
        }

        @Override
        public void set(int i, int value) {
            switch (i) {
                case 0:
                    progress = value;
            }
        }

        @Override
        public int size() {
            return 1;
        }
    };

    public GrinderBlockEntity() {
        super(SpinningBlocks.GRINDER_BLOCK_ENTITY, DefaultedList.create(3, ItemStack.EMPTY));
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
        progress = tag.getInt(NBT_PROGRESS);
        Inventories.fromTag(tag, items);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        tag.putInt(NBT_PROGRESS, progress);
        Inventories.toTag(tag, items);
        return super.toTag(tag);
    }

    @Override
    public void fromClientTag(CompoundTag tag) {
        // TODO
    }

    @Override
    public CompoundTag toClientTag(CompoundTag tag) {
        // TODO
        return tag;
    }

    @Override
    public void tick() {
        // TODO
    }

    private static boolean isSpinning(World world, BlockPos pos) {
        NetworkNode node = ((NetworkManagerProvider) world).getNetworkManager().getNetworks().getNodes().get(pos);
        return node != null && node.getNetwork().getState().getMomentum() > 0;
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
}
