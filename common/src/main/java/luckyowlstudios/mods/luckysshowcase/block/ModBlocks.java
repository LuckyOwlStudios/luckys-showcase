package luckyowlstudios.mods.luckysshowcase.block;

import luckyowlstudios.mods.luckysshowcase.block.custom.pedestal.PedestalBlock;
import luckyowlstudios.mods.luckysshowcase.block.custom.weapon_stand.WeaponStandBlock;
import net.blay09.mods.balm.api.block.BalmBlocks;
import net.blay09.mods.balm.api.item.BalmItems;
import net.minecraft.world.level.block.Block;
import luckyowlstudios.mods.luckysshowcase.block.custom.item_rack.ToolRackBlock;

import static net.blay09.mods.balm.api.block.BalmBlocks.blockProperties;
import static luckyowlstudios.mods.luckysshowcase.LuckysShowcase.id;

public class ModBlocks {

    public static Block TOOL_RACK;
    public static Block WEAPON_STAND;
    public static Block PEDESTAL;

    public static void initialize(BalmBlocks blocks) {
        blocks.register(
                (identifier) -> TOOL_RACK = new ToolRackBlock(blockProperties(identifier).dynamicShape().instabreak()),
                BalmItems::blockItem,
                id("tool_rack"));
        blocks.register(
                (identifier) -> WEAPON_STAND = new WeaponStandBlock(blockProperties(identifier).dynamicShape().strength(0.5F, 2.0F)),
                BalmItems::blockItem,
                id("weapon_stand"));
        blocks.register(
                (identifier) -> PEDESTAL = new PedestalBlock(blockProperties(identifier).dynamicShape().strength(1.5F, 6.0F)),
                BalmItems::blockItem,
                id("pedestal"));
    }
}
