package com.evandev.redomesticate.mixin;

import com.evandev.redomesticate.util.TameableUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Player.class)
public class PlayerMixin {

    @Redirect(
            method = {"attack(Lnet/minecraft/world/entity/Entity;)V"},
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Player;isAlliedTo(Lnet/minecraft/world/entity/Entity;)Z"
            )
    )
    private boolean di_onSweepAttack_isAlliedTo(Player player, Entity entity) {
        return TameableUtils.isPetOf(player, entity) || player.isAlliedTo(entity);
    }
}
