package com.evandev.redomesticate.config;

import com.evandev.redomesticate.Constants;
import com.evandev.redomesticate.platform.Services;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ModConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = Services.PLATFORM.getConfigDirectory().resolve(Constants.MOD_ID + ".json").toFile();
    private static ModConfig INSTANCE;

    // General
    public boolean rottenApple = true;
    public int petstoreVillageWeight = 17;

    // Loot Chances
    public double blazingProtectionLootChance = 0.2D;
    public double sinisterCarrotLootChance = 0.3D;
    public double bubblingLootChance = 0.65D;
    public double vampirismLootChance = 0.22D;
    public double sonicBoomLootChance = 0.6D;
    public double paralysisLootChance = 0.1D;
    public double toughLootChance = 0.1D;
    public double shareLootChance = 0.5D;
    public double oreScentingLootChance = 0.15D;

    // Protection
    public boolean protectPetsFromOwner = true;
    public boolean protectPetsFromPets = true;
    public boolean protectChildren = true;
    public boolean reflectDamage = false;
    public boolean displayHitWarning = true;
    public boolean protectTeamMembers = true;
    public boolean respectTeamRules = true;

    public List<String> canHurtPetItem = List.of();
    public List<String> canHurtAllItem = List.of();
    public List<String> noProtectionEntity = List.of();
    public List<String> otherShouldProtectEntity = List.of();
    public List<String> playerCantHurtEntity = List.of();

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

    public Set<EntityType<?>> getNoProtectionEntity() {
        return parseEntities(noProtectionEntity);
    }

    public Set<EntityType<?>> getOtherShouldProtectEntity() {
        return parseEntities(otherShouldProtectEntity);
    }

    public Set<EntityType<?>> getPlayerCantHurtEntity() {
        return parseEntities(playerCantHurtEntity);
    }

    public Set<Item> getCanHurtPetItem() {
        return parseItems(canHurtPetItem);
    }

    public Set<Item> getCanHurtAllItem() {
        return parseItems(canHurtAllItem);
    }

    private Set<EntityType<?>> parseEntities(List<String> list) {
        return list.stream().map(ResourceLocation::parse).filter(BuiltInRegistries.ENTITY_TYPE::containsKey).map(BuiltInRegistries.ENTITY_TYPE::get).collect(Collectors.toSet());
    }

    private Set<Item> parseItems(List<String> list) {
        return list.stream().map(ResourceLocation::parse).filter(BuiltInRegistries.ITEM::containsKey).map(BuiltInRegistries.ITEM::get).collect(Collectors.toSet());
    }
}