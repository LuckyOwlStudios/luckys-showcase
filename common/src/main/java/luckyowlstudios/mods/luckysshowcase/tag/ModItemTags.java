package luckyowlstudios.mods.luckysshowcase.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import static luckyowlstudios.mods.luckysshowcase.LuckysShowcase.id;

public class ModItemTags {
    public static final TagKey<Item> YOUR_TAG = TagKey.create(Registries.ITEM, id("baits"));
}
