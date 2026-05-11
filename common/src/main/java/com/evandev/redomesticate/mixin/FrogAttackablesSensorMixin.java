package com.evandev.redomesticate.mixin;

import com.evandev.redomesticate.api.ITameableEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.sensing.FrogAttackablesSensor;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FrogAttackablesSensor.class)
public class FrogAttackablesSensorMixin {

    @Inject(
            method = "isMatchingEntity(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/LivingEntity;)Z",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void di_isHuntTarget(LivingEntity frog, LivingEntity livingEntity, CallbackInfoReturnable<Boolean> cir) {
        if (frog instanceof ITameableEntity tamed && tamed.redomesticate$getTameOwner() != null && !tamed.redomesticate$isStayingStill()) {
            if (tamed.redomesticate$isValidAttackTarget(livingEntity)) {
                cir.setReturnValue(true);
            } else if (tamed instanceof Axolotl) {
                cir.setReturnValue(false);
            }
        }
    }
}
