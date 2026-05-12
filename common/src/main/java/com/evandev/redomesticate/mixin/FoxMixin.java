package com.evandev.redomesticate.mixin;

import com.evandev.redomesticate.api.ICommandableMob;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Fox.class)
public abstract class FoxMixin extends Animal {

    protected FoxMixin(EntityType<? extends Animal> foxType, Level level) {
        super(foxType, level);
    }

    @Shadow
    public abstract void setSitting(boolean sitting);

    @Shadow
    abstract void setSleeping(boolean sleeping);

    @WrapOperation(method = "aiStep()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;finishUsingItem(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;)Lnet/minecraft/world/item/ItemStack;"))
    private ItemStack wrapFinishUsingItem(ItemStack instance, Level level, LivingEntity livingEntity, Operation<ItemStack> original) {
        FoodProperties food = instance.get(DataComponents.FOOD);
        if (!instance.isEmpty() && food != null) {
            this.heal(food.nutrition() * 2);
        }
        return original.call(instance, level, livingEntity);
    }

    @Inject(at = @At("TAIL"), method = "aiStep()V")
    private void aiStep(CallbackInfo ci) {
        if (((ICommandableMob) this).redomesticate$isFollowingOwner()) {
            this.setSleeping(false);
            this.setSitting(false);
        }
    }
}