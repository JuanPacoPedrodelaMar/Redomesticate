package com.evandev.redomesticate.registry;

import com.evandev.redomesticate.Constants;
import com.evandev.redomesticate.platform.registry.RegistrationProvider;
import com.evandev.redomesticate.platform.registry.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.schedule.Activity;

public class ModActivities {
    public static final RegistrationProvider<Activity> DEF_REG = RegistrationProvider.get(Registries.ACTIVITY, Constants.MOD_ID);

    public static final RegistryObject<Activity> AXOLOTL_FOLLOW = DEF_REG.register("axolotl_follow", () -> new Activity("axolotl_follow"));
    public static final RegistryObject<Activity> AXOLOTL_STAY = DEF_REG.register("axolotl_stay", () -> new Activity("axolotl_stay"));
    public static final RegistryObject<Activity> FROG_FOLLOW = DEF_REG.register("frog_follow", () -> new Activity("frog_follow"));
    public static final RegistryObject<Activity> FROG_STAY = DEF_REG.register("frog_stay", () -> new Activity("frog_stay"));

    public static void init() {
    }
}