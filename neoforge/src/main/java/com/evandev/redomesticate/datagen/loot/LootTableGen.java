package com.evandev.redomesticate.datagen.loot;

import com.evandev.redomesticate.registry.ModEnchantments;
import com.evandev.redomesticate.registry.ModItems;
import com.evandev.redomesticate.registry.ModLootTables;
import com.evandev.redomesticate.registry.ModTags;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import net.minecraft.world.level.storage.loot.functions.SetEnchantmentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

public class LootTableGen {
    public record ChestLootTables(HolderLookup.Provider provider) implements LootTableSubProvider {
        private Holder<Enchantment> getEnchantment(ResourceKey<Enchantment> key) {
            return provider.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(key);
        }

        @Override
        public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer) {
            consumer.accept(ModLootTables.PET_LOOT_TABLE, LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .name("rotten_apple")
                            .setRolls(ConstantValue.exactly(1))
                            .when(LootItemRandomChanceCondition.randomChance(0.05F))
                            .add(LootItem.lootTableItem(ModItems.ROTTEN_APPLE.get())))
                    .withPool(LootPool.lootPool()
                            .name("sinister_carrot")
                            .setRolls(ConstantValue.exactly(1))
                            .when(LootItemRandomChanceCondition.randomChance(0.05F))
                            .add(LootItem.lootTableItem(ModItems.SINISTER_CARROT.get())))
                    .withPool(LootPool.lootPool()
                            .name("enchant")
                            .setRolls(ConstantValue.exactly(1))
                            .when(LootItemRandomChanceCondition.randomChance(0.15F))

                            .add(enchantItem(Items.BOOK, ModEnchantments.BUBBLING, 1, 2))
                            .add(enchantItem(Items.BOOK, ModEnchantments.AMPHIBIOUS, 1, 1))
                            .add(enchantItem(Items.BOOK, ModEnchantments.VAMPIRE, 1, 2))

                    ).withPool(LootPool.lootPool()
                            .name("petshop_chest_collars")
                            .setRolls(UniformGenerator.between(1, 2))
                            .when(LootItemRandomChanceCondition.randomChance(0.5f))
                            .add(LootItem.lootTableItem(ModItems.COLLAR_TAG.get()).setWeight(1).apply(EnchantRandomlyFunction.randomEnchantment().withOneOf(provider.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(ModTags.TradableEnchantmentKey))))
                            .add(LootItem.lootTableItem(ModItems.COLLAR_TAG.get()).setWeight(2))

                    )
                    .withPool(LootPool.lootPool()
                            .name("petshop_chest")
                            .setRolls(UniformGenerator.between(5, 12))
                            .add(LootItem.lootTableItem(Items.TROPICAL_FISH).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 5))))
                            .add(LootItem.lootTableItem(Items.BONE).setWeight(4).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
                            .add(LootItem.lootTableItem(Items.LEAD).setWeight(4).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2)))).add(LootItem.lootTableItem(Items.IRON_HORSE_ARMOR).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))))
                            .add(LootItem.lootTableItem(Items.AXOLOTL_BUCKET).setWeight(1)).add(LootItem.lootTableItem(Items.TADPOLE_BUCKET).setWeight(2)).add(LootItem.lootTableItem(Items.EMERALD).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))))
                            .add(LootItem.lootTableItem(Items.CARROT).setWeight(4).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))))
                    ));

        }

        private LootPoolEntryContainer.Builder<?> enchantItem(Item item, ResourceKey<Enchantment> enchant, int weight, Integer maxLevel) {
            var enchantment = getEnchantment(enchant);
            return LootItem.lootTableItem(item).setWeight(weight).apply(new SetEnchantmentsFunction.Builder(true).withEnchantment(enchantment, UniformGenerator.between(1, maxLevel)));
        }
    }

    public static class BlockLootTables extends BlockLootSubProvider {
        public final Set<Block> knownBlocks = new HashSet<>();

        public BlockLootTables(HolderLookup.Provider provider) {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags(), provider);
        }

        @Override
        public void generate() {

        }

        @Override
        public void add(@NotNull Block block, LootTable.@NotNull Builder builder) {
            this.knownBlocks.add(block);
            super.add(block, builder);
        }

        @Override
        public @NotNull Iterable<Block> getKnownBlocks() {
            return this.knownBlocks;
        }
    }
}
