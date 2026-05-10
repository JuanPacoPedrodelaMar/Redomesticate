package com.evandev.redomesticate.server.entity.ai;

import com.evandev.redomesticate.api.ITameableEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Fox;

import java.util.EnumSet;

public class Sit2Goal extends Goal {
    private final Animal mob;

    public Sit2Goal(Animal animal) {
        this.mob = animal;
        this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE));
    }

    public boolean canContinueToUse() {
        return ((ITameableEntity) this.mob).redomesticate$isTame() && ((ITameableEntity) this.mob).redomesticate$isStayingStill();
    }

    public boolean canUse() {
        if (!((ITameableEntity) this.mob).redomesticate$isTame()) {
            return false;
        } else if (this.mob.isInWaterOrBubble()) {
            return false;
        } else if (!this.mob.onGround()) {
            return false;
        } else {
            return ((ITameableEntity) this.mob).redomesticate$isStayingStill();
        }
    }

    public void start() {
        this.mob.getNavigation().stop();
        if (this.mob instanceof Fox) {
            ((Fox) this.mob).setSitting(true);
        }
    }

    public void stop() {
    }
}
