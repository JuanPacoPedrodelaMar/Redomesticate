package com.evandev.redomesticate.client;

import com.evandev.redomesticate.Constants;
import com.evandev.redomesticate.client.particle.*;
import com.evandev.redomesticate.client.render.*;
import com.evandev.redomesticate.registry.ModEntities;
import com.evandev.redomesticate.registry.ModParticles;
import com.evandev.redomesticate.server.entity.HighlightedBlockEntity;
import com.evandev.redomesticate.util.ClientMobTooltip;
import com.evandev.redomesticate.util.ItemMobTooltip;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

import java.util.List;
import java.util.stream.Collectors;

public class NeoForgeClientEvents {

    @EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
    public static class ModBusEvents {

        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(ModEntities.CHAIN_LIGHTNING.get(), ChainLightningRender::new);
            event.registerEntityRenderer(ModEntities.GIANT_BUBBLE.get(), RenderGiantBubble::new);
            event.registerEntityRenderer(ModEntities.PSYCHIC_WALL.get(), RenderPsychicWall::new);
            event.registerEntityRenderer(ModEntities.HIGHLIGHTED_BLOCK.get(), RenderHighlightedBlock::new);
            event.registerEntityRenderer(ModEntities.FEATHER.get(), RenderFeather::new);
        }

        @SubscribeEvent
        public static void setupParticles(RegisterParticleProvidersEvent event) {
            event.registerSpecial(ModParticles.DEFLECTION_SHIELD.get(), new ParticleDeflectionShield.Factory());
            event.registerSpriteSet(ModParticles.MAGNET.get(), ParticleMagnet.Factory::new);
            event.registerSpriteSet(ModParticles.ZZZ.get(), ParticleZZZ.Factory::new);
            event.registerSpriteSet(ModParticles.GIANT_POP.get(), ParticleGiantPop.Factory::new);
            event.registerSpriteSet(ModParticles.SIMPLE_BUBBLE.get(), ParticleSimpleBubble.Factory::new);
            event.registerSpriteSet(ModParticles.VAMPIRE.get(), ParticleVampire.Factory::new);
            event.registerSpriteSet(ModParticles.SNIFF.get(), ParticleSniff.Factory::new);
            event.registerSpriteSet(ModParticles.PSYCHIC_WALL.get(), ParticlePsychicWall.Factory::new);
            event.registerSpecial(ModParticles.INTIMIDATION.get(), new ParticleIntimidation.Factory());
            event.registerSpriteSet(ModParticles.BLIGHT.get(), ParticleBlight.Factory::new);
            event.registerSpriteSet(ModParticles.QUESTION_MARK_PARTICLE_TYPE.get(), ParticleQuestionMark.Factory::new);
            event.registerSpriteSet(ModParticles.LANTERN_BUGS.get(), ParticleLanternBugs.Factory::new);
        }

        @SubscribeEvent
        public static void onRegisterClientTooltip(RegisterClientTooltipComponentFactoriesEvent event) {
            event.register(ItemMobTooltip.class, ClientMobTooltip::new);
        }

        @SubscribeEvent
        @OnlyIn(Dist.CLIENT)
        @SuppressWarnings({"unchecked"})
        public static void onAddLayers(EntityRenderersEvent.AddLayers event) {
            List<EntityType<? extends LivingEntity>> entityTypes = ImmutableList.copyOf(
                    BuiltInRegistries.ENTITY_TYPE.stream()
                            .filter(LayerManager::canApply)
                            .filter(DefaultAttributes::hasSupplier)
                            .map(entityType -> (EntityType<? extends LivingEntity>) entityType)
                            .collect(Collectors.toList()));

            entityTypes.forEach((entityType -> {
                ResourceLocation key = BuiltInRegistries.ENTITY_TYPE.getKey(entityType);
                String modId = key.getNamespace();
                if (!modId.equals("minecraft")) {
                    System.out.println("EntityType: " + key + " | Mod: " + modId);
                }

                EntityRenderer<?> renderer = event.getRenderer(entityType);
                if (renderer != null) {
                    LayerManager.addLayerIfApplicable(entityType, renderer);
                }
            }));
        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                OutlineColorCallback.LISTENERS.add(entity -> {
                    if (entity instanceof HighlightedBlockEntity blockEntity) {
                        return OreColorRegistry.getBlockColor(blockEntity.getBlockState());
                    }
                    return null;
                });
            });
        }
    }
}