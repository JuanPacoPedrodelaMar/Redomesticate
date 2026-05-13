package com.evandev.redomesticate.compat.clothconfig;

import com.evandev.redomesticate.config.ModConfig;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ClothConfigIntegration {

    public static Screen createScreen(Screen parent) {
        ModConfig config = ModConfig.get();

        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.translatable("config.redomesticate.title"));

        builder.setSavingRunnable(ModConfig::save);

        ConfigCategory general = builder.getOrCreateCategory(Component.translatable("config.redomesticate.category.general"));
        ConfigCategory loot = builder.getOrCreateCategory(Component.translatable("config.redomesticate.category.loot"));

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        // General Category
        general.addEntry(entryBuilder.startBooleanToggle(Component.translatable("redomesticate.configuration.trinaryCommandSystem"), config.trinaryCommandSystem)
                .setDefaultValue(true)
                .setTooltip(Component.translatable("redomesticate.configuration.trinaryCommandSystem.tooltip"))
                .setSaveConsumer(val -> config.trinaryCommandSystem = val).build());

        general.addEntry(entryBuilder.startBooleanToggle(Component.translatable("redomesticate.configuration.tameableHorse"), config.tameableHorse)
                .setDefaultValue(true)
                .setTooltip(Component.translatable("redomesticate.configuration.tameableHorse.tooltip"))
                .setSaveConsumer(val -> config.tameableHorse = val).build());

        general.addEntry(entryBuilder.startBooleanToggle(Component.translatable("redomesticate.configuration.tameableFox"), config.tameableFox)
                .setDefaultValue(true)
                .setTooltip(Component.translatable("redomesticate.configuration.tameableFox.tooltip"))
                .setSaveConsumer(val -> config.tameableFox = val).build());

        general.addEntry(entryBuilder.startBooleanToggle(Component.translatable("redomesticate.configuration.swingThroughPets"), config.swingThroughPets)
                .setDefaultValue(true)
                .setTooltip(Component.translatable("redomesticate.configuration.swingThroughPets.tooltip"))
                .setSaveConsumer(val -> config.swingThroughPets = val).build());

        general.addEntry(entryBuilder.startBooleanToggle(Component.translatable("redomesticate.configuration.rotten_apple"), config.rottenApple)
                .setDefaultValue(true)
                .setTooltip(Component.translatable("redomesticate.configuration.rotten_apple.tooltip"))
                .setSaveConsumer(val -> config.rottenApple = val).build());

        general.addEntry(entryBuilder.startBooleanToggle(Component.translatable("redomesticate.configuration.petBedRespawns"), config.petBedRespawns)
                .setDefaultValue(true)
                .setTooltip(Component.translatable("redomesticate.configuration.petBedRespawns.tooltip"))
                .setSaveConsumer(val -> config.petBedRespawns = val).build());

        general.addEntry(entryBuilder.startBooleanToggle(Component.translatable("redomesticate.configuration.rabbitsScareRavagers"), config.rabbitsScareRavagers)
                .setDefaultValue(true)
                .setTooltip(Component.translatable("redomesticate.configuration.rabbitsScareRavagers.tooltip"))
                .setSaveConsumer(val -> config.rabbitsScareRavagers = val).build());

        general.addEntry(entryBuilder.startIntField(Component.translatable("redomesticate.configuration.petstore_village_weight"), config.petStoreVillageWeight)
                .setDefaultValue(17)
                .setTooltip(Component.translatable("redomesticate.configuration.petstore_village_weight.tooltip"))
                .setSaveConsumer(val -> config.petStoreVillageWeight = val).build());

        // Loot Category
        loot.addEntry(entryBuilder.startBooleanToggle(Component.translatable("redomesticate.configuration.petCurseEnchantmentsLootOnly"), config.petCurseEnchantmentsLootOnly)
                .setDefaultValue(true)
                .setTooltip(Component.translatable("redomesticate.configuration.petCurseEnchantmentsLootOnly.tooltip"))
                .setSaveConsumer(val -> config.petCurseEnchantmentsLootOnly = val).build());

        loot.addEntry(entryBuilder.startDoubleField(Component.translatable("redomesticate.configuration.blazing_protection_loot_chance"), config.blazingProtectionLootChance)
                .setDefaultValue(0.2D)
                .setTooltip(Component.translatable("redomesticate.configuration.blazing_protection_loot_chance.tooltip"))
                .setSaveConsumer(val -> config.blazingProtectionLootChance = val).build());

        loot.addEntry(entryBuilder.startDoubleField(Component.translatable("redomesticate.configuration.sinister_carrot_loot_chance"), config.sinisterCarrotLootChance)
                .setDefaultValue(0.3D)
                .setTooltip(Component.translatable("redomesticate.configuration.sinister_carrot_loot_chance.tooltip"))
                .setSaveConsumer(val -> config.sinisterCarrotLootChance = val).build());

        loot.addEntry(entryBuilder.startDoubleField(Component.translatable("redomesticate.configuration.bubbling_loot_chance"), config.bubblingLootChance)
                .setDefaultValue(0.65D)
                .setTooltip(Component.translatable("redomesticate.configuration.bubbling_loot_chance.tooltip"))
                .setSaveConsumer(val -> config.bubblingLootChance = val).build());

        loot.addEntry(entryBuilder.startDoubleField(Component.translatable("redomesticate.configuration.vampirism_loot_chance"), config.vampirismLootChance)
                .setDefaultValue(0.22D)
                .setTooltip(Component.translatable("redomesticate.configuration.vampirism_loot_chance.tooltip"))
                .setSaveConsumer(val -> config.vampirismLootChance = val).build());

        loot.addEntry(entryBuilder.startDoubleField(Component.translatable("redomesticate.configuration.voidCloudLootChance"), config.voidCloudLootChance)
                .setDefaultValue(0.19D)
                .setTooltip(Component.translatable("redomesticate.configuration.voidCloudLootChance.tooltip"))
                .setSaveConsumer(val -> config.voidCloudLootChance = val).build());

        loot.addEntry(entryBuilder.startDoubleField(Component.translatable("redomesticate.configuration.muffledLootChance"), config.muffledLootChance)
                .setDefaultValue(0.19D)
                .setTooltip(Component.translatable("redomesticate.configuration.muffledLootChance.tooltip"))
                .setSaveConsumer(val -> config.muffledLootChance = val).build());

        loot.addEntry(entryBuilder.startDoubleField(Component.translatable("redomesticate.configuration.ore_scenting_loot_chance"), config.oreScentingLootChance)
                .setDefaultValue(0.15D)
                .setTooltip(Component.translatable("redomesticate.configuration.ore_scenting_loot_chance.tooltip"))
                .setSaveConsumer(val -> config.oreScentingLootChance = val).build());

        return builder.build();
    }
}