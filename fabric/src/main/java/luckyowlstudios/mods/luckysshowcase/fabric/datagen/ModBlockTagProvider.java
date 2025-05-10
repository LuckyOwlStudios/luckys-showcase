package luckyowlstudios.mods.luckysshowcase.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import luckyowlstudios.mods.luckysshowcase.block.ModBlocks;
import luckyowlstudios.mods.luckysshowcase.tag.ModBlockTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider<Block> {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.BLOCK, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {

        getOrCreateTagBuilder(ModBlockTags.YOUR_TAG).add(ModBlocks.TOOL_RACK);
    }
}
