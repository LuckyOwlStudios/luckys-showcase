package luckyowlstudios.mods.luckysshowcase.block.custom;

import luckyowlstudios.mods.luckysshowcase.LuckysShowcase;
import luckyowlstudios.mods.luckysshowcase.block.ModBlocks;
import luckyowlstudios.mods.luckysshowcase.block.custom.item_rack.ToolRackBlockEntity;
import luckyowlstudios.mods.luckysshowcase.block.custom.pedestal.PedestalBlockEntity;
import luckyowlstudios.mods.luckysshowcase.block.custom.weapon_stand.WeaponStandBlockEntity;
import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.DeferredObject;
import net.blay09.mods.balm.api.block.BalmBlockEntities;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntityType {
    public static BalmBlockEntities blockEntities = Balm.getBlockEntities();

    public static DeferredObject<BlockEntityType<ToolRackBlockEntity>> TOOL_RACK = blockEntities.registerBlockEntity(id("tool_rack"),
            ToolRackBlockEntity::new,
            () -> new Block[]{ModBlocks.TOOL_RACK});

    public static DeferredObject<BlockEntityType<WeaponStandBlockEntity>> WEAPON_STAND = blockEntities.registerBlockEntity(id("weapon_stand"),
            WeaponStandBlockEntity::new,
            () -> new Block[]{ModBlocks.WEAPON_STAND});

    public static DeferredObject<BlockEntityType<PedestalBlockEntity>> PEDESTAL = blockEntities.registerBlockEntity(id("pedestal"),
            PedestalBlockEntity::new,
            () -> new Block[]{ModBlocks.PEDESTAL});

    public static void initialize(BalmBlockEntities blockEntities) {
    }

    private static ResourceLocation id(String name) {
        return ResourceLocation.fromNamespaceAndPath(LuckysShowcase.MOD_ID, name);
    }

}
