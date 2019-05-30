package io.github.cottonmc.spinningmachinery.block;

import io.github.cottonmc.spinningmachinery.gui.SpinningGuis;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

public final class PlatePressBlock extends AbstractMachineBlock {
    public PlatePressBlock(Settings settings) {
        super(settings, SpinningGuis.PLATE_PRESS);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return SpinningBlocks.PLATE_PRESS_BLOCK_ENTITY.instantiate();
    }
}
