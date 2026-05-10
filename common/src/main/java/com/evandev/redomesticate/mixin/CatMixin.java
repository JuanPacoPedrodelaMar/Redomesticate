package com.evandev.redomesticate.mixin;

import com.evandev.redomesticate.api.ICommandableMob;
import com.evandev.redomesticate.config.ModConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Cat.class)
public abstract class CatMixin extends TamableAnimal implements ICommandableMob {
    @Unique
    private static final EntityDataAccessor<Integer> redomesticate$COMMAND = SynchedEntityData.defineId(Cat.class, EntityDataSerializers.INT);

    protected CatMixin(EntityType<? extends TamableAnimal> type, Level level) {
        super(type, level);
    }

    @Inject(
            at = {@At("TAIL")},
            method = {"defineSynchedData(Lnet/minecraft/network/syncher/SynchedEntityData$Builder;)V"}
    )
    private void registerData(SynchedEntityData.Builder builder, CallbackInfo ci) {
        builder.define(redomesticate$COMMAND, 0);
    }

    @Inject(
            at = {@At("TAIL")},
            method = {"addAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V"}
    )
    private void writeAdditional(CompoundTag compoundNBT, CallbackInfo ci) {
        compoundNBT.putInt("RedomesticateCommand", this.redomesticate$getCommand());
    }

    @Inject(
            at = {@At("TAIL")},
            method = {"readAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V"}
    )
    private void readAdditional(CompoundTag compoundNBT, CallbackInfo ci) {
        this.redomesticate$setCommand(compoundNBT.getInt("RedomesticateCommand"));
    }

    @Inject(
            method = {"mobInteract(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResult;"},
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/animal/Cat;setOrderedToSit(Z)V"
            ),
            cancellable = true
    )
    private void onInteract(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        if (ModConfig.get().trinaryCommandSystem) {
            this.jumping = false;
            this.navigation.stop();
            this.setTarget(null);
            player.swing(hand, true);
            cir.setReturnValue(this.playerSetCommand(player, this));
        }
    }

    public int redomesticate$getCommand() {
        return this.entityData.get(redomesticate$COMMAND);
    }

    public void redomesticate$setCommand(int i) {
        this.entityData.set(redomesticate$COMMAND, i);
    }

    @Override
    public void redomesticate$sendCommandMessage(Player owner, int command, Component name) {
        owner.displayClientMessage(Component.translatable("message.redomesticate.command_" + command, name), true);
    }
}
