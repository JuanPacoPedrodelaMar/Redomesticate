package com.evandev.redomesticate.registry;

import com.evandev.redomesticate.Constants;
import com.evandev.redomesticate.platform.registry.RegistrationProvider;
import com.evandev.redomesticate.platform.registry.RegistryObject;
import com.evandev.redomesticate.worldgen.PetshopStructurePoolElement;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElementType;

public class ModVillagePieces {
    public static final RegistrationProvider<StructurePoolElementType<?>> DEF_REG =
            RegistrationProvider.get(Registries.STRUCTURE_POOL_ELEMENT, Constants.MOD_ID);

    public static final RegistryObject<StructurePoolElementType<PetshopStructurePoolElement>> PETSHOP =
            DEF_REG.register("petshop", () -> () -> PetshopStructurePoolElement.CODEC);

    public static void init() {
    }
}