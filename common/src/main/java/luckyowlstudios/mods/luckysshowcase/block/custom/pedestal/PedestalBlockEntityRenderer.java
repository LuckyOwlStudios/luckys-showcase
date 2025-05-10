package luckyowlstudios.mods.luckysshowcase.block.custom.pedestal;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class PedestalBlockEntityRenderer implements BlockEntityRenderer<PedestalBlockEntity> {

    public PedestalBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(PedestalBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        if (!pBlockEntity.hasLevel()) {
            return;
        }

        Level level = pBlockEntity.getLevel();
        ItemStack itemStack = pBlockEntity.getTheItem();

        if (!itemStack.isEmpty()) {

            BlockState blockState = pBlockEntity.getBlockState();
            Direction facing = blockState.getValue(PedestalBlock.FACING);

            pPoseStack.pushPose();

            // Translate the item to the correct position based on the block's facing direction
            getPosition(itemStack, facing, pPoseStack);
            // Rotate the item to face the correct direction based on the block's facing direction
            getRotation(itemStack, facing, pPoseStack);

            Minecraft.getInstance().getItemRenderer().renderStatic(
                    itemStack,
                    ItemDisplayContext.NONE,
                    pPackedLight,
                    OverlayTexture.NO_OVERLAY,
                    pPoseStack,
                    pBufferSource,
                    level,
                    0
            );
            pPoseStack.popPose();
        }
    }

    private void getPosition(ItemStack itemStack, Direction facing, PoseStack pPoseStack) {
        // Translate the item to the correct position based on the block's facing direction
        float heightOffset = 0.5f;
        if (itemStack.getItem() instanceof ShovelItem || itemStack.getItem() instanceof PickaxeItem || itemStack.getItem() instanceof HoeItem || itemStack.getItem() instanceof CrossbowItem || itemStack.getItem() instanceof SwordItem) heightOffset = 0.4f;
        if (itemStack.getItem() instanceof SpyglassItem) heightOffset = 0.625f;

        if (itemStack.getItem() instanceof TridentItem) {
            switch (facing) {
                case NORTH -> pPoseStack.translate(-0.75f, 0.1, 0.3f);
                case EAST -> pPoseStack.translate(0.7f, 0.1, -0.75f);
                case SOUTH -> pPoseStack.translate(1.75f, 0.1, 0.7f);
                case WEST -> pPoseStack.translate(0.3f, 0.1, 1.75f);
            }
        } else if (itemStack.getItem() instanceof ShieldItem) {
            switch (facing) {
                case NORTH -> pPoseStack.translate(0.0, 1, 0.25f);
                case EAST -> pPoseStack.translate(0.75f, 1, 0.0f);
                case SOUTH -> pPoseStack.translate(1.0f, 1, 0.75f);
                case WEST -> pPoseStack.translate(0.25f, 1, 1.0f);
            }
        } else {
            switch (facing)
            {
                case NORTH -> pPoseStack.translate(0.5, heightOffset, 0.8125);
                case EAST -> pPoseStack.translate(0.1875, heightOffset, 0.5f);
                case SOUTH -> pPoseStack.translate(0.5, heightOffset, 0.1875);
                case WEST -> pPoseStack.translate(0.8125, heightOffset, 0.5f);
            }
        }
    }

    private void getRotation(ItemStack itemStack, Direction facing, PoseStack pPoseStack) {
        float flip = 45;
        if (itemStack.getItem() instanceof BowItem || itemStack.getItem() instanceof CrossbowItem) flip = -45;
        if (itemStack.getItem() instanceof SwordItem) flip = -135;
        if (itemStack.getItem() instanceof SpyglassItem) flip = -90;
        if (itemStack.getItem() instanceof FishingRodItem) flip = 0;
        if (itemStack.getItem() instanceof ShieldItem) flip = 0;
        if (itemStack.getItem() instanceof TridentItem) flip = 270;

        switch (facing)
        {
            case NORTH -> {
                pPoseStack.mulPose(Axis.YP.rotationDegrees(180));
                pPoseStack.mulPose(Axis.ZP.rotationDegrees(flip));
            }
            case EAST -> {
                pPoseStack.mulPose(Axis.YP.rotationDegrees(90));
                pPoseStack.mulPose(Axis.ZP.rotationDegrees(flip));
            }
            case SOUTH -> {
                pPoseStack.mulPose(Axis.ZP.rotationDegrees(flip));
            }
            case WEST -> {
                pPoseStack.mulPose(Axis.YP.rotationDegrees(270));
                pPoseStack.mulPose(Axis.ZP.rotationDegrees(flip));
            }
        }
    }
}
