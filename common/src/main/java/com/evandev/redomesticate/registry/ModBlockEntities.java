package com.evandev.redomesticate.registry;

import com.evandev.redomesticate.Constants;
import com.evandev.redomesticate.platform.registry.RegistrationProvider;
import com.evandev.redomesticate.platform.registry.RegistryObject;
import com.evandev.redomesticate.content.block.entity.DrumBlockEntity;
import com.evandev.redomesticate.content.block.entity.PetBedBlockEntity;
import com.evandev.redomesticate.content.block.entity.WaywardLanternBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntities {

    public static final RegistrationProvider<BlockEntityType<?>> DEF_REG = RegistrationProvider.get(Registries.BLOCK_ENTITY_TYPE, Constants.MOD_ID);

    public static final RegistryObject<BlockEntityType<PetBedBlockEntity>> PET_BED = DEF_REG.register("pet_bed", () -> build(BlockEntityType.Builder.of(PetBedBlockEntity::new, ModBlocks.PET_BED_BLOCKS.values().stream().map(RegistryObject::get).toArray(Block[]::new)
    )));

     public static final RegistryObject<BlockEntityType<DrumBlockEntity>> DRUM = DEF_REG.register("drum", () -> build(BlockEntityType.Builder.of(DrumBlockEntity::new,
             ModBlocks.DRUM.get()
     )));

    public static final RegistryObject<BlockEntityType<WaywardLanternBlockEntity>> WAYWARD_LANTERN = DEF_REG.register("wayward_lantern", () -> build(BlockEntityType.Builder.of(WaywardLanternBlockEntity::new,
            ModBlocks.WAYWARD_LANTERN.get()
    )));

    public static <T extends BlockEntity> BlockEntityType<T> build(BlockEntityType.Builder<T> builder) {
        return builder.build(null);
    }

    public static void init() {}
}