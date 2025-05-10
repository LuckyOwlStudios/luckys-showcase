package luckyowlstudios.mods.luckysshowcase.block;

import luckyowlstudios.mods.luckysshowcase.block.custom.pedestal.PedestalBlock;
import net.blay09.mods.balm.api.block.BalmBlocks;
import net.blay09.mods.balm.api.item.BalmItems;
import net.minecraft.world.level.block.Block;
import luckyowlstudios.mods.luckysshowcase.block.custom.item_rack.ToolRackBlock;

import static net.blay09.mods.balm.api.block.BalmBlocks.blockProperties;
import static luckyowlstudios.mods.luckysshowcase.LuckysShowcase.id;

public class ModBlocks {

    public static Block TOOL_RACK;
    public static Block WEAPON_PEDESTAL;

    public static void initialize(BalmBlocks blocks) {
        blocks.register(
                (identifier) -> TOOL_RACK = new ToolRackBlock(blockProperties(identifier).dynamicShape()),
                BalmItems::blockItem,
                id("tool_rack"));
        blocks.register(
                (identifier) -> WEAPON_PEDESTAL = new PedestalBlock(blockProperties(identifier).dynamicShape()),
                BalmItems::blockItem,
                id("pedestal"));
    }
}
