package luckyowlstudios.mods.luckysshowcase.block.custom.weapon_stand;

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

public class WeaponStandBlockEntityRenderer implements BlockEntityRenderer<WeaponStandBlockEntity> {

    public WeaponStandBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(WeaponStandBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        if (!pBlockEntity.hasLevel()) {
            return;
        }

        Level level = pBlockEntity.getLevel();
        ItemStack itemStack = pBlockEntity.getTheItem();

        if (!itemStack.isEmpty()) {

            BlockState blockState = pBlockEntity.getBlockState();
            Direction facing = blockState.getValue(WeaponStandBlock.FACING);

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
        if (itemStack.getItem() instanceof TridentItem) {
            float heightOffset = 2.25f;
            float xOffset = switch (facing) {
                case NORTH, WEST -> 0.0f;
                default -> 1.0F;
            };
            float zOffset = switch (facing) {
                case SOUTH, WEST -> 1.0f;
                default -> 0.0F;
            };
            pPoseStack.translate(xOffset, heightOffset, zOffset);
        } else {
            float heightOffset = 0.75f;
            if (itemStack.getItem() instanceof ShovelItem || itemStack.getItem() instanceof PickaxeItem || itemStack.getItem() instanceof HoeItem || itemStack.getItem() instanceof CrossbowItem) heightOffset = 0.7f;
            if (itemStack.getItem() instanceof AxeItem) heightOffset = 0.65f;
            if (itemStack.getItem() instanceof SpyglassItem) heightOffset = 0.625f;
            pPoseStack.translate(0.5F, heightOffset, 0.5F);
        }
    }

    private void getRotation(ItemStack itemStack, Direction facing, PoseStack pPoseStack) {
        float flip = -135;
        if (itemStack.getItem() instanceof TridentItem) flip = 0;

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
