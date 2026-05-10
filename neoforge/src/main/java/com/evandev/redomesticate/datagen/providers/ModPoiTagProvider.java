package com.evandev.redomesticate.datagen.providers;

import com.evandev.redomesticate.Constants;
import com.evandev.redomesticate.registry.ModPOIs;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.PoiTypeTagsProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.PoiTypeTags;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static com.evandev.redomesticate.registry.ModPOIs.getBeds;

public class ModPoiTagProvider extends PoiTypeTagsProvider {
    public ModPoiTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, Constants.MOD_ID, existingFileHelper);
    }

    public static void bootstrap(BootstrapContext<PoiType> bootstrap) {
        ResourceKey<PoiType> petBedKey = ResourceKey.create(Registries.POINT_OF_INTEREST_TYPE, ModPOIs.PET_BED.getId());
        bootstrap.register(petBedKey, new PoiType(getBeds(), 1, 1));
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        ResourceKey<PoiType> petBedKey = ResourceKey.create(Registries.POINT_OF_INTEREST_TYPE, ModPOIs.PET_BED.getId());
        this.tag(PoiTypeTags.ACQUIRABLE_JOB_SITE).add(petBedKey);
    }
}