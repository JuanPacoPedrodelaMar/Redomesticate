package com.evandev.redomesticate.mixin;

import com.evandev.redomesticate.api.ICommandableMob;
import com.evandev.redomesticate.api.ITameableEntity;
import com.evandev.redomesticate.config.ModConfig;
import com.evandev.redomesticate.util.TameableUtils;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

@Mixin(Axolotl.class)
public abstract class AxolotlMixin extends Animal implements ITameableEntity, ICommandableMob {

    @Unique
    private static final EntityDataAccessor<Optional<UUID>> OWNER_UUID = SynchedEntityData.defineId(Axolotl.class, EntityDataSerializers.OPTIONAL_UUID);
    @Unique
    private static final EntityDataAccessor<Integer> redomesticate$COMMAND = SynchedEntityData.defineId(Axolotl.class, EntityDataSerializers.INT);
    @Unique
    private static final EntityDataAccessor<Boolean> redomesticate$TAMED = SynchedEntityData.defineId(Axolotl.class, EntityDataSerializers.BOOLEAN);

    protected AxolotlMixin(EntityType<? extends Animal> type, Level lvl) {
        super(type, lvl);
    }

    @Shadow
    public abstract void readAdditionalSaveData(@NotNull CompoundTag p_149145_);

    @Inject(
            at = {@At("TAIL")},
            method = {"Lnet/minecraft/world/entity/animal/axolotl/Axolotl;defineSynchedData()V"}
    )
    private void registerData(CallbackInfo ci) {
        this.entityData.define(OWNER_UUID, Optional.empty());
        this.entityData.define(redomesticate$COMMAND, 0);
        this.entityData.define(redomesticate$TAMED, false);
    }

    @Inject(
            at = {@At("TAIL")},
            method = {"addAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V"}
    )
    private void writeAdditional(CompoundTag compoundNBT, CallbackInfo ci) {
        compoundNBT.putInt("DICommand", this.redomesticate$getCommand());
        compoundNBT.putBoolean("Tamed", this.redomesticate$isTame());
        if (this.redomesticate$getTameOwnerUUID() != null) {
            compoundNBT.putUUID("Owner", this.redomesticate$getTameOwnerUUID());
        }
    }

    @Inject(
            at = {@At("TAIL")},
            method = {"readAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V"}
    )
    private void readAdditional(CompoundTag compoundNBT, CallbackInfo ci) {
        this.redomesticate$setCommand(compoundNBT.getInt("DICommand"));
        this.redomesticate$setTame(compoundNBT.getBoolean("Tamed"));
        UUID uuid;
        if (compoundNBT.hasUUID("Owner")) {
            uuid = compoundNBT.getUUID("Owner");
        } else {
            String s = compoundNBT.getString("Owner");
            uuid = OldUsersConverter.convertMobOwnerIfNecessary(this.getServer(), s);
        }

        if (uuid != null) {
            try {
                this.redomesticate$setTameOwnerUUID(uuid);
                this.redomesticate$setTame(true);
            } catch (Throwable throwable) {
                this.redomesticate$setTame(false);
            }
        }
    }

    @Inject(
            at = {@At("TAIL")},
            method = {"saveToBucketTag(Lnet/minecraft/world/item/ItemStack;)V"}
    )
    private void writeAdditionalBucket(ItemStack stack, CallbackInfo ci) {
        CompoundTag compoundNBT = stack.getOrCreateTag();
        this.addAdditionalSaveData(compoundNBT);
        compoundNBT.putInt("DICommand", this.redomesticate$getCommand());
        compoundNBT.putBoolean("Tamed", this.redomesticate$isTame());
        if (this.redomesticate$getTameOwnerUUID() != null) {
            compoundNBT.putUUID("Owner", this.redomesticate$getTameOwnerUUID());
        }
    }

    @Inject(
            at = {@At("TAIL")},
            method = {"loadFromBucketTag(Lnet/minecraft/nbt/CompoundTag;)V"}
    )
    private void readAdditionalBucket(CompoundTag compoundNBT, CallbackInfo ci) {
        this.readAdditionalSaveData(compoundNBT);
        this.redomesticate$setCommand(compoundNBT.getInt("DICommand"));
        this.redomesticate$setTame(compoundNBT.getBoolean("Tamed"));
        UUID uuid;
        if (compoundNBT.hasUUID("Owner")) {
            uuid = compoundNBT.getUUID("Owner");
        } else {
            String s = compoundNBT.getString("Owner");
            uuid = OldUsersConverter.convertMobOwnerIfNecessary(this.getServer(), s);
        }
        if (uuid != null) {
            try {
                this.redomesticate$setTameOwnerUUID(uuid);
                this.redomesticate$setTame(true);
            } catch (Throwable throwable) {
                this.redomesticate$setTame(false);
            }
        }
    }

