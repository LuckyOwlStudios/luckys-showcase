package luckyowlstudios.mods.luckysshowcase.fabric.client;

import net.blay09.mods.balm.api.EmptyLoadContext;
import net.blay09.mods.balm.api.client.BalmClient;
import net.fabricmc.api.ClientModInitializer;
import luckyowlstudios.mods.luckysshowcase.LuckysShowcase;
import luckyowlstudios.mods.luckysshowcase.client.LuckysShowcaseClient;

public class FabricLuckysShowcaseClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BalmClient.initializeMod(LuckysShowcase.MOD_ID, EmptyLoadContext.INSTANCE, LuckysShowcaseClient::initialize);
    }
}
