package luckyowlstudios.mods.luckysshowcase.forge;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.EmptyLoadContext;
import net.blay09.mods.balm.api.client.BalmClient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import luckyowlstudios.mods.luckysshowcase.LuckysShowcase;
import luckyowlstudios.mods.luckysshowcase.client.LuckysShowcaseClient;

@Mod(LuckysShowcase.MOD_ID)
public class ForgeYourMod {

    public ForgeYourMod(FMLJavaModLoadingContext context) {
        Balm.initializeMod(LuckysShowcase.MOD_ID, EmptyLoadContext.INSTANCE, LuckysShowcase::initialize);
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> BalmClient.initializeMod(LuckysShowcase.MOD_ID, EmptyLoadContext.INSTANCE, LuckysShowcaseClient::initialize));
    }

}
