package com.evandev.redomesticate.network;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.concurrent.CompletableFuture;

/**
 * Platform-agnostic context for handling network messages.
 */
public interface IMessageContext {

    /**
     * Enqueues work to run on the main game thread.
     */
    CompletableFuture<Void> enqueueWork(Runnable runnable);

    /**
     * Disconnects the player with the specified reason.
     */
    void disconnect(Component reason);

    Player getPlayer();
}