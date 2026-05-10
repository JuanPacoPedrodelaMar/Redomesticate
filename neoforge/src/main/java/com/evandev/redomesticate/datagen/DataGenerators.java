package com.evandev.redomesticate.datagen;

import com.evandev.redomesticate.Constants;
import com.evandev.redomesticate.datagen.providers.*;
import com.evandev.redomesticate.loot.GlobalLootModifier;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherDataGen(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        var datapack = new ModDatapackProvider(packOutput, lookupProvider);
        var datapackLookProvider = datapack.getRegistryProvider();
        generator.addProvider(event.includeServer(), datapack);

        var blockTagProvider = new ModBlockTagsProvider(packOutput, lookupProvider, Constants.MOD_ID, existingFileHelper);
        generator.addProvider(event.includeServer(), blockTagProvider);
        generator.addProvider(event.includeServer(), new ModRecipeProvider(packOutput, lookupProvider));
        generator.addProvider(event.includeServer(), new ModItemProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModPoiTagProvider(packOutput, lookupProvider, existingFileHelper));

        generator.addProvider(event.includeServer(), new ModEntityTagsProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModEnchantTagProvider(packOutput, lookupProvider, existingFileHelper));

        generator.addProvider(event.includeServer(), new GlobalLootModifier(packOutput, lookupProvider));
        generator.addProvider(event.includeServer(), new ModItemTagProvider(packOutput, lookupProvider, blockTagProvider.contentsGetter(), Constants.MOD_ID, existingFileHelper));

        generator.addProvider(event.includeServer(), new ModLangProvider(packOutput, Constants.MOD_ID, "en_us"));
        generator.addProvider(event.includeServer(), new ModLootProvider(packOutput, datapackLookProvider));

    }
}
