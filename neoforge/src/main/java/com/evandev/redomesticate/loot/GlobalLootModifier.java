package com.evandev.redomesticate.loot;

import com.evandev.redomesticate.Constants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.AnyOfCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class GlobalLootModifier extends GlobalLootModifierProvider {
    public GlobalLootModifier(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, Constants.MOD_ID);
    }

    @Override
    public void start() {
        add("sinister_carrot", new ModLootModifier(new LootItemCondition[]{new LootTableIdCondition.Builder(BuiltInLootTables.WOODLAND_MANSION.location()).build()}, 0));
        add("bubbling_enchanted_book", new ModLootModifier(new LootItemCondition[]{new LootTableIdCondition.Builder(BuiltInLootTables.BURIED_TREASURE.location()).build()}, 1));
        add("vampirism_enchanted_book", new ModLootModifier(new LootItemCondition[]{new LootTableIdCondition.Builder(BuiltInLootTables.WOODLAND_MANSION.location()).build()}, 2));
        add("void_cloud_enchanted_book", new ModLootModifier(new LootItemCondition[]{new LootTableIdCondition.Builder(BuiltInLootTables.END_CITY_TREASURE.location()).build()}, 3));
        add("ore_scenting_enchanted_book", new ModLootModifier(new LootItemCondition[]{new LootTableIdCondition.Builder(BuiltInLootTables.ABANDONED_MINESHAFT.location()).build()}, 4));
        add("muffled_enchanted_book", new ModLootModifier(new LootItemCondition[]{new LootTableIdCondition.Builder(BuiltInLootTables.ANCIENT_CITY.location()).build()}, 5));
        add("blazing_protection_enchanted_book", new ModLootModifier(new LootItemCondition[]{new LootTableIdCondition.Builder(BuiltInLootTables.NETHER_BRIDGE.location()).build()}, 6));
    }

    @SafeVarargs
    private LootTableIdCondition.Builder[] manyChests(ResourceKey<LootTable>... lootablekey) {
        return Arrays.stream(lootablekey).map(i -> LootTableIdCondition.builder(i.location())).toArray(LootTableIdCondition.Builder[]::new);
    }
}