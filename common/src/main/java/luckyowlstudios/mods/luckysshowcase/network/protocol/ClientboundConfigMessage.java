package luckyowlstudios.mods.luckysshowcase.network.protocol;

import net.blay09.mods.balm.api.network.SyncConfigMessage;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import luckyowlstudios.mods.luckysshowcase.ModConfig;

import static luckyowlstudios.mods.luckysshowcase.LuckysShowcase.id;

public class ClientboundConfigMessage extends SyncConfigMessage<ModConfig> {
    public static final CustomPacketPayload.Type<ClientboundConfigMessage> TYPE = new CustomPacketPayload.Type<>(id("config"));

    public ClientboundConfigMessage(ModConfig yourModConfig) {
        super(TYPE, yourModConfig);
    }
}
