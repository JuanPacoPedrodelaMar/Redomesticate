package com.evandev.redomesticate.datagen.providers;

import com.evandev.redomesticate.Constants;
import com.evandev.redomesticate.registry.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {
        var axe = this.tag(BlockTags.MINEABLE_WITH_AXE);
        for (var item : ModBlocks.PET_BED_BLOCKS.values()) {
            axe.add(item.get());
        }
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.WAYWARD_LANTERN.get());
    }

    @Override
    public @NotNull String getName() {
        return Constants.MOD_NAME + " block tags";
    }
}
