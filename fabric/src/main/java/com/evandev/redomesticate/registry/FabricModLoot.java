package com.evandev.redomesticate.registry;

import com.evandev.redomesticate.config.ModConfig;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import net.minecraft.world.level.storage.loot.functions.EnchantWithLevelsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class FabricModLoot {

    public static void init() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {

            if (key.equals(BuiltInLootTables.WOODLAND_MANSION)) {
                tableBuilder.pool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .conditionally(LootItemRandomChanceCondition.randomChance((float) ModConfig.get().sinisterCarrotLootChance).build())
                        .add(LootItem.lootTableItem(ModItems.SINISTER_CARROT.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))).build()
                );
                addEnchantedBookPool(tableBuilder, registries, ModEnchantments.VAMPIRE, (float) ModConfig.get().vampirismLootChance);
            }

            if (key.equals(BuiltInLootTables.BURIED_TREASURE)) {
                addEnchantedBookPool(tableBuilder, registries, ModEnchantments.BUBBLING, (float) ModConfig.get().bubblingLootChance);
            }

            if (key.equals(BuiltInLootTables.ABANDONED_MINESHAFT)) {
                addEnchantedBookPool(tableBuilder, registries, ModEnchantments.ORE_SCENTING, (float) ModConfig.get().oreScentingLootChance);
            }

            if (key.equals(BuiltInLootTables.NETHER_BRIDGE)) {
                addEnchantedBookPool(tableBuilder, registries, ModEnchantments.BLAZING_PROTECTION, (float) ModConfig.get().blazingProtectionLootChance);
            }

            if (key.equals(BuiltInLootTables.END_CITY_TREASURE)) {
                addEnchantedBookPool(tableBuilder, registries, ModEnchantments.VOID_CLOUD, (float) ModConfig.get().voidCloudLootChance);
            }

            if (key.equals(BuiltInLootTables.ANCIENT_CITY)) {
                addEnchantedBookPool(tableBuilder, registries, ModEnchantments.MUFFLED, (float) ModConfig.get().muffledLootChance);
            }

            if (key.location().getPath().equals("chests/pet_shop") && key.location().getNamespace().equals("redomesticate")) {
                tableBuilder.pool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(1.0F, 2.0F))
                        .conditionally(LootItemRandomChanceCondition.randomChance(0.5F).build())
                        .add(LootItem.lootTableItem(ModItems.COLLAR_TAG.get())
                                .setWeight(1)
                                .apply(EnchantWithLevelsFunction.enchantWithLevels(registries, UniformGenerator.between(12.0F, 40.0F)))
                        )
                        .add(LootItem.lootTableItem(ModItems.COLLAR_TAG.get())
                                .setWeight(2)
                        ).build()
                );
            }
        });
    }

    /**
     * Helper method to inject an enchanted book into a given loot table pool.
     */
    private static void addEnchantedBookPool(LootTable.Builder tableBuilder,
                                             HolderLookup.Provider registries,
                                             ResourceKey<Enchantment> enchantment,
                                             float chance) {

        var enchantRegistry = registries.lookupOrThrow(Registries.ENCHANTMENT);
        var enchantHolder = enchantRegistry.getOrThrow(enchantment);

        tableBuilder.pool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                .conditionally(LootItemRandomChanceCondition.randomChance(chance).build())
                .add(LootItem.lootTableItem(Items.BOOK)
                        .apply(new EnchantRandomlyFunction.Builder().withEnchantment(enchantHolder))
                ).build()
        );
    }
}