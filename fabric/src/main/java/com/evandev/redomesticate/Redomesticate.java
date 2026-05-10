package com.evandev.redomesticate;

import com.evandev.redomesticate.network.FabricNetworking;
import com.evandev.redomesticate.registry.FabricModLoot;
import com.evandev.redomesticate.registry.ModVillagers;
import com.evandev.redomesticate.server.ServerProxy;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.world.entity.npc.VillagerTrades;

import java.util.List;

public class Redomesticate implements ModInitializer {

    @Override
    public void onInitialize() {
        CommonClass.init();
        FabricNetworking.initMain();
        FabricModLoot.init();

        ServerLifecycleEvents.SERVER_STARTING.register(ServerProxy::serverStart);
        ServerTickEvents.END_WORLD_TICK.register(ServerProxy::onServerTick);
        ServerEntityEvents.ENTITY_LOAD.register(ServerProxy::onEntityJoinWorldEvent);
        ServerEntityEvents.ENTITY_UNLOAD.register(ServerProxy::onEntityLeaveWorld);
        ServerEntityWorldChangeEvents.AFTER_ENTITY_CHANGE_WORLD.register((originalEntity, newEntity, origin, destination) -> {
            ServerProxy.onEntityTravelToDimension(newEntity, destination);
        });

        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
            ServerProxy.onBlockBreak(world, pos, state, player);
        });

        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            return ServerProxy.onInteractWithEntity(player, hand, world, entity, player.getItemInHand(hand));
        });

        Int2ObjectMap<List<VillagerTrades.ItemListing>> tempMap = new Int2ObjectOpenHashMap<>();
        ServerProxy.onVillagerTrades(ModVillagers.ANIMAL_TAMER.get(), tempMap);

        for (int level = 1; level <= 5; level++) {
            List<VillagerTrades.ItemListing> tradesForLevel = tempMap.get(level);
            if (tradesForLevel != null) {
                TradeOfferHelper.registerVillagerOffers(ModVillagers.ANIMAL_TAMER.get(), level, factories -> {
                    factories.addAll(tradesForLevel);
                });
            }
        }

        ItemTooltipCallback.EVENT.register(ServerProxy::onItemTooltip);
    }
}