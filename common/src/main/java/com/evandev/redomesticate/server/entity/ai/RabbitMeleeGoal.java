package com.evandev.redomesticate.server.entity.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.animal.Animal;

public class RabbitMeleeGoal extends MeleeAttackGoal {
    public RabbitMeleeGoal(Animal animal) {
        super(animal, 1.4D, true);
    }

    protected double getAttackReachSqr(LivingEntity animal) {
        return 4.0F + animal.getBbWidth();
    }
}