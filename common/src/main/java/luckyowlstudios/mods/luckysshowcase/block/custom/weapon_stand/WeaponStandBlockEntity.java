package luckyowlstudios.mods.luckysshowcase.block.custom.weapon_stand;

import luckyowlstudios.mods.luckysshowcase.block.custom.AbstractItemDisplayingBlockEntity;
import luckyowlstudios.mods.luckysshowcase.block.custom.ModBlockEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class WeaponStandBlockEntity extends AbstractItemDisplayingBlockEntity {

    public WeaponStandBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntityType.WEAPON_STAND.get(), pPos, pBlockState);
    }
}
