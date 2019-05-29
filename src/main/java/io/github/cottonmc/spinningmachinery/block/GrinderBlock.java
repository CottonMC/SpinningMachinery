package io.github.cottonmc.spinningmachinery.block;

import io.github.cottonmc.spinningmachinery.util.FunctionUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.world.BlockView;

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
                state -> state.with(FACING, context.getPlayerFacing().getOpposite())
        );
    }

    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return SpinningBlocks.GRINDER_BLOCK_ENTITY.instantiate();
    }
}
