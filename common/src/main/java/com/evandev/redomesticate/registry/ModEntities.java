package com.evandev.redomesticate.registry;

import com.evandev.redomesticate.Constants;
import com.evandev.redomesticate.platform.registry.RegistrationProvider;
import com.evandev.redomesticate.platform.registry.RegistryObject;
import com.evandev.redomesticate.server.entity.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModEntities {

    public static final RegistrationProvider<EntityType<?>> DEF_REG = RegistrationProvider.get(Registries.ENTITY_TYPE, Constants.MOD_ID);

    public static final RegistryObject<EntityType<ChainLightningEntity>> CHAIN_LIGHTNING = DEF_REG.register("chain_lightning", () -> EntityType.Builder.of(ChainLightningEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).fireImmune().build("chain_lightning"));
    public static final RegistryObject<EntityType<GiantBubbleEntity>> GIANT_BUBBLE = DEF_REG.register("giant_bubble", () -> EntityType.Builder.of(GiantBubbleEntity::new, MobCategory.MISC).sized(1.2F, 1.8F).fireImmune().build("giant_bubble"));
    public static final RegistryObject<EntityType<PsychicWallEntity>> PSYCHIC_WALL = DEF_REG.register("psychic_wall", () -> EntityType.Builder.of(PsychicWallEntity::new, MobCategory.MISC).sized(1F, 1F).fireImmune().build("psychic_wall"));
    public static final RegistryObject<EntityType<HighlightedBlockEntity>> HIGHLIGHTED_BLOCK = DEF_REG.register("highlighted_block", () -> EntityType.Builder.of(HighlightedBlockEntity::new, MobCategory.MISC).sized(1.0F, 1.0F).fireImmune().build("highlighted_block"));
    public static final RegistryObject<EntityType<FeatherEntity>> FEATHER = DEF_REG.register("feather", () -> EntityType.Builder.<FeatherEntity>of(FeatherEntity::new, MobCategory.MISC).sized(0.2F, 0.2F).fireImmune().build("feather"));

    public static void init() {
    }
}