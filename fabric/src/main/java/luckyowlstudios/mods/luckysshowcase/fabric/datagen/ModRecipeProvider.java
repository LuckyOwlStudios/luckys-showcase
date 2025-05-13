package luckyowlstudios.mods.luckysshowcase.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import luckyowlstudios.mods.luckysshowcase.LuckysShowcase;
import luckyowlstudios.mods.luckysshowcase.block.ModBlocks;
import luckyowlstudios.mods.luckysshowcase.item.ModItems;

import java.util.concurrent.CompletableFuture;

import static net.minecraft.data.recipes.ShapedRecipeBuilder.shaped;
import static net.minecraft.data.recipes.ShapelessRecipeBuilder.shapeless;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider);
    }

    @Override
    public void buildRecipes(RecipeOutput exporter) {
        shaped(RecipeCategory.DECORATIONS, ModBlocks.TOOL_RACK, 1)
                .pattern("SDS")
                .define('D', ItemTags.PLANKS)
                .define('S', Items.IRON_INGOT)
                .unlockedBy("has_iron", has(Items.IRON_INGOT))
                .save(exporter);
        shaped(RecipeCategory.DECORATIONS, ModBlocks.WEAPON_STAND, 1)
                .pattern(" D ")
                .pattern("SSS")
                .define('D', Items.STONE)
                .define('S', ItemTags.PLANKS)
                .unlockedBy("has_stone", has(Items.STONE))
                .save(exporter);
        shaped(RecipeCategory.DECORATIONS, ModBlocks.PEDESTAL, 1)
                .pattern("SSS")
                .pattern(" S ")
                .define('S', Items.STONE_BRICKS)
                .unlockedBy("has_stone", has(Items.STONE))
                .save(exporter);
    }

    @Override
    public String getName() {
        return LuckysShowcase.MOD_ID;
    }
}
