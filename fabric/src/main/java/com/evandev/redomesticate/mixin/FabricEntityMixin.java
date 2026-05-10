package com.evandev.redomesticate.mixin;

import com.evandev.redomesticate.server.ServerProxy;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class FabricEntityMixin {

    @Inject(method = "startRiding(Lnet/minecraft/world/entity/Entity;Z)Z", at = @At("HEAD"), cancellable = true)
    private void redomesticate$onStartRiding(Entity vehicle, boolean force, CallbackInfoReturnable<Boolean> cir) {
        if (ServerProxy.onEntityMount(vehicle, (Entity) (Object) this, false)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "removeVehicle", at = @At("HEAD"), cancellable = true)
    private void redomesticate$onRemoveVehicle(CallbackInfo ci) {
        Entity entity = (Entity) (Object) this;
        Entity vehicle = entity.getVehicle();
        if (vehicle != null) {
            if (ServerProxy.onEntityMount(vehicle, entity, true)) {
                ci.cancel();
            }
        }
    }
}