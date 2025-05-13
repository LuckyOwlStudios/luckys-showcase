package luckyowlstudios.mods.luckysshowcase.block;

import luckyowlstudios.mods.luckysshowcase.block.custom.CarpetType;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class ModBlockStateProperties {
    public static final EnumProperty<CarpetType> CARPET_TYPE;

    public ModBlockStateProperties() {
    }

    static {
        CARPET_TYPE = EnumProperty.create("carpet_type", CarpetType.class);
    }
}
