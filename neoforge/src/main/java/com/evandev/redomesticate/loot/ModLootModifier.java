package com.evandev.redomesticate.loot;

import com.evandev.redomesticate.config.ModConfig;
import com.evandev.redomesticate.registry.ModEnchantments;
import com.evandev.redomesticate.registry.ModItems;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class ModLootModifier extends LootModifier {
    public static final MapCodec<ModLootModifier> CODEC =
            RecordCodecBuilder.mapCodec(inst ->
                    inst.group(
                                    LOOT_CONDITIONS_CODEC.fieldOf("conditions").forGetter(lm -> lm.conditions),
                                    Codec.INT.fieldOf("loot_type").orElse(0).forGetter((configuration) -> configuration.lootType)
                            )
                            .apply(inst, ModLootModifier::new));

    private final int lootType;

    protected ModLootModifier(LootItemCondition[] conditionsIn, int lootType) {
        super(conditionsIn);
        this.lootType = lootType;
    }

    @Nonnull
    @Override
    protected ObjectArrayList<ItemStack> doApply(@NotNull ObjectArrayList<ItemStack> generatedLoot, @NotNull LootContext context) {
        switch (lootType) {
            case 0 -> {
                if (context.getRandom().nextFloat() < ModConfig.get().sinisterCarrotLootChance) {
                    generatedLoot.add(new ItemStack(ModItems.SINISTER_CARROT.get(), context.getRandom().nextInt(1, 2)));
                }
            }
            case 1 -> {
                if (context.getRandom().nextFloat() < ModConfig.get().bubblingLootChance) {
                    generatedLoot.add(enchantedBook(ModEnchantments.BUBBLING, context.getRandom(), context));
                }
            }
            case 2 -> {
                if (context.getRandom().nextFloat() < ModConfig.get().vampirismLootChance) {
                    generatedLoot.add(enchantedBook(ModEnchantments.VAMPIRE, context.getRandom(), context));
                }
            }
            case 3 -> {
                if (context.getRandom().nextFloat() < ModConfig.get().shareLootChance) {
                    generatedLoot.add(enchantedBook(ModEnchantments.SHARE, context.getRandom(), context));
                }
            }
            case 4 -> {
                if (context.getRandom().nextFloat() < ModConfig.get().oreScentingLootChance) {
                    generatedLoot.add(enchantedBook(ModEnchantments.ORE_SCENTING, context.getRandom(), context));
                }
            }
            case 5 -> {
                if (context.getRandom().nextFloat() < ModConfig.get().sonicBoomLootChance) {
                    generatedLoot.add(enchantedBook(ModEnchantments.SonicBoom, context.getRandom(), context));
                }
            }
            case 6 -> {
                if (context.getRandom().nextFloat() < ModConfig.get().blazingProtectionLootChance) {
                    generatedLoot.add(enchantedBook(ModEnchantments.BLAZING_PROTECTION, context.getRandom(), context));
                }
            }
            case 7 -> {
                if (context.getRandom().nextFloat() < ModConfig.get().paralysisLootChance) {
                    generatedLoot.add(enchantedBook(ModEnchantments.PARALYSIS, context.getRandom(), context));
                }
            }
            case 8 -> {
                if (context.getRandom().nextFloat() < ModConfig.get().toughLootChance) {
                    generatedLoot.add(enchantedBook(ModEnchantments.TOUGH, context.getRandom(), context));
                }
            }

            default -> throw new IllegalStateException("Unexpected value: " + lootType);
        }
        return generatedLoot;
    }

    private ItemStack enchantedBook(ResourceKey<Enchantment> enchantmentKey, RandomSource randomSource, LootContext context) {
        var reg = context.getLevel()
                .registryAccess()
                .registryOrThrow(Registries.ENCHANTMENT);
        var enchant = reg.get(enchantmentKey);

        int maxLevels = enchant.getMaxLevel();

        int level = maxLevels > 1 ? 1 + randomSource.nextInt(maxLevels) : 1;

        return EnchantedBookItem.createForEnchantment(new EnchantmentInstance(reg.wrapAsHolder(enchant), level));
    }

    @Override
    public @NotNull MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}