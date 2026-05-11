package com.evandev.redomesticate.api;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;

public interface ICommandableMob {

    int redomesticate$getCommand();

    void redomesticate$setCommand(int command);

    default PetCommand redomesticate$getPetCommand() {
        return PetCommand.fromId(redomesticate$getCommand());
    }

    default void redomesticate$setPetCommand(PetCommand command) {
        redomesticate$setCommand(command.getId());
    }

    default InteractionResult playerSetCommand(Player owner, Mob ourselves) {
        if (!owner.level().isClientSide()) {
            PetCommand nextCommand = redomesticate$getPetCommand().next();
            this.redomesticate$setPetCommand(nextCommand);

            this.redomesticate$sendCommandMessage(owner, nextCommand.getId(), ourselves.getName());

            if (ourselves instanceof TamableAnimal tamable) {
                tamable.setOrderedToSit(nextCommand == PetCommand.SIT);
                tamable.setInSittingPose(nextCommand == PetCommand.SIT);
            }
        }
        return InteractionResult.SUCCESS;
    }

    default void redomesticate$sendCommandMessage(Player owner, int command, Component name) {
        owner.displayClientMessage(Component.translatable("message.redomesticate.command_" + command, name), true);
    }
}