package luckyowlstudios.mods.luckysshowcase;

import luckyowlstudios.mods.luckysshowcase.block.custom.ModBlockEntityType;
import net.blay09.mods.balm.api.Balm;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import luckyowlstudios.mods.luckysshowcase.block.ModBlocks;
import luckyowlstudios.mods.luckysshowcase.item.ModItems;
import luckyowlstudios.mods.luckysshowcase.network.ModNetworking;

public class LuckysShowcase {

    public static final Logger logger = LoggerFactory.getLogger(LuckysShowcase.class);

    public static final String MOD_ID = "luckysshowcase";

    public static void initialize() {
        ModConfig.initialize();
        ModNetworking.initialize(Balm.getNetworking());
        ModBlocks.initialize(Balm.getBlocks());
        ModItems.initialize(Balm.getItems());
        ModBlockEntityType.initialize(Balm.getBlockEntities());
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

}
