package luckyowlstudios.mods.luckysshowcase.block.custom.pedestal;

import com.mojang.serialization.MapCodec;
import luckyowlstudios.mods.luckysshowcase.block.ModBlockStateProperties;
import luckyowlstudios.mods.luckysshowcase.block.custom.AbstractItemDisplayingBlock;
import luckyowlstudios.mods.luckysshowcase.block.custom.AbstractItemDisplayingBlockEntity;
import luckyowlstudios.mods.luckysshowcase.block.custom.CarpetType;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PedestalBlock extends AbstractItemDisplayingBlock {
    public static final MapCodec<PedestalBlock> CODEC = simpleCodec(PedestalBlock::new);
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<CarpetType> CARPET_TYPE = ModBlockStateProperties.CARPET_TYPE;
    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    protected static final VoxelShape SHAPE = Shapes.or(
            // Top
            Block.box(0.0, 10.0, 0.0, 16.0, 16.0, 16.0),
            // Middle
            Block.box(4.0, 4.0, 4.0, 12.0, 10, 12.0),
            // Bottom
            Block.box(2.0, 0.0, 2.0, 14.0, 4, 14.0));

    public PedestalBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(WATERLOGGED, Boolean.valueOf(false))
                .setValue(CARPET_TYPE, CarpetType.NONE)
                .setValue(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends AbstractItemDisplayingBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new PedestalBlockEntity(pPos, pState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder.add(CARPET_TYPE));
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        Item item = stack.getItem();

        if (item instanceof BlockItem blockItem) {
            Block placedBlock = blockItem.getBlock();
            CarpetType type = BLOCK_TO_CARPET_TYPE.get(placedBlock);

            if (type != null && state.getValue(CARPET_TYPE) == CarpetType.NONE) {
                level.setBlock(pos, state.setValue(CARPET_TYPE, type), 3);
                if (!player.isCreative()) {
                    stack.shrink(1);
                }
                return ItemInteractionResult.SUCCESS;
            }

        } else if (item instanceof ShearsItem && state.getValue(CARPET_TYPE) != CarpetType.NONE) {
            CarpetType type = state.getValue(CARPET_TYPE);
            Block carpetBlock = CARPET_TYPE_TO_BLOCK.get(type);

            if (carpetBlock != null) {
                ItemStack drop = new ItemStack(carpetBlock.asItem());
                if (!player.getInventory().add(drop)) {
                    player.drop(drop, false);
                }

                level.setBlock(pos, state.setValue(CARPET_TYPE, CarpetType.NONE), 3);
                level.playSound(null, pos, SoundEvents.WOOL_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);
                return ItemInteractionResult.SUCCESS;
            }
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hit);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        super.animateTick(pState, pLevel, pPos, pRandom);

        if (pLevel.getBlockEntity(pPos) instanceof AbstractItemDisplayingBlockEntity abstractItemDisplayingBlockEntity) {
            ItemStack itemStack = abstractItemDisplayingBlockEntity.getTheItem();
            double baseX = pPos.getX() + 0.5F;
            double baseY = pPos.getY() + 1.5F;
            double baseZ = pPos.getZ() + 0.5F;
            if (itemStack.isEnchanted()) {
                pLevel.addParticle(ParticleTypes.ENCHANT,
                        baseX + (pRandom.nextFloat() - 0.5) * 0.5,
                        baseY + (pRandom.nextFloat() - 0.5) * 0.5,
                        baseZ + (pRandom.nextFloat() - 0.5) * 0.5,
                        0.0, 0.0, 0.0);
            }
            if (itemStack.getRarity() == Rarity.EPIC) {
                pLevel.addParticle(ParticleTypes.WITCH,
                        baseX + (pRandom.nextFloat() - 0.5) * 0.5,
                        baseY + (pRandom.nextFloat() - 0.5) * 0.5,
                        baseZ + (pRandom.nextFloat() - 0.5) * 0.5,
                        0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        CarpetType type = state.getValue(CARPET_TYPE);
        Block carpetBlock = CARPET_TYPE_TO_BLOCK.get(type);

        if (type != CarpetType.NONE && carpetBlock != null) {
            // Drop the carpet block
            popResource(level, pos, new ItemStack(carpetBlock.asItem()));
        }

        super.playerDestroy(level, player, pos, state, blockEntity, tool);
    }

    @Override
    public void appendHoverText(ItemStack pStack, Item.TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
        pTooltipComponents.add(Component.empty());
        pTooltipComponents.add(Component.translatable("tooltip.luckysshowcase.can_hold").withStyle(ChatFormatting.GRAY));
        pTooltipComponents.add(CommonComponents.space().append((Component.translatable("tooltip.luckysshowcase.items").withStyle(ChatFormatting.BLUE))));
        pTooltipComponents.add(Component.translatable("tooltip.luckysshowcase.can_apply").withStyle(ChatFormatting.GRAY));
        pTooltipComponents.add(CommonComponents.space().append((Component.translatable("tooltip.luckysshowcase.carpets").withStyle(ChatFormatting.BLUE))));
    }

    @Override
    public boolean canHoldItem(Item item) {
        return true;
    }

    @Override
    public SoundEvent placeItemSound() {
        return SoundEvents.ITEM_FRAME_ROTATE_ITEM;
    }

    private static final Map<Block, CarpetType> BLOCK_TO_CARPET_TYPE = new HashMap<>();
    private static final Map<CarpetType, Block> CARPET_TYPE_TO_BLOCK = new HashMap<>();

    static {
        BLOCK_TO_CARPET_TYPE.put(Blocks.WHITE_CARPET, CarpetType.WHITE);
        BLOCK_TO_CARPET_TYPE.put(Blocks.LIGHT_GRAY_CARPET, CarpetType.LIGHT_GRAY);
        BLOCK_TO_CARPET_TYPE.put(Blocks.GRAY_CARPET, CarpetType.GRAY);
        BLOCK_TO_CARPET_TYPE.put(Blocks.BLACK_CARPET, CarpetType.BLACK);
        BLOCK_TO_CARPET_TYPE.put(Blocks.BROWN_CARPET, CarpetType.BROWN);
        BLOCK_TO_CARPET_TYPE.put(Blocks.RED_CARPET, CarpetType.RED);
        BLOCK_TO_CARPET_TYPE.put(Blocks.ORANGE_CARPET, CarpetType.ORANGE);
        BLOCK_TO_CARPET_TYPE.put(Blocks.YELLOW_CARPET, CarpetType.YELLOW);
        BLOCK_TO_CARPET_TYPE.put(Blocks.LIME_CARPET, CarpetType.LIME);
        BLOCK_TO_CARPET_TYPE.put(Blocks.GREEN_CARPET, CarpetType.GREEN);
        BLOCK_TO_CARPET_TYPE.put(Blocks.CYAN_CARPET, CarpetType.CYAN);
        BLOCK_TO_CARPET_TYPE.put(Blocks.LIGHT_BLUE_CARPET, CarpetType.LIGHT_BLUE);
        BLOCK_TO_CARPET_TYPE.put(Blocks.BLUE_CARPET, CarpetType.BLUE);
        BLOCK_TO_CARPET_TYPE.put(Blocks.PURPLE_CARPET, CarpetType.PURPLE);
        BLOCK_TO_CARPET_TYPE.put(Blocks.MAGENTA_CARPET, CarpetType.MAGENTA);
        BLOCK_TO_CARPET_TYPE.put(Blocks.PINK_CARPET, CarpetType.PINK);

        // Create reverse mapping
        BLOCK_TO_CARPET_TYPE.forEach((block, type) -> CARPET_TYPE_TO_BLOCK.put(type, block));
    }

}