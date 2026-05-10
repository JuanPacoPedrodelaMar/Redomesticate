package com.evandev.redomesticate.util;

import com.evandev.redomesticate.config.ModConfig;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class FriendlyFireCommon {

    public static void init() {
    }

    public static boolean preventAttack(Entity target, DamageSource source, float amount) {
        return isProtected(target, source.getEntity());
    }

    public static boolean isProtected(Entity victim, Entity attacker) {
        if (!ModConfig.get().swingThroughPets) {
            return false;
        }

        if (attacker == null) {
            return false;
        }

        final UUID ownerId = getOwner(victim);

        if (ownerId != null && ownerId.equals(attacker.getUUID())) {
            return true;
        }

        return false;
    }

    @Nullable
    public static UUID getOwner(Entity entity) {
        if (entity instanceof OwnableEntity ownable) {
            return ownable.getOwnerUUID();
        }

        if (entity instanceof AbstractHorse horse) {
            return horse.getOwnerUUID();
        }

        return null;
    }
}