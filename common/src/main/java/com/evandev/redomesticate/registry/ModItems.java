package com.evandev.redomesticate.registry;

import com.evandev.redomesticate.Constants;
import com.evandev.redomesticate.content.item.*;
import com.evandev.redomesticate.platform.registry.RegistrationProvider;
import com.evandev.redomesticate.platform.registry.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;

public class ModItems {
    public static final RegistrationProvider<Item> ITEM_REGISTRY = RegistrationProvider.get(Registries.ITEM, Constants.MOD_ID);

    public static final RegistryObject<Item> COLLAR_TAG = ITEM_REGISTRY.register("collar_tag", CollarTagItem::new);
    public static final RegistryObject<Item> FEATHER_ON_A_STICK = ITEM_REGISTRY.register("feather_on_a_stick", FeatherOnAStickItem::new);
    public static final RegistryObject<Item> ROTTEN_APPLE = ITEM_REGISTRY.register("rotten_apple", RottenAppleItem::new);
    public static final RegistryObject<Item> SINISTER_CARROT = ITEM_REGISTRY.register("sinister_carrot", SinisterCarrotItem::new);
    public static final RegistryObject<Item> DEFLECTION_SHIELD = ITEM_REGISTRY.register("deflection_shield", () -> new InventoryOnlyItem(new Item.Properties()));
    public static final RegistryObject<Item> MAGNET = ITEM_REGISTRY.register("magnet", () -> new InventoryOnlyItem(new Item.Properties()));
    public static final RegistryObject<Item> DEED_OF_OWNERSHIP = ITEM_REGISTRY.register("deed_of_ownership", DeedOfOwnershipItem::new);

    public static void init() {
    }
}