package com.evandev.redomesticate.client;

import com.evandev.redomesticate.Constants;
import com.evandev.redomesticate.client.event.OutlineColorCallback;
import com.evandev.redomesticate.client.particle.*;
import com.evandev.redomesticate.client.registry.OreColorRegistry;
import com.evandev.redomesticate.client.render.*;
import com.evandev.redomesticate.network.FabricNetworking;
import com.evandev.redomesticate.registry.ModEntities;
import com.evandev.redomesticate.registry.ModParticles;
import com.evandev.redomesticate.event.EventProxy;
import com.evandev.redomesticate.content.entity.HighlightedBlockEntity;
import com.evandev.redomesticate.util.ClientMobTooltip;
import com.evandev.redomesticate.util.ItemMobTooltip;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;

public class RedomesticateFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.CHAIN_LIGHTNING.get(), ChainLightningRender::new);
        EntityRendererRegistry.register(ModEntities.GIANT_BUBBLE.get(), GiantBubbleRender::new);
        EntityRendererRegistry.register(ModEntities.PSYCHIC_WALL.get(), PsychicWallRender::new);
        EntityRendererRegistry.register(ModEntities.HIGHLIGHTED_BLOCK.get(), HighlightedBlockRender::new);
        EntityRendererRegistry.register(ModEntities.FOLLOWING_JUKEBOX.get(), JukeboxFollowerRender::new);
        EntityRendererRegistry.register(ModEntities.RECALL_BALL.get(), RecallBallRender::new);
        EntityRendererRegistry.register(ModEntities.FEATHER.get(), FeatherRender::new);

        ParticleFactoryRegistry.getInstance().register(ModParticles.DEFLECTION_SHIELD.get(), new ParticleDeflectionShield.Factory());
        ParticleFactoryRegistry.getInstance().register(ModParticles.MAGNET.get(), ParticleMagnet.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.ZZZ.get(), ParticleZZZ.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.GIANT_POP.get(), ParticleGiantPop.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.SIMPLE_BUBBLE.get(), ParticleSimpleBubble.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.VAMPIRE.get(), ParticleVampire.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.SNIFF.get(), ParticleSniff.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.PSYCHIC_WALL.get(), ParticlePsychicWall.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.INTIMIDATION.get(), new ParticleIntimidation.Factory());
        ParticleFactoryRegistry.getInstance().register(ModParticles.BLIGHT.get(), ParticleBlight.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.QUESTION_MARK_PARTICLE_TYPE.get(), ParticleQuestionMark.Factory::new);

        ItemTooltipCallback.EVENT.register(EventProxy::onItemTooltip);

        TooltipComponentCallback.EVENT.register(data -> {
            if (data instanceof ItemMobTooltip tooltipData) {
                return new ClientMobTooltip(tooltipData);
            }
            return null;
        });

        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
            if (LayerManager.canApply(entityType) && entityType != EntityType.ENDER_DRAGON) {
                if (entityRenderer instanceof LivingEntityRenderer livingRenderer) {
                    registrationHelper.register(new LayerPetOverlays(livingRenderer));
                } else {
                    Constants.LOG.warn("Could not apply pet overlays layer to {}. Renderer is not a LivingEntityRenderer.",
                            BuiltInRegistries.ENTITY_TYPE.getKey(entityType));
                }
            }
        });

        OutlineColorCallback.LISTENERS.add(entity -> {
            if (entity instanceof HighlightedBlockEntity blockEntity) {
                return OreColorRegistry.getBlockColor(blockEntity.getBlockState());
            }
            return null;
        });

        FabricNetworking.initClient();
    }
}