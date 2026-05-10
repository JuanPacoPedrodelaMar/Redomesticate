package com.evandev.redomesticate.datagen.providers;

import com.evandev.redomesticate.datagen.loot.LootTableGen;
import com.evandev.redomesticate.registry.ModLootTables;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModLootProvider extends LootTableProvider {
    public ModLootProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, Set.of(ModLootTables.PET_LOOT_TABLE), List.of(
                new SubProviderEntry(LootTableGen.ChestLootTables::new, LootContextParamSets.CHEST),
                new SubProviderEntry(LootTableGen.BlockLootTables::new, LootContextParamSets.BLOCK)

        ), registries);
    }
}
