package com.evandev.redomesticate;

import com.evandev.redomesticate.client.ClientConfigSetup;
import com.evandev.redomesticate.platform.NeoForgeRegistrationProvider;
import com.evandev.redomesticate.registry.NeoForgeModLootModifiers;
import com.evandev.redomesticate.server.ServerProxy;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;

@Mod(Constants.MOD_ID)
public class Redomesticate {
    public Redomesticate(IEventBus modEventBus, ModContainer modContainer) {
        CommonClass.init();
        NeoForgeRegistrationProvider.registerAll(modEventBus);
        NeoForgeModLootModifiers.register(modEventBus);
        modEventBus.addListener(this::commonSetup);

        if (FMLEnvironment.dist.isClient()) {
            ClientConfigSetup.register(modContainer);
        }
    }

    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        if (ServerProxy.onLivingDrops(event.getEntity())) {
            event.setCanceled(true);
        }
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }
}