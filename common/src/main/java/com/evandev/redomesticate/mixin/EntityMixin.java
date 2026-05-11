package com.evandev.redomesticate.mixin;

import com.evandev.redomesticate.api.ITameableEntity;
import com.evandev.redomesticate.content.entity.PsychicWallEntity;
import com.evandev.redomesticate.registry.ModEnchantments;
import com.evandev.redomesticate.util.TameableUtils;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {

    @Inject(method = "fireImmune()Z", at = @At("HEAD"), cancellable = true)
    private void isFireImmune(CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof LivingEntity livingThis) {
            if (TameableUtils.isTamed(livingThis) && TameableUtils.hasEnchant(livingThis, ModEnchantments.FIREPROOF)) {
                cir.setReturnValue(true);
            }
        }
    }

    @Inject(method = "isPushedByFluid()Z", at = @At("HEAD"), cancellable = true)
    protected void pushedByWater(CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof LivingEntity livingThis) {
            if (TameableUtils.isTamed(livingThis) && TameableUtils.hasEnchant(livingThis, ModEnchantments.AMPHIBIOUS)) {
                cir.setReturnValue(false);
            }
        }
    }

    @Inject(method = "isAlliedTo(Lnet/minecraft/world/entity/Entity;)Z", at = @At("HEAD"), cancellable = true)
    private void redomesticate$universalAlliances(Entity other, CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof ITameableEntity tameable && tameable.redomesticate$isTame()) {
            if (other == tameable.redomesticate$getTameOwner()) {
                cir.setReturnValue(true);
            }
            else if ((Object) this instanceof LivingEntity livingThis && other instanceof LivingEntity livingOther) {
                if (TameableUtils.hasSameOwnerAs(livingThis, livingOther)) {
                    cir.setReturnValue(true);
                }
            }
        }
    }

    @Inject(method = "canCollideWith(Lnet/minecraft/world/entity/Entity;)Z", at = @At("HEAD"), cancellable = true)
    protected void canCollideWith(Entity other, CallbackInfoReturnable<Boolean> cir) {
        if (other instanceof PsychicWallEntity wall && wall.isSameTeam((Entity) (Object) this)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "dismountsUnderwater()Z", at = @At("HEAD"), cancellable = true)
    protected void rideableInWater(CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof LivingEntity livingThis) {
            if (TameableUtils.isTamed(livingThis) && TameableUtils.hasEnchant(livingThis, ModEnchantments.AMPHIBIOUS)) {
                cir.setReturnValue(true);
            }
        }
    }

    @Inject(method = "getMovementEmission()Lnet/minecraft/world/entity/Entity$MovementEmission;", at = @At("HEAD"), cancellable = true)
    protected void getMovementEmission(CallbackInfoReturnable<Entity.MovementEmission> cir) {
        if ((Object) this instanceof LivingEntity livingThis) {
            if (TameableUtils.isTamed(livingThis) && TameableUtils.hasEnchant(livingThis, ModEnchantments.MUFFLED)) {
                cir.setReturnValue(Entity.MovementEmission.NONE);
            }
        }
    }

    @Inject(method = "gameEvent(Lnet/minecraft/core/Holder;Lnet/minecraft/world/entity/Entity;)V", at = @At("HEAD"), cancellable = true)
    protected void gameEvent(Holder<GameEvent> gameEvent, Entity entity, CallbackInfo ci) {
        if ((Object) this instanceof LivingEntity livingThis) {
            if (TameableUtils.isTamed(livingThis) && TameableUtils.hasEnchant(livingThis, ModEnchantments.MUFFLED)) {
                ci.cancel();
            }
        }
    }
}