    @Inject(
            method = {"mobInteract(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResult;"},
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void onInteract(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        if (ModConfig.get().tameableAxolotl) {
            ItemStack itemStack = player.getItemInHand(hand);
            if (!this.redomesticate$isTame() && this.redomesticate$isFish(itemStack)) {
                this.usePlayerItem(player, hand, itemStack);
                this.heal(2);
                this.playSound(SoundEvents.CAT_EAT, this.getSoundVolume(), this.getVoicePitch());
                if (!this.level().isClientSide) {
                    if (this.getRandom().nextInt(4) == 0) {
                        this.spawnTamingParticles(true);
                    } else {
                        this.spawnTamingParticles(false);
                        this.redomesticate$setTame(true);
                        this.redomesticate$setTameOwnerUUID(player.getUUID());
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.TAME_ANIMAL.trigger((ServerPlayer) player, this);
                        }
                    }
                }
                cir.setReturnValue(InteractionResult.SUCCESS);
            } else if (redomesticate$isTame() && itemStack.getItem() != Items.WATER_BUCKET) {
                if (this.redomesticate$isFish(itemStack) && this.getHealth() < this.getMaxHealth()) {
                    this.heal(2);
                    this.playSound(SoundEvents.CAT_EAT, this.getSoundVolume(), this.getVoicePitch());
                    cir.setReturnValue(InteractionResult.SUCCESS);
                } else if (super.mobInteract(player, hand) == InteractionResult.PASS && ModConfig.get().trinaryCommandSystem) {
                    player.swing(hand, true);
                    cir.setReturnValue(this.playerSetCommand(player, this));
                }
            }
        }
    }

    @Inject(
            at = {@At("TAIL")},
            method = {"customServerAiStep()V"}
    )
    private void customServerAiStep(CallbackInfo ci) {
        if (this.redomesticate$isTame() && this.redomesticate$getTameOwner() != null) {
            if (this.redomesticate$getTameOwner().getLastHurtMob() != null && this.redomesticate$getTameOwner().getLastHurtMob().isAlive() && !TameableUtils.hasSameOwnerAs(this, this.redomesticate$getTameOwner().getLastHurtMob())) {
                this.setTarget(this.redomesticate$getTameOwner().getLastHurtMob());
            }
            if (this.redomesticate$getTameOwner().getLastHurtByMob() != null && this.redomesticate$getTameOwner().getLastHurtByMob().isAlive() && !TameableUtils.hasSameOwnerAs(this, this.redomesticate$getTameOwner().getLastHurtByMob())) {
                this.setTarget(this.redomesticate$getTameOwner().getLastHurtByMob());
            }
        }
    }

    @Inject(
            at = {@At("HEAD")},
            method = {"removeWhenFarAway(D)Z"},
            cancellable = true)
    private void removeWhenFarAway(double dist, CallbackInfoReturnable<Boolean> cir) {
        if (this.redomesticate$isTame()) {
            cir.setReturnValue(false);
        }
    }

    @Unique
    private void spawnTamingParticles(boolean smoke) {
        if (!level().isClientSide) {
            ParticleOptions particleoptions = smoke ? ParticleTypes.SMOKE : ParticleTypes.HEART;
            for (int i = 0; i < 7; ++i) {
                double d0 = this.getRandom().nextGaussian() * 0.02D;
                double d1 = this.getRandom().nextGaussian() * 0.02D;
                double d2 = this.getRandom().nextGaussian() * 0.02D;
                ((ServerLevel) this.level()).sendParticles(particleoptions, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), 3, d0, d1, d2, 0.03F);
            }
        }
    }

    public int redomesticate$getCommand() {
        return this.entityData.get(redomesticate$COMMAND);
    }

    public void redomesticate$setCommand(int i) {
        this.entityData.set(redomesticate$COMMAND, i);
    }

    public boolean redomesticate$isTame() {
        return this.entityData.get(redomesticate$TAMED);
    }

    public void redomesticate$setTame(boolean b) {
        this.entityData.set(redomesticate$TAMED, b);
    }

    @Unique
    private boolean redomesticate$isFish(ItemStack stack) {
        return isFood(stack) || stack.getItem() == Items.TROPICAL_FISH;
    }

    @Nullable
    public UUID redomesticate$getTameOwnerUUID() {
        return ModConfig.get().tameableAxolotl ? this.entityData.get(OWNER_UUID).orElse((UUID) null) : null;
    }

    public void redomesticate$setTameOwnerUUID(@Nullable UUID uuid) {
        this.entityData.set(OWNER_UUID, Optional.ofNullable(uuid));
    }

    @Nullable
    public LivingEntity redomesticate$getTameOwner() {
        try {
            UUID uuid = this.redomesticate$getTameOwnerUUID();
            return uuid == null ? null : this.level().getPlayerByUUID(uuid);
        } catch (IllegalArgumentException illegalargumentexception) {
            return null;
        }
    }

    public boolean redomesticate$isFollowingOwner() {
        return this.redomesticate$getCommand() == 2 && ModConfig.get().trinaryCommandSystem;
    }

    public boolean redomesticate$isStayingStill() {
        return this.redomesticate$getCommand() == 1 && ModConfig.get().trinaryCommandSystem;
    }

    public boolean redomesticate$isValidAttackTarget(LivingEntity target) {
        if (this.isAlliedTo(target)) {
            return false;
        }
        if (this.redomesticate$getTameOwner() != null && this.redomesticate$getTameOwner().getLastHurtMob() != null && this.redomesticate$getTameOwner().getLastHurtMob().equals(target)) {
            return !TameableUtils.hasSameOwnerAs(this, target) && !this.isAlliedTo(target);
        }
        if (this.redomesticate$getTameOwner() != null && this.redomesticate$getTameOwner().getLastHurtByMob() != null && this.redomesticate$getTameOwner().getLastHurtByMob().equals(target)) {
            return !TameableUtils.hasSameOwnerAs(this, target) && !this.isAlliedTo(target);
        }
        return false;
    }

    @Override
    public void redomesticate$sendCommandMessage(Player owner, int command, Component name) {
        owner.displayClientMessage(Component.translatable("message.domesticationinnovation.command_" + command, name), true);
    }
}
