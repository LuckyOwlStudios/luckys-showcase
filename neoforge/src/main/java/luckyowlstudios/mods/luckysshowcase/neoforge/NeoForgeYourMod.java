package luckyowlstudios.mods.luckysshowcase.neoforge;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.neoforge.NeoForgeLoadContext;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import luckyowlstudios.mods.luckysshowcase.LuckysShowcase;

@Mod(LuckysShowcase.MOD_ID)
public class NeoForgeYourMod {

    public NeoForgeYourMod(IEventBus modEventBus) {
        final var context = new NeoForgeLoadContext(modEventBus);
        Balm.initialize(LuckysShowcase.MOD_ID, context, LuckysShowcase::initialize);
    }
}
