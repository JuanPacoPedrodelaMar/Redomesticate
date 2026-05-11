package com.evandev.redomesticate.mixin;

import com.evandev.redomesticate.Constants;
import com.evandev.redomesticate.api.IPetbedDataEntity;
import com.evandev.redomesticate.api.ITameableEntity;
import com.evandev.redomesticate.registry.ModEnchantments;
import com.evandev.redomesticate.util.TameableUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({LivingEntity.class})
public abstract class LivingEntityMixin extends Entity implements IPetbedDataEntity {
    @Unique
    private static final EntityDataAccessor<CompoundTag> REDOMESTICATE_SAVED_DATA = SynchedEntityData.defineId(LivingEntityMixin.class, EntityDataSerializers.COMPOUND_TAG);

    protected LivingEntityMixin(EntityType<? extends Entity> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(
            at = @At("TAIL"),
            method = "defineSynchedData"
    )
    private void registerData(SynchedEntityData.Builder builder, CallbackInfo ci) {
        builder.define(REDOMESTICATE_SAVED_DATA, new CompoundTag());
    }

    @Inject(
            method = "getWaterSlowDown()F",
            at = @At(value = "TAIL"),
            cancellable = true
    )
    private void getWaterSlowdown(CallbackInfoReturnable<Float> cir) {
        if (TameableUtils.isTamed(this) && redomesticate$isLandAndSea()) {
            cir.setReturnValue(0.98F);
        }
    }

    @Inject(
            at = @At("TAIL"),
            method = "addAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V"
    )
    private void writeAdditional(CompoundTag compoundNBT, CallbackInfo ci) {
        CompoundTag citadelDat = this.redomesticate$getEntityData();
        if (citadelDat != null) {
            compoundNBT.put(Constants.ENTITY_SYNC_DATA, citadelDat);
        }

    }

    @Inject(
            at = @At("TAIL"),
            method = "readAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V"
    )
    private void readAdditional(CompoundTag compoundNBT, CallbackInfo ci) {
        if (compoundNBT.contains(Constants.ENTITY_SYNC_DATA)) {
            this.redomesticate$setEntityData(compoundNBT.getCompound(Constants.ENTITY_SYNC_DATA));
        }

    }

    @Unique
    private boolean redomesticate$isLandAndSea() {
        return TameableUtils.hasEnchant(((LivingEntity) (Entity) this), ModEnchantments.AMPHIBIOUS);
    }

    @Override
    public CompoundTag redomesticate$getEntityData() {
        return this.entityData.get(REDOMESTICATE_SAVED_DATA);
    }

    @Override
    public void redomesticate$setEntityData(CompoundTag nbt) {
        this.entityData.set(REDOMESTICATE_SAVED_DATA, nbt);
    }

}
