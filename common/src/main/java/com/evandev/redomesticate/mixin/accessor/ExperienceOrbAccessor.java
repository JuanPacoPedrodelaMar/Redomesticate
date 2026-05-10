package com.evandev.redomesticate.mixin.accessor;

import net.minecraft.world.entity.ExperienceOrb;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ExperienceOrb.class)
public interface ExperienceOrbAccessor {
    @Accessor("value")
    int redomesticate$getValue();

    @Accessor("value")
    void redomesticate$setValue(int value);
}