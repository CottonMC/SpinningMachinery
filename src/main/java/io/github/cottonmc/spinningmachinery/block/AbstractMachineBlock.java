package io.github.cottonmc.spinningmachinery.block;

import io.github.cottonmc.spinningmachinery.block.entity.AbstractMachineBlockEntity;
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
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class AbstractMachineBlock extends BlockWithEntity {
    public static final DirectionProperty FACING = Properties.FACING_HORIZONTAL;
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
    private final Identifier menuId;

    public AbstractMachineBlock(Settings settings, Identifier menuId) {
        super(settings);
        this.menuId = menuId;
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
    public boolean activate(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hitResult) {
        if (!world.isClient) {
            BlockEntity be = world.getBlockEntity(pos);

            if (be instanceof AbstractMachineBlockEntity && ((AbstractMachineBlockEntity) be).checkUnlocked(player)) {
                ContainerProviderRegistry.INSTANCE.openContainer(menuId, player, buf -> {
                    buf.writeBlockPos(pos);
                    buf.writeTextComponent(((AbstractMachineBlockEntity) be).getDisplayName());
                });
            }
        }

        return true;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
