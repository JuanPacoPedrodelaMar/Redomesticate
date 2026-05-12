package com.evandev.redomesticate.registry;

import com.evandev.redomesticate.Constants;
import com.evandev.redomesticate.content.block.DrumBlock;
import com.evandev.redomesticate.content.block.DyeColors;
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

import java.util.HashMap;
import java.util.function.Supplier;

public class ModBlocks {

    public static final RegistrationProvider<Block> DEF_REG = RegistrationProvider.get(Registries.BLOCK, Constants.MOD_ID);
    public static final HashMap<DyeColor, RegistryObject<Block>> PET_BED_BLOCKS = new HashMap<>();

    public static final RegistryObject<Block> WAYWARD_LANTERN = registerBlockAndItem("wayward_lantern", WaywardLanternBlock::new);
    public static final RegistryObject<Block> DRUM = registerBlockAndItem("drum",
            () -> new DrumBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).sound(SoundType.WOOD).strength(1F).noOcclusion())
    );

    static {
        for (DyeColor color : DyeColors.COLORS.keySet()) {
            RegistryObject<Block> blockObj = registerBlockAndItem("pet_bed_" + color.name().toLowerCase(),
                    () -> new PetBedBlock(color.name().toLowerCase(), color));

            PET_BED_BLOCKS.put(color, blockObj);
        }
    }

    public static RegistryObject<Block> registerBlockAndItem(String name, Supplier<Block> block) {
        RegistryObject<Block> blockObj = DEF_REG.register(name, block);
        ModItems.DEF_REG.register(name, () -> new ModBlockItem(blockObj, new Item.Properties()));
        return blockObj;
    }

    public static void init() {
    }
}