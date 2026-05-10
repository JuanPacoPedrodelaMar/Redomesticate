package com.evandev.redomesticate.registry;

import com.evandev.redomesticate.Constants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;

public class ModTags {
    public static final TagKey<Item> COLLAR_TAG_tagkey = TagKey.create(BuiltInRegistries.ITEM.key(), ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "collar_tag_key"));
    public static final TagKey<PoiType> animal_tamer_tag = TagKey.create(BuiltInRegistries.POINT_OF_INTEREST_TYPE.key(), ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "animal_tamer_tag_key"));
    public static final TagKey<EntityType<?>> petstore_cage_0 = TagKey.create(BuiltInRegistries.ENTITY_TYPE.key(), ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "petstore_cage_0"));
    public static final TagKey<EntityType<?>> petstore_cage_1 = TagKey.create(BuiltInRegistries.ENTITY_TYPE.key(), ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "petstore_cage_1"));
    public static final TagKey<EntityType<?>> petstore_cage_2 = TagKey.create(BuiltInRegistries.ENTITY_TYPE.key(), ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "petstore_cage_2"));
    public static final TagKey<EntityType<?>> petstore_cage_3 = TagKey.create(BuiltInRegistries.ENTITY_TYPE.key(), ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "petstore_cage_3"));
    public static final TagKey<EntityType<?>> petstore_fishtank = TagKey.create(BuiltInRegistries.ENTITY_TYPE.key(), ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "petstore_fishtank"));

    public static final TagKey<Item> PetBedKey = TagKey.create(BuiltInRegistries.ITEM.key(), ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "pet_beds"));
    public static final TagKey<Enchantment> TradableEnchantmentKey = TagKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "trade_enchantment_book"));
    public static final TagKey<Enchantment> INFUSE_EXTRA = TagKey.create(Registries.ENCHANTMENT, ResourceLocation.parse("enchantinginfuser:infuse_extra"));

    public static TagKey<EntityType<?>> infamy_target_attracted = TagKey.create(BuiltInRegistries.ENTITY_TYPE.key(), ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "infamy_target_attracted"));
    public static final TagKey<EntityType<?>> blacklisted = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "blacklisted"));

    public static final TagKey<EntityType<?>> PETSTORE_FISHTANK = registerEntity("petstore_fishtank");
    public static final TagKey<EntityType<?>> PETSTORE_CAGE_0 = registerEntity("petstore_cage_0");
    public static final TagKey<EntityType<?>> PETSTORE_CAGE_1 = registerEntity("petstore_cage_1");
    public static final TagKey<EntityType<?>> PETSTORE_CAGE_2 = registerEntity("petstore_cage_2");
    public static final TagKey<EntityType<?>> PETSTORE_CAGE_3 = registerEntity("petstore_cage_3");
    public static final TagKey<EntityType<?>> REFUSES_COLLAR_TAGS = registerEntity("refuses_collar_tags");
    public static final TagKey<EntityType<?>> REFUSES_PET_BEDS = registerEntity("refuses_pet_beds");
    public static final TagKey<Item> TAME_FROGS_WITH = registerItem("tame_frogs_with");

    private static TagKey<EntityType<?>> registerEntity(String name) {
        return TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name));
    }

    private static TagKey<Item> registerItem(String name) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name));
    }
}
