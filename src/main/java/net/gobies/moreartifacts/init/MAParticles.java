package net.gobies.moreartifacts.init;

import net.gobies.moreartifacts.MoreArtifacts;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MAParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLES;
    public static final RegistryObject<SimpleParticleType> BLOOD;
    public static final RegistryObject<SimpleParticleType> STORM_SHOCK;
    public static final RegistryObject<SimpleParticleType> MANA_SHIELD_DAMAGE;
    public static final RegistryObject<SimpleParticleType> MANA_SHIELD_BREAK;
    public static final RegistryObject<SimpleParticleType> ANNIHILATION_SLASH;

    public static void register(IEventBus eventBus) {
        PARTICLES.register(eventBus);
    }

    static {
        PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MoreArtifacts.MOD_ID);
        BLOOD = PARTICLES.register("blood", () -> new SimpleParticleType(false));
        STORM_SHOCK = PARTICLES.register("storm_shock", () -> new SimpleParticleType(false));
        MANA_SHIELD_DAMAGE = PARTICLES.register("mana_shield_damage", () -> new SimpleParticleType(false));
        MANA_SHIELD_BREAK = PARTICLES.register("mana_shield_break", () -> new SimpleParticleType(false));
        ANNIHILATION_SLASH = PARTICLES.register("annihilation_slash", () -> new SimpleParticleType(false));

    }
}
