package com.evandev.redomesticate.mixin;

import com.evandev.redomesticate.server.ServerProxy;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
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
            ServerProxy.onLivingUpdate(entity);
        }
    }

    @Inject(method = "dropAllDeathLoot", at = @At("HEAD"), cancellable = true)
    private void redomesticate$onLivingDrops(ServerLevel level, DamageSource damageSource, CallbackInfo ci) {
        if (ServerProxy.onLivingDrops((LivingEntity) (Object) this)) {
            ci.cancel();
        }
    }

    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    private void redomesticate$onLivingDamagePre(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        ServerProxy.onTameHurt(entity, source);

        float newDamage = ServerProxy.onEntityHurtPre(entity, source, amount);

        if (ServerProxy.onLivingDamage(entity, source, newDamage)) {
            cir.setReturnValue(false);
        } else {
            ServerProxy.onEntityHurt(entity, source, amount, newDamage);
        }
    }
}