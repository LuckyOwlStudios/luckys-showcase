package luckyowlstudios.mods.luckysshowcase.block.custom.weapon_stand;

import com.mojang.serialization.MapCodec;
import luckyowlstudios.mods.luckysshowcase.block.custom.AbstractItemDisplayingBlock;
import luckyowlstudios.mods.luckysshowcase.block.custom.AbstractItemDisplayingBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WeaponStandBlock extends AbstractItemDisplayingBlock {
    public static final MapCodec<luckyowlstudios.mods.luckysshowcase.block.custom.weapon_stand.WeaponStandBlock> CODEC = simpleCodec(luckyowlstudios.mods.luckysshowcase.block.custom.weapon_stand.WeaponStandBlock::new);
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    protected static final VoxelShape NORTH_SOUTH_AABB = Shapes.or(Block.box(1.0, 0.0, 4.0, 15.0, 2, 12.0), Block.box(2.0, 2.0, 5.0, 14.0, 5.0, 11.0));
    protected static final VoxelShape EAST_WEST_AABB = Shapes.or(Block.box(4.0, 0.0, 1.0, 12.0, 2, 15.0), Block.box(5.0, 2.0, 2.0, 11.0, 5.0, 14.0));

    protected static final VoxelShape NORTH_SOUTH_ITEM_AABB = Shapes.or(NORTH_SOUTH_AABB, Block.box(2.0, 5.0, 7.0, 14.0, 20.0, 9));
    protected static final VoxelShape EAST_WEST_ITEM_AABB = Shapes.or(EAST_WEST_AABB, Block.box(7, 5.0, 2.0, 9, 20.0, 14.0));

    public WeaponStandBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, Boolean.valueOf(false)).setValue(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends AbstractItemDisplayingBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new WeaponStandBlockEntity(blockPos, blockState);
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        Direction facing = pState.getValue(FACING);
        if (pLevel.getBlockEntity(pPos) instanceof WeaponStandBlockEntity weaponStandBlockEntity) {
            ItemStack itemStack = weaponStandBlockEntity.getTheItem();
            if (!itemStack.isEmpty()) {
                return facing == Direction.NORTH || facing == Direction.SOUTH ? NORTH_SOUTH_ITEM_AABB : EAST_WEST_ITEM_AABB;
            }
        }
        return facing == Direction.NORTH || facing == Direction.SOUTH ? NORTH_SOUTH_AABB : EAST_WEST_AABB;
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        Direction facing = pState.getValue(FACING);
        return facing == Direction.NORTH || facing == Direction.SOUTH ? NORTH_SOUTH_AABB : EAST_WEST_AABB;
    }

    // Copied from LadderBlock
    /**
     * Update the provided state given the provided neighbor direction and neighbor state, returning a new state.
     * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately returns its solidified counterpart.
     * Note that this method should ideally consider only the specific direction passed in.
     */
    @Override
    protected BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (!pState.canSurvive(pLevel, pCurrentPos)) {
            return Blocks.AIR.defaultBlockState();
        } else {
            if (pState.getValue(WATERLOGGED)) {
                pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
            }
            return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
        }
    }

    // Copied from LadderBlock
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        if (!pContext.replacingClickedOnBlock()) {
            BlockState blockstate = pContext.getLevel().getBlockState(pContext.getClickedPos().relative(pContext.getClickedFace().getOpposite()));
            if (blockstate.is(this) && blockstate.getValue(FACING) == pContext.getClickedFace()) {
                return null;
            }
        }

        BlockState blockstate1 = this.defaultBlockState();
        LevelReader levelreader = pContext.getLevel();
        BlockPos blockpos = pContext.getClickedPos();
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());

        for (Direction direction : pContext.getNearestLookingDirections()) {
            if (direction.getAxis().isHorizontal()) {
                blockstate1 = blockstate1.setValue(FACING, direction.getOpposite());
                if (blockstate1.canSurvive(levelreader, blockpos)) {
                    return blockstate1.setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));
                }
            }
        }

        return null;
    }

    // Copied from LadderBlock
    private boolean canAttachTo(BlockGetter pBlockReader, BlockPos pPos, Direction pDirection) {
        BlockState blockstate = pBlockReader.getBlockState(pPos);
        return blockstate.isFaceSturdy(pBlockReader, pPos, pDirection);
    }

    // Copied from LadderBlock
    @Override
    protected boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return this.canAttachTo(pLevel, pPos.relative(Direction.DOWN), Direction.DOWN);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        super.animateTick(pState, pLevel, pPos, pRandom);

        if (pLevel.getBlockEntity(pPos) instanceof AbstractItemDisplayingBlockEntity abstractItemDisplayingBlockEntity) {
            ItemStack itemStack = abstractItemDisplayingBlockEntity.getTheItem();
            double baseX = pPos.getX() + 0.5F;
            double baseY = pPos.getY();
            double baseZ = pPos.getZ() + 0.5F;
            if (itemStack.isEnchanted()) {
                pLevel.addParticle(ParticleTypes.ENCHANT,
                        baseX + (pRandom.nextFloat() - 0.5) * 0.5,
                        baseY + 1.0F + (pRandom.nextFloat() - 0.5) * 0.5,
                        baseZ + (pRandom.nextFloat() - 0.5) * 0.5,
                        0.0, 0.0, 0.0);
            }
            if (itemStack.getRarity() == Rarity.EPIC) {
                pLevel.addParticle(ParticleTypes.WITCH,
                        baseX + (pRandom.nextFloat() - 0.5) * 0.5,
                        baseY + 0.5F + (pRandom.nextFloat() - 0.5) * 0.5,
                        baseZ + (pRandom.nextFloat() - 0.5) * 0.5,
                        0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, Item.TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
        pTooltipComponents.add(Component.empty());
        pTooltipComponents.add(Component.translatable("tooltip.luckysshowcase.can_hold").withStyle(ChatFormatting.GRAY));
        pTooltipComponents.add(CommonComponents.space().append((Component.translatable("tooltip.luckysshowcase.weapons_and_tools").withStyle(ChatFormatting.BLUE))));
    }

    @Override
    public boolean canHoldItem(Item item) {
        return item instanceof DiggerItem || item instanceof SwordItem || item instanceof TridentItem || item instanceof MaceItem;
    }

    @Override
    public SoundEvent placeItemSound() {
        return SoundEvents.DECORATED_POT_INSERT_FAIL;
    }
}
