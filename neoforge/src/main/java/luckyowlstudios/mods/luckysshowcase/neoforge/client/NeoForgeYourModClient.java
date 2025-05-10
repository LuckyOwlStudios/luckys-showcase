package luckyowlstudios.mods.luckysshowcase.neoforge.client;

import net.blay09.mods.balm.api.client.BalmClient;
import net.blay09.mods.balm.neoforge.NeoForgeLoadContext;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import luckyowlstudios.mods.luckysshowcase.LuckysShowcase;
import luckyowlstudios.mods.luckysshowcase.client.LuckysShowcaseClient;

@Mod(value = LuckysShowcase.MOD_ID, dist = Dist.CLIENT)
public class NeoForgeYourModClient {

    public NeoForgeYourModClient(IEventBus modEventBus) {
        final var context = new NeoForgeLoadContext(modEventBus);
        BalmClient.initialize(LuckysShowcase.MOD_ID, context, LuckysShowcaseClient::initialize);
    }
}
