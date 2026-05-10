package com.evandev.redomesticate.util;

import com.evandev.redomesticate.Constants;
import com.evandev.redomesticate.config.ModConfig;
import com.mojang.authlib.GameProfile;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.scores.PlayerTeam;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class FriendlyFireCommon {
    public static final TagKey<Item> BYPASS_PET = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "bypass_pet"));
    public static final TagKey<Item> BYPASS_ALL = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "bypass_all_protection"));
    public static final TagKey<EntityType<?>> GENERAL_PROTECTION = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "general_protection"));
    public static final TagKey<EntityType<?>> PLAYER_PROTECTION = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "player_protection"));
    public static final TagKey<EntityType<?>> BYPASSED_PROTECTION = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "bypassed_entity_types"));

    public static void init() {
        Constants.LOG.debug("Protect children = {}", ModConfig.get().protectChildren);
        Constants.LOG.debug("Protect pets from owner = {}", ModConfig.get().protectPetsFromOwner);
        Constants.LOG.debug("Protect pets from pets = {}", ModConfig.get().protectPetsFromPets);
        Constants.LOG.debug("Reflect damage = {}", ModConfig.get().reflectDamage);
    }

    public static boolean preventAttack(Entity target, DamageSource source, float amount) {

        final Entity attacker = source.getEntity();
        final boolean preventDamage = isProtected(target, attacker, amount);

        if (preventDamage && attacker instanceof ServerPlayer player && ModConfig.get().displayHitWarning) {
            player.displayClientMessage(Component.literal("").withStyle(ChatFormatting.AQUA).append(Component.translatable("notif.friendlyfire.protected", target.getName()).withStyle(ChatFormatting.WHITE)), true);
        }

        return preventDamage;
    }

    public static boolean isProtected(Entity victim, Entity attacker, float amount) {

        if (ModConfig.get().noProtectionEntity.contains(victim.getType())) {
            return false;
        }

        if (attacker == null || attacker.isCrouching()) {
            return false;
        }

        final ItemStack heldItem = attacker instanceof LivingEntity attackerLiving ? attackerLiving.getMainHandItem() : ItemStack.EMPTY;

        if (ModConfig.get().canHurtAllItem.contains(heldItem.getItem())) {
            return false;
        }

        if (ModConfig.get().otherShouldProtectEntity.contains(victim.getType())) {
            return true;
        }

        if (attacker instanceof Player player && ModConfig.get().playerCantHurtEntity.contains(victim.getType())) {
            return true;
        }

        final UUID ownerId = getOwner(victim);

        if (ownerId != null && !ModConfig.get().canHurtPetItem.contains(heldItem.getItem())) {
            if (ModConfig.get().protectPetsFromOwner && ownerId.equals(attacker.getUUID())) {

                if (ModConfig.get().reflectDamage) {
                    attacker.hurt(attacker.level().damageSources().generic(), amount);
                }

                return true;
            } else if (ModConfig.get().protectPetsFromPets && ownerId.equals(getOwner(attacker))) {
                return true;
            }
        }

        if (ModConfig.get().protectTeamMembers && isOnProtectedTeam(attacker, victim)) {
            return true;
        }

        if (ModConfig.get().protectChildren && attacker instanceof Player && !(victim instanceof Enemy) && victim instanceof AgeableMob agable && agable.isBaby() && !attacker.isCrouching()) {
            return true;
        }

        return false;
    }

    public static boolean isOnProtectedTeam(Entity attacker, Entity victim) {
        final PlayerTeam attackerTeam = getEffectiveTeam(attacker);
        final PlayerTeam victimTeam = getEffectiveTeam(victim);
        return attackerTeam != null && victimTeam != null && (attackerTeam.isAlliedTo(victimTeam) && victimTeam.isAlliedTo(attackerTeam)) && (!ModConfig.get().respectTeamRules || !victimTeam.isAllowFriendlyFire());
    }

    @Nullable
    public static PlayerTeam getEffectiveTeam(Entity entity) {
        final PlayerTeam directTeam = entity.getTeam();

        if (directTeam == null && entity instanceof OwnableEntity ownable && ownable.getOwnerUUID() != null) {
            if (ownable.getOwner() != null) {
                return ownable.getOwner().getTeam();
            }

            if (entity.level() instanceof ServerLevel server) {
                final GameProfile fetchResult = server.getServer().getProfileCache().get(ownable.getOwnerUUID()).orElse(null);
                if (fetchResult != null) {
                    return entity.level().getScoreboard().getPlayersTeam(fetchResult.getName());
                }
            }
        }

        return directTeam;
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