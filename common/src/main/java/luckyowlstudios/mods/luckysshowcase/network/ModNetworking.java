package luckyowlstudios.mods.luckysshowcase.network;

import net.blay09.mods.balm.api.network.BalmNetworking;
import net.blay09.mods.balm.api.network.SyncConfigMessage;
import luckyowlstudios.mods.luckysshowcase.ModConfig;
import luckyowlstudios.mods.luckysshowcase.network.protocol.ClientboundConfigMessage;

public class ModNetworking {

    public static void initialize(BalmNetworking networking) {
        SyncConfigMessage.register(ClientboundConfigMessage.TYPE,
                ClientboundConfigMessage.class,
                ClientboundConfigMessage::new,
                ModConfig.class,
                ModConfig::new);
    }
}
