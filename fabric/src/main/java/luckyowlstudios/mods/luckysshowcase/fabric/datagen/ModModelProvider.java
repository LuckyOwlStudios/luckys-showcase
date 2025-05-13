package luckyowlstudios.mods.luckysshowcase.fabric.datagen;

import luckyowlstudios.mods.luckysshowcase.LuckysShowcase;
import luckyowlstudios.mods.luckysshowcase.block.ModBlockStateProperties;
import luckyowlstudios.mods.luckysshowcase.block.custom.CarpetType;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.core.Direction;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.*;
import net.minecraft.data.models.model.*;
import luckyowlstudios.mods.luckysshowcase.block.ModBlocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
        createToolRack(blockStateModelGenerator, ModBlocks.TOOL_RACK);
        createWeaponStand(blockStateModelGenerator, ModBlocks.WEAPON_STAND);
        registerPedestalWithCarpet(blockStateModelGenerator, ModBlocks.PEDESTAL);
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        itemModelGenerator.generateFlatItem(ModBlocks.TOOL_RACK.asItem(), ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ModBlocks.WEAPON_STAND.asItem(), ModelTemplates.FLAT_ITEM);
    }

    public final void createToolRack(BlockModelGenerators blockStateModelGenerator, Block block) {
        ResourceLocation model = ResourceLocation.fromNamespaceAndPath(LuckysShowcase.MOD_ID, "block/tool_rack");
        blockStateModelGenerator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, model)).with(createHorizontalFacingDispatch()));
    }

    public final void createWeaponStand(BlockModelGenerators blockStateModelGenerator, Block block) {
        ResourceLocation model = ResourceLocation.fromNamespaceAndPath(LuckysShowcase.MOD_ID, "block/weapon_stand");
        blockStateModelGenerator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, model)).with(createHorizontalFacingDispatch()));
    }

    private void registerPedestalWithCarpet(BlockModelGenerators generator, Block block) {
        ResourceLocation mainModel = ResourceLocation.fromNamespaceAndPath(LuckysShowcase.MOD_ID, "block/pedestal");
        ModelTemplate carpet_overlay = new ModelTemplate(
                Optional.of(ResourceLocation.fromNamespaceAndPath(LuckysShowcase.MOD_ID, "block/pedestal_carpet_overlay")),
                Optional.empty(),
                TextureSlot.TOP,
                TextureSlot.SIDE
        );
        generator.blockStateOutput.accept(createPedestalWithOverlay(generator, block, mainModel, carpet_overlay));
    }

    public static BlockStateGenerator createPedestalWithOverlay(BlockModelGenerators generator, Block block, ResourceLocation baseModelId, ModelTemplate overlayTemplate) {
        MultiPartGenerator multipart = MultiPartGenerator.multiPart(block)
                .with(Variant.variant().with(VariantProperties.MODEL, baseModelId));

        for (CarpetType type : CarpetType.values()) {
            if (type == CarpetType.NONE) continue; // Skip NONE

            // Construct suffix like "_white", "_red", etc.
            String suffix = "_" + type.getSerializedName();

            // Create model file
            ResourceLocation modelId = overlayTemplate.createWithOverride(
                    block,
                    suffix,
                    new TextureMapping()
                            .put(TextureSlot.TOP, ResourceLocation.fromNamespaceAndPath(LuckysShowcase.MOD_ID, "block/pedestal_" + type.getSerializedName() + "_top"))
                            .put(TextureSlot.SIDE, ResourceLocation.fromNamespaceAndPath(LuckysShowcase.MOD_ID, "block/pedestal_" + type.getSerializedName() + "_side")),
                    generator.modelOutput
            );

            // Add conditional variant to multipart
            multipart.with(
                    Condition.condition().term(ModBlockStateProperties.CARPET_TYPE, type),
                    Variant.variant().with(VariantProperties.MODEL, modelId)
            );
        }

        return multipart;
    }

    public static PropertyDispatch createHorizontalFacingDispatch() {
        return PropertyDispatch.property(BlockStateProperties.HORIZONTAL_FACING).select(Direction.EAST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)).select(Direction.SOUTH, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)).select(Direction.WEST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)).select(Direction.NORTH, Variant.variant());
    }
}
