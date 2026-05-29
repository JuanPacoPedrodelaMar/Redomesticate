package com.evandev.redomesticate.util;

import com.evandev.redomesticate.Constants;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.TicketType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;

import java.util.UUID;

public class ChunkLoader {
    private static final TicketType<UUID> LANTERN_TICKET = TicketType.create(Constants.MOD_ID + "_lantern", UUID::compareTo, 100);

    public static void forceLoadChunk(Level level, ChunkPos chunkPos, UUID petId) {
        ServerLevel serverLevel = (ServerLevel) level;
        var ticketManager = serverLevel.getChunkSource().chunkMap.getDistanceManager();
        ticketManager.addRegionTicket(LANTERN_TICKET, chunkPos, 31, petId);
    }

    public static void unloadChunk(Level level, ChunkPos chunkPos, UUID petId) {
        ServerLevel serverLevel = (ServerLevel) level;
        var ticketManager = serverLevel.getChunkSource().chunkMap.getDistanceManager();
        ticketManager.removeRegionTicket(LANTERN_TICKET, chunkPos, 31, petId);
    }
}