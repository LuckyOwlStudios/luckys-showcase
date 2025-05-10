package luckyowlstudios.mods.luckysshowcase.client.renderer;

import luckyowlstudios.mods.luckysshowcase.LuckysShowcase;
import luckyowlstudios.mods.luckysshowcase.block.custom.ModBlockEntityType;
import luckyowlstudios.mods.luckysshowcase.block.custom.item_rack.ToolRackBlockEntityRenderer;
import net.blay09.mods.balm.api.client.rendering.BalmRenderers;

public class ModRenderers {

    public static void initialize(BalmRenderers renderers) {
        renderers.registerBlockEntityRenderer(LuckysShowcase.id("tool_rack"), ModBlockEntityType.TOOL_RACK::get, ToolRackBlockEntityRenderer::new);
    }
}
