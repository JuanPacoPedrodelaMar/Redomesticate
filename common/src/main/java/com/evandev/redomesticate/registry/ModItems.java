package com.evandev.redomesticate.registry;

import com.evandev.redomesticate.Constants;
import com.evandev.redomesticate.content.item.*;
import com.evandev.redomesticate.platform.registry.RegistrationProvider;
import com.evandev.redomesticate.platform.registry.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;

public class ModItems {
    public static final RegistrationProvider<Item> DEF_REG = RegistrationProvider.get(Registries.ITEM, Constants.MOD_ID);

    public static final RegistryObject<Item> COLLAR_TAG = DEF_REG.register("collar_tag", CollarTagItem::new);
    public static final RegistryObject<Item> FEATHER_ON_A_STICK = DEF_REG.register("feather_on_a_stick", FeatherOnAStickItem::new);
    public static final RegistryObject<Item> ROTTEN_APPLE = DEF_REG.register("rotten_apple", RottenAppleItem::new);
    public static final RegistryObject<Item> SINISTER_CARROT = DEF_REG.register("sinister_carrot", SinisterCarrotItem::new);
    public static final RegistryObject<Item> DEFLECTION_SHIELD = DEF_REG.register("deflection_shield", () -> new InventoryOnlyItem(new Item.Properties()));
    public static final RegistryObject<Item> MAGNET = DEF_REG.register("magnet", () -> new InventoryOnlyItem(new Item.Properties()));
    public static final RegistryObject<Item> DEED_OF_OWNERSHIP = DEF_REG.register("deed_of_ownership", DeedOfOwnershipItem::new);

    public static void init() {
    }
}