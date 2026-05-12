package com.evandev.redomesticate.datagen.providers;

import com.evandev.redomesticate.Constants;
import com.evandev.redomesticate.registry.ModBlocks;
import com.evandev.redomesticate.registry.ModItems;
import com.evandev.redomesticate.registry.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput pWriter) {

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "wayward_lantern"))).pattern("LLL").pattern("LIL").pattern(" L ").define('I', Items.LANTERN).define('L', Tags.Items.INGOTS_IRON)
                .unlockedBy("has_craft", has(Items.CRAFTING_TABLE)).save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COLLAR_TAG.get()).pattern("I").pattern("C").define('I', Items.CHAIN).define('C', Tags.Items.INGOTS_COPPER)
                .unlockedBy("has_craft", has(Items.CRAFTING_TABLE)).save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FEATHER_ON_A_STICK.get()).pattern("I ").pattern(" C").define('I', Items.FISHING_ROD).define('C', Tags.Items.FEATHERS)
                .unlockedBy("has_craft", has(Items.CRAFTING_TABLE)).save(pWriter);

        ModBlocks.PET_BED_BLOCKS.forEach((color, blockObj) -> {
            ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, blockObj.get(), 1)
                    .unlockedBy("has_bone", has(Items.BONE))
                    .requires(ModTags.PET_BED_KEY)
                    .requires(BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("minecraft", color.getName() + "_dye")))
                    .save(pWriter, Constants.MOD_ID + ":pet_bed_from_dye_" + color.getName());

            ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, blockObj.get(), 1)
                    .unlockedBy("has_bone", has(Items.BONE))
                    .requires(ItemTags.PLANKS)
                    .requires(Items.BONE)
                    .requires(BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("minecraft", color.getName() + "_wool")))
                    .save(pWriter, Constants.MOD_ID + ":pet_bed_item_" + color.getName());
        });
    }
}