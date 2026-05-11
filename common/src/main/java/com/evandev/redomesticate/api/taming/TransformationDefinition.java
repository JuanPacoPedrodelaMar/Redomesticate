package com.evandev.redomesticate.api.taming;

import com.evandev.redomesticate.Constants;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.crafting.Ingredient;

public record TransformationDefinition(
        HolderSet<EntityType<?>> targetEntity,
        Ingredient triggerItem,
        EntityType<?> resultEntity,
        Holder<SoundEvent> soundEvent
) {
    public static final ResourceKey<Registry<TransformationDefinition>> REGISTRY_KEY = ResourceKey.createRegistryKey(
            ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "transformation")
    );

    public static final Codec<TransformationDefinition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RegistryCodecs.homogeneousList(Registries.ENTITY_TYPE).fieldOf("target_entity").forGetter(TransformationDefinition::targetEntity),
            Ingredient.CODEC_NONEMPTY.fieldOf("trigger_item").forGetter(TransformationDefinition::triggerItem),
            BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("result_entity").forGetter(TransformationDefinition::resultEntity),
            SoundEvent.CODEC.fieldOf("sound").forGetter(TransformationDefinition::soundEvent)
    ).apply(instance, TransformationDefinition::new));
}