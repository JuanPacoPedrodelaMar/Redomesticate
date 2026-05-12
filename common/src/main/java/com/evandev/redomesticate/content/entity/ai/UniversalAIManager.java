package com.evandev.redomesticate.content.entity.ai;

import com.evandev.redomesticate.Constants;
import com.evandev.redomesticate.content.entity.ai.goal.FollowOwner2Goal;
import com.evandev.redomesticate.content.entity.ai.goal.OwnerHurtByTarget2Goal;
import com.evandev.redomesticate.content.entity.ai.goal.OwnerHurtTarget2Goal;
import com.evandev.redomesticate.content.entity.ai.goal.Sit2Goal;
import com.evandev.redomesticate.mixin.accessor.MobAccessor;
import com.evandev.redomesticate.registry.ModTags;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;

public class UniversalAIManager {

    public static void applyPetAI(Mob mob) {
        if (mob.level().isClientSide) return;

        try {
            GoalSelector goalSelector = ((MobAccessor) mob).redomesticate$getGoalSelector();
            GoalSelector targetSelector = ((MobAccessor) mob).redomesticate$getTargetSelector();

            targetSelector.getAvailableGoals().removeIf(wrapped -> {
                Goal goal = wrapped.getGoal();
                return goal instanceof NearestAttackableTargetGoal<?> ||
                        goal instanceof HurtByTargetGoal;
            });

            boolean alreadyHasPetAI = goalSelector.getAvailableGoals().stream()
                    .anyMatch(wrapped -> wrapped.getGoal() instanceof Sit2Goal);

            if (!alreadyHasPetAI) {
                goalSelector.addGoal(1, new Sit2Goal(mob));

                if (!mob.getType().is(ModTags.USES_BRAIN_AI)) {
                    goalSelector.addGoal(2, new FollowOwner2Goal(mob, 1.2D, 10.0F, 2.0F, false));
                }

                targetSelector.addGoal(1, new OwnerHurtByTarget2Goal(mob));
                targetSelector.addGoal(2, new OwnerHurtTarget2Goal(mob));
            }
        } catch (Exception e) {
            Constants.LOG.warn("Failed to apply Pet AI to: {}", mob.getType().getDescriptionId());
        }
    }
}