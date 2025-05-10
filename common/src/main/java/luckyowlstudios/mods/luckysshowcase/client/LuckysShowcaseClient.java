package luckyowlstudios.mods.luckysshowcase.client;

import luckyowlstudios.mods.luckysshowcase.client.models.ModModels;
import luckyowlstudios.mods.luckysshowcase.client.renderer.ModRenderers;
import net.blay09.mods.balm.api.client.BalmClient;

public class LuckysShowcaseClient {
    public static void initialize() {
        ModRenderers.initialize(BalmClient.getRenderers());
        ModModels.initialize(BalmClient.getModels());
        ModKeyMappings.initialize();
    }
}
