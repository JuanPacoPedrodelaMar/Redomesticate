package com.evandev.redomesticate.api;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface ITameableEntity extends OwnableEntity {
    boolean redomesticate$isTame();

    void redomesticate$setTame(boolean value);

    @Nullable
    UUID redomesticate$getTameOwnerUUID();

    void redomesticate$setTameOwnerUUID(@Nullable UUID uuid);

    @Nullable
    LivingEntity redomesticate$getTameOwner();

    boolean redomesticate$isStayingStill();

    boolean redomesticate$isFollowingOwner();

    boolean redomesticate$isValidAttackTarget(LivingEntity target);

    @Nullable
    default UUID getOwnerUUID() {
        return redomesticate$getTameOwnerUUID();
    }

}
