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

    public static void register(IEventBus eventBus) {
        PARTICLES.register(eventBus);
    }

    static {
        PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MoreArtifacts.MOD_ID);
        BLOOD = PARTICLES.register("blood", () -> new SimpleParticleType(false));
    }
}
