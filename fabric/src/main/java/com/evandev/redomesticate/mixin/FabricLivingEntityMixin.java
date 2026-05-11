package com.evandev.redomesticate.mixin;

import com.evandev.redomesticate.event.EventProxy;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class FabricLivingEntityMixin {

    @Inject(method = "tick", at = @At("TAIL"))
    private void redomesticate$onLivingTick(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (!entity.level().isClientSide) {
            EventProxy.onLivingUpdate(entity);
        }
    }

    @Inject(method = "dropAllDeathLoot", at = @At("HEAD"), cancellable = true)
    private void redomesticate$onLivingDrops(ServerLevel level, DamageSource damageSource, CallbackInfo ci) {
        if (EventProxy.onLivingDrops((LivingEntity) (Object) this)) {
            ci.cancel();
        }
    }

    @Inject(method = "randomTeleport(DDDZ)Z", at = @At("HEAD"))
    private void redomesticate$onRandomTeleport(double x, double y, double z, boolean broadcastTeleport, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        EventProxy.onEntityTeleport(entity, entity.position(), new Vec3(x, y, z));
    }

    @Inject(method = "die", at = @At("HEAD"))
    private void redomesticate$onDie(DamageSource damageSource, CallbackInfo ci) {
        EventProxy.onLivingDie((LivingEntity) (Object) this, damageSource);
    }

    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    private void redomesticate$onLivingDamagePre(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        EventProxy.onTameHurt(entity, source);

        if (EventProxy.onLivingDamage(entity, source, amount)) {
            cir.setReturnValue(false);
        } else {
            EventProxy.onEntityHurt(entity, source, amount, amount);
        }
    }
}