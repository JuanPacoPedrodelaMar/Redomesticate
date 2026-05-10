package com.evandev.redomesticate.client.integration;

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
        ConfigCategory protection = builder.getOrCreateCategory(Component.translatable("config.redomesticate.category.protection"));

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        // General Category
        general.addEntry(entryBuilder.startBooleanToggle(Component.literal("Rotten Apple"), config.rottenApple).setDefaultValue(true).setSaveConsumer(val -> config.rottenApple = val).build());
        general.addEntry(entryBuilder.startIntField(Component.literal("Pet Store Village Weight"), config.petstoreVillageWeight).setDefaultValue(17).setSaveConsumer(val -> config.petstoreVillageWeight = val).build());

        // Loot Category
        loot.addEntry(entryBuilder.startDoubleField(Component.literal("Blazing Protection Loot Chance"), config.blazingProtectionLootChance).setDefaultValue(0.2D).setSaveConsumer(val -> config.blazingProtectionLootChance = val).build());
        loot.addEntry(entryBuilder.startDoubleField(Component.literal("Bubbling Loot Chance"), config.bubblingLootChance).setDefaultValue(0.65D).setSaveConsumer(val -> config.bubblingLootChance = val).build());
        loot.addEntry(entryBuilder.startDoubleField(Component.literal("Sinister Carrot Loot Chance"), config.sinisterCarrotLootChance).setDefaultValue(0.3D).setSaveConsumer(val -> config.sinisterCarrotLootChance = val).build());

        // Protection Category
        protection.addEntry(entryBuilder.startBooleanToggle(Component.literal("Protect Pets From Owner"), config.protectPetsFromOwner).setDefaultValue(true).setSaveConsumer(val -> config.protectPetsFromOwner = val).build());
        protection.addEntry(entryBuilder.startBooleanToggle(Component.literal("Protect Pets From Pets"), config.protectPetsFromPets).setDefaultValue(true).setSaveConsumer(val -> config.protectPetsFromPets = val).build());
        protection.addEntry(entryBuilder.startBooleanToggle(Component.literal("Protect Children"), config.protectChildren).setDefaultValue(true).setSaveConsumer(val -> config.protectChildren = val).build());
        protection.addEntry(entryBuilder.startBooleanToggle(Component.literal("Reflect Damage"), config.reflectDamage).setDefaultValue(false).setSaveConsumer(val -> config.reflectDamage = val).build());
        protection.addEntry(entryBuilder.startBooleanToggle(Component.literal("Display Hit Warning"), config.displayHitWarning).setDefaultValue(true).setSaveConsumer(val -> config.displayHitWarning = val).build());

        return builder.build();
    }
}