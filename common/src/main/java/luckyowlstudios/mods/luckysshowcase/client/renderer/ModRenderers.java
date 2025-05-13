package luckyowlstudios.mods.luckysshowcase.client.renderer;

import luckyowlstudios.mods.luckysshowcase.LuckysShowcase;
import luckyowlstudios.mods.luckysshowcase.block.custom.ModBlockEntityType;
import luckyowlstudios.mods.luckysshowcase.block.custom.item_rack.ToolRackBlockEntityRenderer;
import luckyowlstudios.mods.luckysshowcase.block.custom.pedestal.PedestalBlockEntityRenderer;
import luckyowlstudios.mods.luckysshowcase.block.custom.weapon_stand.WeaponStandBlockEntityRenderer;
import net.blay09.mods.balm.api.client.rendering.BalmRenderers;

public class ModRenderers {

    public static void initialize(BalmRenderers renderers) {
        renderers.registerBlockEntityRenderer(LuckysShowcase.id("tool_rack"), ModBlockEntityType.TOOL_RACK::get, ToolRackBlockEntityRenderer::new);
        renderers.registerBlockEntityRenderer(LuckysShowcase.id("weapon_stand"), ModBlockEntityType.WEAPON_STAND::get, WeaponStandBlockEntityRenderer::new);
        renderers.registerBlockEntityRenderer(LuckysShowcase.id("pedestal"), ModBlockEntityType.PEDESTAL::get, PedestalBlockEntityRenderer::new);
    }
}
