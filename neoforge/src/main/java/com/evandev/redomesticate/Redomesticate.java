package com.evandev.redomesticate;

import com.evandev.redomesticate.client.ClientConfigSetup;
import com.evandev.redomesticate.platform.NeoForgeRegistrationProvider;
import com.evandev.redomesticate.registry.NeoForgeModLootModifiers;
import com.evandev.redomesticate.content.ServerProxy;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.*;
import net.neoforged.neoforge.event.entity.item.ItemExpireEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.ExplosionEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;

@Mod(Constants.MOD_ID)
public class Redomesticate {
    public Redomesticate(IEventBus modEventBus, ModContainer modContainer) {
        CommonClass.init();
        NeoForgeRegistrationProvider.registerAll(modEventBus);
        NeoForgeModLootModifiers.register(modEventBus);
        modEventBus.addListener(this::commonSetup);

        NeoForge.EVENT_BUS.register(this);

        if (FMLEnvironment.dist.isClient()) {
            ClientConfigSetup.register(modContainer);
        }
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    @SubscribeEvent
    public void onServerStart(ServerStartingEvent event) {
        ServerProxy.serverStart(event.getServer());
    }

    @SubscribeEvent
    public void onServerTick(LevelTickEvent.Post event) {
        if (event.getLevel() instanceof ServerLevel serverLevel) {
            ServerProxy.onServerTick(serverLevel);
        }
    }

    @SubscribeEvent
    public void onLivingDrops(LivingDropsEvent event) {
        if (ServerProxy.onLivingDrops(event.getEntity())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onLivingHurt(LivingDamageEvent.Pre event) {
        ServerProxy.onTameHurt(event.getEntity(), event.getSource());
        event.setNewDamage(event.getOriginalDamage());
    }

    @SubscribeEvent
    public void onLivingDamage(LivingIncomingDamageEvent event) {
        if (ServerProxy.onLivingDamage(event.getEntity(), event.getSource(), event.getAmount())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onLivingDamagePost(LivingDamageEvent.Post event) {
        ServerProxy.onEntityHurt(event.getEntity(), event.getSource(), event.getOriginalDamage(), event.getNewDamage());
    }

    @SubscribeEvent
    public void onEntityTravelToDimension(EntityTravelToDimensionEvent event) {
        ServerProxy.onEntityTravelToDimension(event.getEntity(), event.getEntity().getServer().getLevel(event.getDimension()));
    }

    @SubscribeEvent
    public void onEntityTeleport(EntityTeleportEvent event) {
        ServerProxy.onEntityTeleport(event.getEntity(), event.getPrev(), event.getTarget());
    }

    @SubscribeEvent
    public void onProjectileImpact(ProjectileImpactEvent event) {
        if (ServerProxy.onProjectileImpactEvent(event.getProjectile(), event.getRayTraceResult())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onEntityMount(EntityMountEvent event) {
        if (ServerProxy.onEntityMount(event.getEntityBeingMounted(), event.getEntityMounting(), event.isDismounting())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onEntityJoinLevel(EntityJoinLevelEvent event) {
        ServerProxy.onEntityJoinWorldEvent(event.getEntity(), event.getLevel());
    }

    @SubscribeEvent
    public void onEntityLeaveLevel(EntityLeaveLevelEvent event) {
        ServerProxy.onEntityLeaveWorld(event.getEntity(), event.getLevel());
    }

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        ServerProxy.onLivingDie(event.getEntity(), event.getSource());
    }

    @SubscribeEvent
    public void onExplosionDetonate(ExplosionEvent.Detonate event) {
        ServerProxy.onExplosion(event.getLevel(), event.getExplosion());
    }

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        ServerProxy.onBlockBreak((ServerLevel) event.getLevel(), event.getPos(), event.getState(), event.getPlayer());
    }

    @SubscribeEvent
    public void onItemExpire(ItemExpireEvent event) {
        ServerProxy.onItemDespawnEvent(event.getEntity());
    }

    @SubscribeEvent
    public void onLivingTick(EntityTickEvent.Pre event) {
        if (event.getEntity() instanceof LivingEntity living) {
            ServerProxy.onLivingUpdate(living);
        }
    }

    @SubscribeEvent
    public void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        InteractionResult result = ServerProxy.onInteractWithEntity(event.getEntity(), event.getHand(), event.getLevel(), event.getTarget(), event.getItemStack());
        if (result.consumesAction()) {
            event.setCanceled(true);
            event.setCancellationResult(result);
        }
    }

    @SubscribeEvent
    public void onVillagerTrades(VillagerTradesEvent event) {
        ServerProxy.onVillagerTrades(event.getType(), event.getTrades());
    }

    @SubscribeEvent
    public void onItemTooltip(ItemTooltipEvent event) {
        ServerProxy.onItemTooltip(event.getItemStack(), event.getContext(), event.getFlags(), event.getToolTip());
    }
}