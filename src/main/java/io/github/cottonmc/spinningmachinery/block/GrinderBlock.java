package io.github.cottonmc.spinningmachinery.block;

import io.github.cottonmc.spinningmachinery.block.entity.GrinderBlockEntity;
import io.github.cottonmc.spinningmachinery.gui.SpinningGuis;
import io.github.cottonmc.spinningmachinery.util.FunctionUtils;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.*;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public final class GrinderBlock extends BlockWithEntity {
    public static final DirectionProperty FACING = Properties.FACING_HORIZONTAL;
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

    public GrinderBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(ACTIVE, false));
    }

    @Override
    protected void appendProperties(StateFactory.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FACING, ACTIVE);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return FunctionUtils.runNullable(
                super.getPlacementState(context),
                state -> state.with(FACING, context.getPlayerHorizontalFacing().getOpposite())
        );
    }

    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return SpinningBlocks.GRINDER_BLOCK_ENTITY.instantiate();
    }

    @Override
    public boolean activate(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hitResult) {
        if (!world.isClient) {
            BlockEntity be = world.getBlockEntity(pos);

            if (be instanceof GrinderBlockEntity && ((GrinderBlockEntity) be).checkUnlocked(player)) {
                ContainerProviderRegistry.INSTANCE.openContainer(SpinningGuis.GRINDER, player, buf -> {
                    buf.writeBlockPos(pos);
                    buf.writeTextComponent(((GrinderBlockEntity) be).getDisplayName());
                });
            }
        }

        return true;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
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
