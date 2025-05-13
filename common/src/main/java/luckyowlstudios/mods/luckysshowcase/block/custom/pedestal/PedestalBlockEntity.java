package luckyowlstudios.mods.luckysshowcase.block.custom.pedestal;

import luckyowlstudios.mods.luckysshowcase.block.custom.AbstractItemDisplayingBlockEntity;
import luckyowlstudios.mods.luckysshowcase.block.custom.ModBlockEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class PedestalBlockEntity extends AbstractItemDisplayingBlockEntity {

    public PedestalBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntityType.PEDESTAL.get(), pPos, pBlockState);
    }
}

