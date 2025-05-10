package luckyowlstudios.mods.luckysshowcase.item;

import luckyowlstudios.mods.luckysshowcase.block.ModBlocks;
import net.blay09.mods.balm.api.DeferredObject;
import net.blay09.mods.balm.api.item.BalmItems;
import net.minecraft.client.gui.components.tabs.Tab;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import luckyowlstudios.mods.luckysshowcase.LuckysShowcase;

import static net.blay09.mods.balm.api.item.BalmItems.itemProperties;
import static luckyowlstudios.mods.luckysshowcase.LuckysShowcase.id;

public class ModItems {
    public static DeferredObject<CreativeModeTab> creativeModeTab;

//    public static Item TEST_ITEM;

    public static void initialize(BalmItems items) {
//        items.registerItem((identifier) -> TEST_ITEM = new Item(itemProperties(identifier)), id("test_item"));

        creativeModeTab = items.registerCreativeModeTab(() -> new ItemStack(ModBlocks.TOOL_RACK.asItem()), id(LuckysShowcase.MOD_ID));
    }

}
