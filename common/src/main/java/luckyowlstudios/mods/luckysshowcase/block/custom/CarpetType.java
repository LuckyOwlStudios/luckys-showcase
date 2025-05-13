package luckyowlstudios.mods.luckysshowcase.block.custom;

import net.minecraft.util.StringRepresentable;

public enum CarpetType implements StringRepresentable {
    NONE("none"),
    WHITE("white"),
    LIGHT_GRAY("light_gray"),
    GRAY("gray"),
    BLACK("black"),
    BROWN("brown"),
    RED("red"),
    ORANGE("orange"),
    YELLOW("yellow"),
    LIME("lime"),
    GREEN("green"),
    CYAN("cyan"),
    LIGHT_BLUE("light_blue"),
    BLUE("blue"),
    PURPLE("purple"),
    MAGENTA("magenta"),
    PINK("pink"),
    TRADER("trader");

    private final String name;

    CarpetType(final String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String getSerializedName() {
        return this.name;
    }
}
