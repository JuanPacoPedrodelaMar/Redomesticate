package com.evandev.redomesticate.registry;

import com.evandev.redomesticate.Constants;
import com.evandev.redomesticate.platform.registry.RegistrationProvider;
import com.evandev.redomesticate.platform.registry.RegistryObject;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;

public class ModParticles {
    public static final RegistrationProvider<ParticleType<?>> DEF_REG = RegistrationProvider.get(Registries.PARTICLE_TYPE, Constants.MOD_ID);

    public static final RegistryObject<SimpleParticleType> DEFLECTION_SHIELD = DEF_REG.register("deflection_shield", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> MAGNET = DEF_REG.register("magnet", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> ZZZ = DEF_REG.register("zzz", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> GIANT_POP = DEF_REG.register("giant_pop", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> SIMPLE_BUBBLE = DEF_REG.register("simple_bubble", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> VAMPIRE = DEF_REG.register("vampire", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> SNIFF = DEF_REG.register("sniff", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> PSYCHIC_WALL = DEF_REG.register("psychic_wall", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> INTIMIDATION = DEF_REG.register("intimidation", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> BLIGHT = DEF_REG.register("blight", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> QUESTION_MARK_PARTICLE_TYPE = DEF_REG.register("question_mark_particle", () -> new SimpleParticleType(false));

    public static void init() {
    }
}
