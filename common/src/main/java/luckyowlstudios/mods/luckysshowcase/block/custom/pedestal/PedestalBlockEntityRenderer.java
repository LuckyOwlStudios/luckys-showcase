package luckyowlstudios.mods.luckysshowcase.block.custom.pedestal;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

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
            pPoseStack.pushPose();

            // Rotate based on game time + partial ticks
            float speed = 2.0F; // Adjust this value to change the speed of rotation
            float time = ((level.getGameTime() + pPartialTick) % 360) * speed;
            pPoseStack.translate(0.5F, 1.25F, 0.5F);
            pPoseStack.mulPose(Axis.YP.rotationDegrees(time)); // Rotate around Y axis

            Minecraft.getInstance().getItemRenderer().renderStatic(
                    itemStack,
                    ItemDisplayContext.GROUND,
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
}
