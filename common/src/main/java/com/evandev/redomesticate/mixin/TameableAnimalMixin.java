package com.evandev.redomesticate.mixin;

import com.evandev.redomesticate.registry.ModEnchantments;
import com.evandev.redomesticate.util.TameableUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TamableAnimal.class)
public abstract class TameableAnimalMixin extends Animal {

    protected TameableAnimalMixin(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(
            method = {"isAlliedTo(Lnet/minecraft/world/entity/Entity;)Z"},
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void isAlliedTo(Entity other, CallbackInfoReturnable<Boolean> cir) {
        if (TameableUtils.hasSameOwnerAs(this, other)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(
            method = "canTeleportTo(Lnet/minecraft/core/BlockPos;)Z",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onCanTeleportTo(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (TameableUtils.hasEnchant(this, ModEnchantments.AMPHIBIOUS) && this.level().isWaterAt(pos)) {
            cir.setReturnValue(true);
        }
    }
}
