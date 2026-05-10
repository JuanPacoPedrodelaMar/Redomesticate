package com.evandev.redomesticate.mixin;

import com.evandev.redomesticate.api.ICommandableMob;
import com.evandev.redomesticate.api.IFrog;
import com.evandev.redomesticate.api.ITameableEntity;
import com.evandev.redomesticate.config.ModConfig;
import com.evandev.redomesticate.registry.ModTags;
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
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.frog.Frog;
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

import org.jetbrains.annotations.Nullable;
import java.util.Optional;
import java.util.UUID;

@Mixin(Frog.class)
public abstract class FrogMixin extends Animal implements ITameableEntity, ICommandableMob, IFrog {

    @Unique
    private static final EntityDataAccessor<Optional<UUID>> OWNER_UUID = SynchedEntityData.defineId(Frog.class, EntityDataSerializers.OPTIONAL_UUID);

    @Unique
    private static final EntityDataAccessor<Integer> redomesticate$COMMAND = SynchedEntityData.defineId(Frog.class, EntityDataSerializers.INT);

    @Unique
    private static final EntityDataAccessor<Boolean> redomesticate$TAMED = SynchedEntityData.defineId(Frog.class, EntityDataSerializers.BOOLEAN);

    @Unique
    private boolean redomesticate$hasInitialDamage = false;

    protected FrogMixin(EntityType<? extends Animal> type, Level lvl) {
        super(type, lvl);
    }

    @Shadow
    public abstract @NotNull Brain<Frog> getBrain();

    @Inject(
            at = {@At("TAIL")},
            method = {"defineSynchedData(Lnet/minecraft/network/syncher/SynchedEntityData$Builder;)V"}
    )
    private void registerData(SynchedEntityData.Builder builder, CallbackInfo ci) {
        builder.define(OWNER_UUID, Optional.empty());
        builder.define(redomesticate$COMMAND, 0);
        builder.define(redomesticate$TAMED, false);
    }

    @Inject(
            method = {"tick()V"},
            at = {@At("TAIL")}
    )
    private void tick(CallbackInfo ci) {
        if (!redomesticate$hasInitialDamage && this.redomesticate$isTame()) {
            redomesticate$hasInitialDamage = true;
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(3.0D);
        }
    }

    @Inject(
            at = {@At("TAIL")},
            method = {"addAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V"}
    )
    private void writeAdditional(CompoundTag compoundNBT, CallbackInfo ci) {
        compoundNBT.putInt("RedomesticateCommand", this.redomesticate$getCommand());
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
        this.redomesticate$setCommand(compoundNBT.getInt("RedomesticateCommand"));
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

    @Unique
    private void redomesticate$spawnTamingParticles(boolean smoke) {
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

    @Nullable
    public UUID redomesticate$getTameOwnerUUID() {
        return ModConfig.get().tameableFrog ? this.entityData.get(OWNER_UUID).orElse(null) : null;
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
        owner.displayClientMessage(Component.translatable("message.redomesticate.command_" + command, name), true);
    }

    @Unique
    private boolean redomesticate$isTamingItem(ItemStack stack) {
        return stack.is(ModTags.TAME_FROGS_WITH);
    }

    @Override
    public boolean redomesticate$onFrogInteract(Player player, InteractionHand hand) {
        if (ModConfig.get().tameableFrog) {
            ItemStack itemStack = player.getItemInHand(hand);
            if (!this.redomesticate$isTame() && this.redomesticate$isTamingItem(itemStack)) {
                this.usePlayerItem(player, hand, itemStack);
                this.heal(2);
                this.playSound(SoundEvents.FROG_EAT, this.getSoundVolume(), this.getVoicePitch());
                if (!this.level().isClientSide) {
                    if (this.getRandom().nextInt(4) == 0) {
                        this.redomesticate$spawnTamingParticles(true);
                    } else {
                        this.redomesticate$spawnTamingParticles(false);
                        this.redomesticate$setTame(true);
                        this.redomesticate$setTameOwnerUUID(player.getUUID());
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.TAME_ANIMAL.trigger((ServerPlayer) player, this);
                        }
                    }
                }
                return true;
            } else if (redomesticate$isTame() && itemStack.getItem() != Items.WATER_BUCKET) {
                if ((this.redomesticate$isTamingItem(itemStack) || itemStack.is(Items.SLIME_BALL)) && this.getHealth() < this.getMaxHealth()) {
                    this.heal(2);
                    this.playSound(SoundEvents.FROG_EAT, this.getSoundVolume(), this.getVoicePitch());
                    return true;
                } else if (ModConfig.get().trinaryCommandSystem) {
                    player.swing(hand, true);
                    this.playerSetCommand(player, this);
                    return false;
                }
            }
        }
        return false;
    }

    @Inject(
            at = {@At("TAIL")},
            method = {"customServerAiStep()V"}
    )
    private void customServerAiStep(CallbackInfo ci) {
        if (this.redomesticate$isTame() && this.redomesticate$getTameOwner() != null) {
            if (this.redomesticate$getTameOwner().getLastHurtMob() != null && this.redomesticate$getTameOwner().getLastHurtMob().isAlive() && !TameableUtils.hasSameOwnerAs(this, this.redomesticate$getTameOwner().getLastHurtMob())) {
                this.setTarget(this.redomesticate$getTameOwner().getLastHurtMob());
                this.getBrain().setMemory(MemoryModuleType.ATTACK_TARGET, this.redomesticate$getTameOwner().getLastHurtMob());
            }
            if (this.redomesticate$getTameOwner().getLastHurtByMob() != null && this.redomesticate$getTameOwner().getLastHurtByMob().isAlive() && !TameableUtils.hasSameOwnerAs(this, this.redomesticate$getTameOwner().getLastHurtByMob())) {
                this.setTarget(this.redomesticate$getTameOwner().getLastHurtByMob());
                this.getBrain().setMemory(MemoryModuleType.ATTACK_TARGET, this.redomesticate$getTameOwner().getLastHurtByMob());
            }
        }
    }

}
