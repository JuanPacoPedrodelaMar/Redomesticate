package com.evandev.redomesticate.registry;

import com.evandev.redomesticate.Constants;
import com.evandev.redomesticate.content.block.DrumBlock;
import com.evandev.redomesticate.content.block.PetBedBlock;
import com.evandev.redomesticate.content.block.WaywardLanternBlock;
import com.evandev.redomesticate.content.item.ModBlockItem;
import com.evandev.redomesticate.platform.registry.RegistrationProvider;
import com.evandev.redomesticate.platform.registry.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import java.util.LinkedHashMap;
import java.util.function.Supplier;

public class ModBlocks {

    public static final RegistrationProvider<Block> BLOCK_REGISTRY = RegistrationProvider.get(Registries.BLOCK, Constants.MOD_ID);
    public static final LinkedHashMap<DyeColor, RegistryObject<Block>> PET_BED_BLOCKS = new LinkedHashMap<>();

    public static final RegistryObject<Block> WAYWARD_LANTERN = registerBlockAndItem("wayward_lantern", WaywardLanternBlock::new);
    public static final RegistryObject<Block> COMMAND_DRUM = registerBlockAndItem("drum",
            () -> new DrumBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).sound(SoundType.WOOD).strength(1F).noOcclusion())
    );

    static {
        DyeColor[] BED_ORDER = new DyeColor[]{
                DyeColor.WHITE, DyeColor.LIGHT_GRAY, DyeColor.GRAY, DyeColor.BLACK,
                DyeColor.BROWN, DyeColor.RED, DyeColor.ORANGE, DyeColor.YELLOW,
                DyeColor.LIME, DyeColor.GREEN, DyeColor.CYAN, DyeColor.LIGHT_BLUE,
                DyeColor.BLUE, DyeColor.PURPLE, DyeColor.MAGENTA, DyeColor.PINK
        };

        for (DyeColor color : BED_ORDER) {
            RegistryObject<Block> blockObj = registerBlockAndItem("pet_bed_" + color.name().toLowerCase(),
                    () -> new PetBedBlock(color.name().toLowerCase(), color));

            PET_BED_BLOCKS.put(color, blockObj);
        }
    }

    public static RegistryObject<Block> registerBlockAndItem(String name, Supplier<Block> block) {
        RegistryObject<Block> blockObj = BLOCK_REGISTRY.register(name, block);
        ModItems.ITEM_REGISTRY.register(name, () -> new ModBlockItem(blockObj, new Item.Properties()));
        return blockObj;
    }

    public static void init() {
    }
}