package io.github.cottonmc.spinningmachinery.block;

import io.github.cottonmc.spinningmachinery.block.entity.GrinderBlockEntity;
import io.github.cottonmc.spinningmachinery.gui.SpinningGuis;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public final class GrinderBlock extends AbstractMachineBlock {
    public GrinderBlock(Settings settings) {
        super(settings, SpinningGuis.GRINDER);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return SpinningBlocks.GRINDER_BLOCK_ENTITY.instantiate();
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(ACTIVE)) {
            BlockEntity be = world.getBlockEntity(pos);
            if (!(be instanceof GrinderBlockEntity)) return;
            GrinderBlockEntity grinder = (GrinderBlockEntity) be;
            Direction facing = state.get(FACING);
            double xOffset = 0.0;
            double zOffset = 0.0;

            switch (facing.getAxis()) {
                case X:
                    zOffset = 0.5;
                    if (facing == Direction.EAST)
                        xOffset = 1.1;
                    else
                        xOffset = -0.1;
                    break;

                case Z:
                    xOffset = 0.5;
                    if (facing == Direction.SOUTH)
                        zOffset = 1.1;
                    else
                        zOffset = -0.1;
                    break;
            }

            ItemStack stack = grinder.getInvStack(0);
            if (stack.isEmpty()) return;
            ParticleEffect effect;

            if (stack.getItem() instanceof BlockItem)
                effect = new BlockStateParticleEffect(ParticleTypes.BLOCK, ((BlockItem) stack.getItem()).getBlock().getDefaultState());
            else
                effect = new ItemStackParticleEffect(ParticleTypes.ITEM, stack);

            world.addParticle(effect, pos.getX() + xOffset, pos.getY() + 0.5, pos.getZ() + zOffset, 0, 0, 0);
        }
    }
}
