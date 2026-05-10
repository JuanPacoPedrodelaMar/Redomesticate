package com.evandev.redomesticate.mixin;

import com.evandev.redomesticate.Constants;
import com.evandev.redomesticate.api.ICommandableMob;
import com.evandev.redomesticate.api.ITameableEntity;
import com.evandev.redomesticate.config.ModConfig;
import com.evandev.redomesticate.content.entity.ai.*;
import com.evandev.redomesticate.util.TameableUtils;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import org.jetbrains.annotations.Nullable;
import java.util.Optional;
import java.util.UUID;

@Mixin(Rabbit.class)
public abstract class RabbitMixin extends Animal implements ITameableEntity, ICommandableMob {

    @Unique
    private static final EntityDataAccessor<Optional<UUID>> OWNER_UUID = SynchedEntityData.defineId(Rabbit.class, EntityDataSerializers.OPTIONAL_UUID);
    @Unique
    private static final EntityDataAccessor<Integer> redomesticate$COMMAND = SynchedEntityData.defineId(Rabbit.class, EntityDataSerializers.INT);
    @Unique
    private static final EntityDataAccessor<Boolean> redomesticate$TAMED = SynchedEntityData.defineId(Rabbit.class, EntityDataSerializers.BOOLEAN);

    @Shadow
    @Final
    private static EntityDataAccessor<Integer> DATA_TYPE_ID;

    protected RabbitMixin(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    @Shadow
    public abstract Rabbit.Variant getVariant();

    @Inject(
            at = {@At("TAIL")},
            method = {"registerGoals()V"}
    )
    private void registerGoals(CallbackInfo ci) {
        this.goalSelector.addGoal(1, new Sit2Goal(this));
        this.goalSelector.addGoal(2, new FollowOwner2Goal(this, 2.0D, 10.0F, 3.0F, false));
        this.targetSelector.addGoal(2, new OwnerHurtTarget2Goal(this));
        this.targetSelector.addGoal(3, new OwnerHurtByTarget2Goal(this));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.0D, Ingredient.of(Items.HAY_BLOCK), false));
        if (redomesticate$isTame()) {
            redomesticate$removeUntamedGoals();
        }
    }

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
        if (b) {
            redomesticate$removeUntamedGoals();
        }
    }

    @Nullable
    public UUID redomesticate$getTameOwnerUUID() {
        return ModConfig.get().tameableRabbit ? this.entityData.get(OWNER_UUID).orElse(null) : null;
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
        return this.getVariant() == Rabbit.Variant.EVIL && (!this.redomesticate$isTame() || !TameableUtils.hasSameOwnerAs(this, target));
    }

    @Unique
    public void redomesticate$removeUntamedGoals() {
        try {
            this.goalSelector.getAvailableGoals().stream().filter((wrapped) -> {
                return wrapped.getGoal() instanceof AvoidEntityGoal;
            }).filter(WrappedGoal::isRunning).forEach(WrappedGoal::stop);
            this.goalSelector.getAvailableGoals().removeIf((wrapped) -> {
                return wrapped.getGoal() instanceof AvoidEntityGoal;
            });
            this.targetSelector.getAvailableGoals().removeIf((wrapped) -> {
                return wrapped.getGoal() instanceof NearestAttackableTargetGoal;
            });
        } catch (Exception e) {
            Constants.LOG.warn("Encountered error modifying Rabbit AI");
        }
    }

    @Inject(
            at = {@At("HEAD")},
            method = {"setVariant(Lnet/minecraft/world/entity/animal/Rabbit$Variant;)V"},
            cancellable = true
    )
    private void setRabbitType(Rabbit.Variant type, CallbackInfo ci) {
        ci.cancel();
        if (type == Rabbit.Variant.EVIL) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(30.0D);
            this.getAttribute(Attributes.ARMOR).setBaseValue(8.0D);
            this.heal(22.0F);
            this.goalSelector.addGoal(4, new RabbitMeleeGoal(this));
            this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers());
            if (!this.redomesticate$isTame()) {
                this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
                this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Wolf.class, true));
            } else {
                this.targetSelector.addGoal(2, new OwnerHurtTarget2Goal(this));
                this.targetSelector.addGoal(3, new OwnerHurtByTarget2Goal(this));
                redomesticate$removeUntamedGoals();
            }
            if (!this.hasCustomName()) {
                this.setCustomName(Component.translatable(Util.makeDescriptionId("entity", ResourceLocation.withDefaultNamespace("killer_bunny"))));
            }
        }
        this.entityData.set(DATA_TYPE_ID, type.id());
    }

    @Override
    public void redomesticate$sendCommandMessage(Player owner, int command, Component name) {
        owner.displayClientMessage(Component.translatable("message.redomesticate.command_" + command, name), true);
    }
}
