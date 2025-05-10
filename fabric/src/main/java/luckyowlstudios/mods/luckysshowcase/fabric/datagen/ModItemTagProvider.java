package luckyowlstudios.mods.luckysshowcase.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import luckyowlstudios.mods.luckysshowcase.item.ModItems;
import luckyowlstudios.mods.luckysshowcase.tag.ModItemTags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider<Item> {
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.ITEM, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookup) {
//        getOrCreateTagBuilder(ModItemTags.YOUR_TAG).add(ModItems.TEST_ITEM);
    }
}
