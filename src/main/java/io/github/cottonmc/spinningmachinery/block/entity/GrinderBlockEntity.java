package io.github.cottonmc.spinningmachinery.block.entity;

import com.jamieswhiteshirt.clotheslinefabric.api.NetworkManagerProvider;
import com.jamieswhiteshirt.clotheslinefabric.api.NetworkNode;
import io.github.cottonmc.spinningmachinery.block.SpinningBlocks;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.container.Container;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class GrinderBlockEntity extends AbstractMachineBlockEntity implements Tickable, BlockEntityClientSerializable {
    private static final String NBT_PROGRESS = "Progress";
    private static final int MAX_PROGRESS = 400;
    private int progress = 0;

    public GrinderBlockEntity() {
        super(SpinningBlocks.GRINDER_BLOCK_ENTITY, DefaultedList.create(3, ItemStack.EMPTY));
    }

    @Override
    protected Component getContainerName() {
        return new TranslatableComponent(SpinningBlocks.GRINDER.getTranslationKey());
    }

    @Override
    protected Container createContainer(int i, PlayerInventory playerInventory) {
        return null;
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
}
