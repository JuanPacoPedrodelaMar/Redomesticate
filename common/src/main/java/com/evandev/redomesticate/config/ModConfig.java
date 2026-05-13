package com.evandev.redomesticate.config;

import com.evandev.redomesticate.Constants;
import com.evandev.redomesticate.platform.Services;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ModConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = Services.PLATFORM.getConfigDirectory().resolve(Constants.MOD_ID + ".json").toFile();
    private static ModConfig INSTANCE;

    // General
    public boolean trinaryCommandSystem = true;
    public boolean tameableHorse = true;
    public boolean tameableFox = true;
    public boolean swingThroughPets = true;
    public boolean rottenApple = true;
    public boolean petBedRespawns = true;
    public boolean rabbitsScareRavagers = true;
    public boolean animalTamerVillager = true;
    public int petStoreVillageWeight = 17;

    // Loot Chances
    public boolean petCurseEnchantmentsLootOnly = true;
    public double blazingProtectionLootChance = 0.2D;
    public double sinisterCarrotLootChance = 0.3D;
    public double bubblingLootChance = 0.65D;
    public double vampirismLootChance = 0.22D;
    public double voidCloudLootChance = 0.19D;
    public double muffledLootChance = 0.19D;
    public double oreScentingLootChance = 0.15D;

    public static ModConfig get() {
        if (INSTANCE == null) {
            load();
        }
        return INSTANCE;
    }

    public static void load() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                INSTANCE = GSON.fromJson(reader, ModConfig.class);
            } catch (Exception e) {
                Constants.LOG.error("Failed to load " + Constants.MOD_ID + ".json", e);
                INSTANCE = new ModConfig();
                save();
            }
        } else {
            INSTANCE = new ModConfig();
            save();
        }
    }

    public static void save() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(INSTANCE, writer);
        } catch (IOException e) {
            Constants.LOG.error("Failed to save " + Constants.MOD_ID + ".json", e);
        }
    }
}