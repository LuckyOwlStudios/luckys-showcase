package luckyowlstudios.mods.luckysshowcase.fabric.datagen;

import luckyowlstudios.mods.luckysshowcase.LuckysShowcase;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.Direction;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.*;
import luckyowlstudios.mods.luckysshowcase.block.ModBlocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
        createToolRack(blockStateModelGenerator, ModBlocks.TOOL_RACK);
        createWeaponPedestal(blockStateModelGenerator, ModBlocks.WEAPON_PEDESTAL);
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        itemModelGenerator.generateFlatItem(ModBlocks.TOOL_RACK.asItem(), ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ModBlocks.WEAPON_PEDESTAL.asItem(), ModelTemplates.FLAT_ITEM);
    }

    public final void createToolRack(BlockModelGenerators blockStateModelGenerator, Block block) {
        ResourceLocation model = ResourceLocation.fromNamespaceAndPath(LuckysShowcase.MOD_ID, "block/tool_rack");
        blockStateModelGenerator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, model)).with(createHorizontalFacingDispatch()));
    }

    public final void createWeaponPedestal(BlockModelGenerators blockStateModelGenerator, Block block) {
        ResourceLocation model = ResourceLocation.fromNamespaceAndPath(LuckysShowcase.MOD_ID, "block/weapon_pedestal");
        blockStateModelGenerator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, model)).with(createHorizontalFacingDispatch()));
    }

    public static PropertyDispatch createHorizontalFacingDispatch() {
        return PropertyDispatch.property(BlockStateProperties.HORIZONTAL_FACING).select(Direction.EAST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)).select(Direction.SOUTH, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)).select(Direction.WEST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)).select(Direction.NORTH, Variant.variant());
    }
}
