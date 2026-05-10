package com.evandev.redomesticate.registry;

import com.evandev.redomesticate.Constants;
import com.evandev.redomesticate.platform.registry.RegistrationProvider;
import com.evandev.redomesticate.platform.registry.RegistryObject;
import com.evandev.redomesticate.server.misc.DrunkEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class ModEffects {
    public static final RegistrationProvider<MobEffect> EFFECTS = RegistrationProvider.get(Registries.MOB_EFFECT, Constants.MOD_ID);
    public static final RegistryObject<MobEffect> DRUNK = EFFECTS.register("drunk", () -> {
        return new DrunkEffect(MobEffectCategory.HARMFUL, 6684723, false);
    });

    public static void init() {
    }
}