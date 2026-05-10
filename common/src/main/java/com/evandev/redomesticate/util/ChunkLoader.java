package com.evandev.redomesticate.util;

import com.evandev.redomesticate.Constants;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.TicketType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;

import java.util.Comparator;

public class ChunkLoader {
    public static void forceLoadChunk(Level level, ChunkPos chunkPos) {
        ServerLevel serverLevel = (ServerLevel) level;
        TicketType<ChunkPos> ticketType = TicketType.create(Constants.MOD_ID, Comparator.comparingLong(ChunkPos::toLong));

        var ticketManager = serverLevel.getChunkSource().chunkMap.getDistanceManager();
        ticketManager.addRegionTicket(ticketType, chunkPos, 0, chunkPos);
    }

    public static void unloadChunk(Level level, ChunkPos chunkPos) {
        ServerLevel serverLevel = (ServerLevel) level;
        TicketType<ChunkPos> ticketType = TicketType.create(Constants.MOD_ID, Comparator.comparingLong(ChunkPos::toLong));

        var ticketManager = serverLevel.getChunkSource().chunkMap.getDistanceManager();
        ticketManager.removeRegionTicket(ticketType, chunkPos, 0, chunkPos);
    }
}