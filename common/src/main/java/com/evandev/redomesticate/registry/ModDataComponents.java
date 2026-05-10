package com.evandev.redomesticate.registry;

import com.evandev.redomesticate.Constants;
import com.evandev.redomesticate.platform.registry.RegistrationProvider;
import com.evandev.redomesticate.platform.registry.RegistryObject;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;

import java.util.function.UnaryOperator;

public class ModDataComponents {
    public static final RegistrationProvider<DataComponentType<?>> DATA_COMPONENT_TYPES =
            RegistrationProvider.get(Registries.DATA_COMPONENT_TYPE, Constants.MOD_ID);

    public static final RegistryObject<DataComponentType<CompoundTag>> ENTITY_HOLDER = register("entity_holder",
            builder -> builder.persistent(CompoundTag.CODEC));
    public static final RegistryObject<DataComponentType<Boolean>> RELEASE_MODE = register("release_mode",
            builder -> builder.persistent(Codec.BOOL));


    private static <T> RegistryObject<DataComponentType<T>> register(String name,
                                                                     UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return DATA_COMPONENT_TYPES.register(name, () -> builderOperator.apply(DataComponentType.builder()).build());
    }

    public static void init() {
    }
}