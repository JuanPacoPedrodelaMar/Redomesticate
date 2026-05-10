package com.evandev.redomesticate.mixin;

import com.evandev.redomesticate.api.ICommandableMob;
import com.evandev.redomesticate.api.ITameableEntity;
import com.evandev.redomesticate.config.ModConfig;
import com.evandev.redomesticate.server.entity.ai.FollowOwner2Goal;
import com.evandev.redomesticate.server.entity.ai.OwnerHurtTarget2Goal;
import com.evandev.redomesticate.server.entity.ai.Sit2Goal;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
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

@Mixin(Fox.class)
public abstract class FoxMixin extends Animal implements ITameableEntity, ICommandableMob {

    @Unique
    private static final EntityDataAccessor<Integer> redomesticate$COMMAND = SynchedEntityData.defineId(Fox.class, EntityDataSerializers.INT);

    @Shadow
    @Final
    private static EntityDataAccessor<Optional<UUID>> DATA_TRUSTED_ID_0;

    @Shadow
    @Final
    private static EntityDataAccessor<Optional<UUID>> DATA_TRUSTED_ID_1;

    protected FoxMixin(EntityType<? extends Animal> foxType, Level level) {
        super(foxType, level);
    }

    @Shadow
    abstract void addTrustedUUID(UUID uuid);

    @Shadow
    public abstract void setSitting(boolean p_28611_);

    @Shadow
    abstract void setSleeping(boolean p_28627_);

    @Inject(
            at = {@At("HEAD")},
            method = {"registerGoals()V"}
    )
    private void registerGoals(CallbackInfo ci) {
        this.goalSelector.addGoal(1, new Sit2Goal(this));
        this.goalSelector.addGoal(2, new FollowOwner2Goal(this, 1.0D, 10.0F, 3.0F, false));
        this.targetSelector.addGoal(1, new OwnerHurtTarget2Goal(this));
    }

    @Inject(
            at = {@At("TAIL")},
            method = {"defineSynchedData(Lnet/minecraft/network/syncher/SynchedEntityData$Builder;)V"}
    )
    private void registerData(SynchedEntityData.Builder builder, CallbackInfo ci) {
        builder.define(redomesticate$COMMAND, 0);
    }

    @Inject(
            at = {@At("TAIL")},
            method = {"addAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V"}
    )
    private void writeAdditional(CompoundTag compoundNBT, CallbackInfo ci) {
        compoundNBT.putInt("RedomesticateCommand", this.redomesticate$getCommand());
    }

    @Inject(
            at = {@At("TAIL")},
            method = {"readAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V"}
    )
    private void readAdditional(CompoundTag compoundNBT, CallbackInfo ci) {
        this.redomesticate$setCommand(compoundNBT.getInt("RedomesticateCommand"));
    }

    public int redomesticate$getCommand() {
        return this.entityData.get(redomesticate$COMMAND);
    }

    public void redomesticate$setCommand(int i) {
        this.entityData.set(redomesticate$COMMAND, i);
    }

    public boolean redomesticate$isTame() {
        return (this.entityData.get(DATA_TRUSTED_ID_0).isPresent() || this.entityData.get(DATA_TRUSTED_ID_1).isPresent()) && ModConfig.get().tameableFox;
    }

    public void redomesticate$setTame(boolean value) {

    }

    @WrapOperation(
            method = "aiStep()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;finishUsingItem(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;)Lnet/minecraft/world/item/ItemStack;"
            )
    )
    private ItemStack wrapFinishUsingItem(ItemStack instance, Level level, LivingEntity livingEntity, Operation<ItemStack> original) {
        FoodProperties food = instance.get(DataComponents.FOOD);
        if (!instance.isEmpty() && food != null) {
            this.heal(food.nutrition() * 2);
        }

        return original.call(instance, level, livingEntity);
    }

    @Inject(
            at = {@At("TAIL")},
            method = {"aiStep()V"}
    )
    private void aiStep_2(CallbackInfo ci) {
        if (this.redomesticate$isFollowingOwner()) {
            this.setSleeping(false);
            this.setSitting(false);
        }
    }

    @Nullable
    public UUID redomesticate$getTameOwnerUUID() {
        if (this.entityData.get(DATA_TRUSTED_ID_0).isPresent()) {
            return this.entityData.get(DATA_TRUSTED_ID_0).get();
        } else {
            return this.entityData.get(DATA_TRUSTED_ID_1).orElse(null);
        }
    }

    public void redomesticate$setTameOwnerUUID(@Nullable UUID uuid) {
        addTrustedUUID(uuid);
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
        return true;
    }

    @Override
    public void redomesticate$sendCommandMessage(Player owner, int command, Component name) {
        owner.displayClientMessage(Component.translatable("message.redomesticate.command_" + command, name), true);
    }
}
