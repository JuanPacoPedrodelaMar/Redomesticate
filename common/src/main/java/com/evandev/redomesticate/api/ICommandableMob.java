package com.evandev.redomesticate.api;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;

public interface ICommandableMob {

    int redomesticate$getCommand();

    void redomesticate$setCommand(int command);

    default InteractionResult playerSetCommand(Player owner, Animal ourselves) {
        if (!owner.level().isClientSide()) {
            int command = (redomesticate$getCommand() + 1) % 3;
            this.redomesticate$setCommand(command);
            this.redomesticate$sendCommandMessage(owner, command, ourselves.getName());

            if (ourselves instanceof TamableAnimal) {
                ((TamableAnimal) (ourselves)).setOrderedToSit(command == 1);
            }
        }
        return InteractionResult.PASS;
    }

    default void redomesticate$sendCommandMessage(Player owner, int command, Component name) {

    }

}
