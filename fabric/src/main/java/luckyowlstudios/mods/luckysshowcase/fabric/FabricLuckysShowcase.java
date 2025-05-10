package luckyowlstudios.mods.luckysshowcase.fabric;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.EmptyLoadContext;
import net.fabricmc.api.ModInitializer;
import luckyowlstudios.mods.luckysshowcase.LuckysShowcase;

public class FabricLuckysShowcase implements ModInitializer {
    @Override
    public void onInitialize() {
        Balm.initializeMod(LuckysShowcase.MOD_ID, EmptyLoadContext.INSTANCE, LuckysShowcase::initialize);
    }
}
