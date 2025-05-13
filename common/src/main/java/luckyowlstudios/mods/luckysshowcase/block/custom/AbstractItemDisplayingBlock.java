package luckyowlstudios.mods.luckysshowcase.block.custom;

import com.mojang.serialization.MapCodec;
import luckyowlstudios.mods.luckysshowcase.block.custom.item_rack.ToolRackBlock;
import luckyowlstudios.mods.luckysshowcase.block.custom.pedestal.PedestalBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class AbstractItemDisplayingBlock extends BaseEntityBlock implements DisplayBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    protected static final VoxelShape SHAPE = Shapes.or(
            // Top
            Block.box(0.0, 10.0, 0.0, 16.0, 16.0, 16.0),
            // Middle
            Block.box(4.0, 4.0, 4.0, 12.0, 10, 12.0),
            // Bottom
            Block.box(2.0, 0.0, 2.0, 14.0, 4, 14.0));

    public AbstractItemDisplayingBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, Boolean.valueOf(false)).setValue(FACING, Direction.NORTH));
    }

    protected abstract MapCodec<? extends AbstractItemDisplayingBlock> codec();

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, WATERLOGGED);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
        final var blockEntity = pLevel.getBlockEntity(pPos);
        if (!(blockEntity instanceof AbstractItemDisplayingBlockEntity abstractItemDisplayingBlockEntity)) {
            return InteractionResult.FAIL;
        }

        if (!pPlayer.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
            return InteractionResult.PASS;
        }

        if (!abstractItemDisplayingBlockEntity.getTheItem().isEmpty()) {
            pPlayer.setItemInHand(InteractionHand.MAIN_HAND, abstractItemDisplayingBlockEntity.getTheItem());
            abstractItemDisplayingBlockEntity.setTheItem(ItemStack.EMPTY);
            pLevel.playSound((Player) null, pPos, placeItemSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        if (pLevel.getBlockEntity(pPos) instanceof AbstractItemDisplayingBlockEntity abstractItemDisplayingBlockEntity) {
            if (pLevel.isClientSide) {
                return ItemInteractionResult.CONSUME;
            } else if (canHoldItem(pStack.getItem())) {
                if (!pStack.isEmpty() && abstractItemDisplayingBlockEntity.isEmpty()) {
                    pPlayer.awardStat(Stats.ITEM_USED.get(pStack.getItem()));

                    ItemStack itemToPlace = pStack.copyWithCount(1);
                    pStack.shrink(1); // Decrease player's item stack
                    abstractItemDisplayingBlockEntity.setTheItem(itemToPlace);

                    abstractItemDisplayingBlockEntity.setChanged();
                    pLevel.gameEvent(pPlayer, GameEvent.BLOCK_CHANGE, pPos);
                    pLevel.playSound((Player) null, pPos, placeItemSound(), SoundSource.BLOCKS, 1.0F, 1.0F);

                    return ItemInteractionResult.SUCCESS;
                } else {
                    return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
                }
            }
        } else {
            return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    public abstract boolean canHoldItem(Item item);

    @Override
    protected boolean isPathfindable(BlockState pState, PathComputationType pPathComputationType) {
        return false;
    }

    @Override
    protected void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        Containers.dropContentsOnDestroy(pState, pNewState, pLevel, pPos);
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    @Override
    protected FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    @Override
    protected boolean hasAnalogOutputSignal(BlockState pState) {
        return true;
    }

    @Override
    protected RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    protected BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    /**
     * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed blockstate.
     */
    @Override
    protected BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }
}
