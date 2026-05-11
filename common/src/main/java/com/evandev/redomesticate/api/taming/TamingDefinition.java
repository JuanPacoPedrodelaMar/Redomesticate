package com.evandev.redomesticate.api.taming;

import com.evandev.redomesticate.Constants;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.crafting.Ingredient;

public record TamingDefinition(HolderSet<EntityType<?>> entities, Ingredient items, float chance) {

    public static final ResourceKey<Registry<TamingDefinition>> REGISTRY_KEY = ResourceKey.createRegistryKey(
            ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "taming")
    );

    public static final Codec<TamingDefinition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RegistryCodecs.homogeneousList(Registries.ENTITY_TYPE).fieldOf("entities").forGetter(TamingDefinition::entities),
            Ingredient.CODEC_NONEMPTY.fieldOf("items").forGetter(TamingDefinition::items),
            Codec.FLOAT.optionalFieldOf("chance", 0.33f).forGetter(TamingDefinition::chance)
    ).apply(instance, TamingDefinition::new));
}