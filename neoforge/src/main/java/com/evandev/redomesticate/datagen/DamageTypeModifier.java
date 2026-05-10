package com.evandev.redomesticate.datagen;

import com.evandev.redomesticate.registry.ModDamageTypes;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DeathMessageType;

public class DamageTypeModifier {
    public static void bootstrap(BootstrapContext<DamageType> bootstrap) {


        bootstrap.register(ModDamageTypes.SIPHON, new DamageType("redomesticate.siphon",
                DamageScaling.NEVER,
                0,
                DamageEffects.HURT,
                DeathMessageType.DEFAULT));


    }
}