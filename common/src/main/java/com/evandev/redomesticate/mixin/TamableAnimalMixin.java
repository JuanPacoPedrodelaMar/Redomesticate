package com.evandev.redomesticate.mixin;

import com.evandev.redomesticate.api.ICommandableMob;
import com.evandev.redomesticate.api.PetCommand;
import com.evandev.redomesticate.config.ModConfig;
import net.minecraft.world.entity.TamableAnimal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TamableAnimal.class)
public abstract class TamableAnimalMixin {

    @Inject(method = "setOrderedToSit", at = @At("HEAD"), cancellable = true)
    private void redomesticate$lockSitStateToCommand(boolean pOrderedToSit, CallbackInfo ci) {
        TamableAnimal tamable = (TamableAnimal) (Object) this;

        if (ModConfig.get().trinaryCommandSystem && tamable.isTame() && this instanceof ICommandableMob commandable) {
            boolean shouldSit = commandable.redomesticate$getPetCommand() == PetCommand.SIT;

            if (pOrderedToSit != shouldSit) {
                ci.cancel();
            }
        }
    }
}