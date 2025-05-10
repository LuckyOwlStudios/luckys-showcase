package luckyowlstudios.mods.luckysshowcase;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.config.BalmConfigData;
import net.blay09.mods.balm.api.config.Comment;
import net.blay09.mods.balm.api.config.Config;
import net.blay09.mods.balm.api.config.ExpectedType;
import luckyowlstudios.mods.luckysshowcase.network.protocol.ClientboundConfigMessage;

import java.util.List;

@Config(LuckysShowcase.MOD_ID)
public class ModConfig implements BalmConfigData {

    @Comment("This is an example int property")
    public int exampleInt = 1234;

    @ExpectedType(String.class)
    @Comment("This is an example string list property")
    public List<String> exampleStringList = List.of("Hello", "World");

    public static void initialize() {
        Balm.getConfig().registerConfig(ModConfig.class, ClientboundConfigMessage::new);
    }

    public static ModConfig getActive() {
        return Balm.getConfig().getActive(ModConfig.class);
    }
}
