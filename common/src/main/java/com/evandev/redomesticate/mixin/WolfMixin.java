package com.evandev.redomesticate.mixin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Wolf.class)
public abstract class WolfMixin extends TamableAnimal {
    protected WolfMixin(EntityType<? extends TamableAnimal> type, Level level) {
        super(type, level);
    }

    @Inject(
            at = @At("HEAD"),
            method = "getTailAngle",
            cancellable = true
    )
    private void getTailAngle(CallbackInfoReturnable<Float> cir) {
        if (!((NeutralMob) this).isAngry() && this.isTame()) {
            float f = (this.getMaxHealth() - this.getHealth()) / this.getMaxHealth() * 20F;
            cir.setReturnValue((0.55F - Math.max(f * 0.02F, 0F)) * (float) Math.PI);
        }
    }
}
