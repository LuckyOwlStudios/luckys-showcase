package luckyowlstudios.mods.luckysshowcase.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.blay09.mods.kuma.api.InputBinding;
import net.blay09.mods.kuma.api.Kuma;
import net.blay09.mods.kuma.api.ManagedKeyMapping;
import luckyowlstudios.mods.luckysshowcase.LuckysShowcase;

import static luckyowlstudios.mods.luckysshowcase.LuckysShowcase.id;

public class ModKeyMappings {

    public static ManagedKeyMapping yourKey;

    public static void initialize() {
        yourKey = Kuma.createKeyMapping(id("your_key"))
                .withDefault(InputBinding.key(InputConstants.KEY_B))
                .handleScreenInput(event -> {
                    LuckysShowcase.logger.info("B was pressed - " + LuckysShowcase.MOD_ID);
                    return true;
                })
                .build();
    }
}
