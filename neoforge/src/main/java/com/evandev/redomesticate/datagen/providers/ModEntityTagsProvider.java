package com.evandev.redomesticate.datagen.providers;

import com.evandev.redomesticate.Constants;
import com.evandev.redomesticate.registry.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModEntityTagsProvider extends EntityTypeTagsProvider {
    public ModEntityTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ModTags.BLACKLISTED).add(EntityType.PAINTING);
        //For the plains & taiga pet store cage
        this.tag(ModTags.PETSTORE_CAGE_0).add(EntityType.WOLF).add(EntityType.CAT).add(EntityType.RABBIT);
//        For the desert pet store cage
        this.tag(ModTags.PETSTORE_CAGE_1).add(EntityType.FROG).add(EntityType.RABBIT);
//        For the snowy pet store cage
        this.tag(ModTags.PETSTORE_CAGE_2).add(EntityType.FOX).add(EntityType.RABBIT);
        //For the savanna pet store cage
        this.tag(ModTags.PETSTORE_CAGE_3).add(EntityType.FROG).add(EntityType.PARROT);
        //For the plain pet store fish tank
        this.tag(ModTags.PETSTORE_FISHTANK).add(EntityType.TROPICAL_FISH);

        tag(ModTags.INFAMY_TARGET_ATTRACTED).add(EntityType.DROWNED)
                .add(EntityType.HUSK).add(EntityType.ZOMBIE_VILLAGER).add(EntityType.ZOMBIE)
                .add(EntityType.VEX).add(EntityType.SPIDER).add(EntityType.SLIME).add(EntityType.GHAST)
                .add(EntityType.CAVE_SPIDER).add(EntityType.BLAZE).add(EntityType.MAGMA_CUBE)
                .add(EntityType.WITHER).add(EntityType.ENDERMITE).add(EntityType.SHULKER)
                .add(EntityType.PHANTOM).add(EntityType.RAVAGER).addTag(EntityTypeTags.SKELETONS)
                .addTag(EntityTypeTags.RAIDERS)
        ;
    }
}
