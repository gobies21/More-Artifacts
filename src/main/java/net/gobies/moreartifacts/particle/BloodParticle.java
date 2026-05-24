package net.gobies.moreartifacts.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class BloodParticle extends TextureSheetParticle {

    protected BloodParticle(ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
        super(level, x, y, z, xd, yd, zd);
        this.lifetime = 40 + this.random.nextInt(40); // tick lifespan
        this.xd = xd * 0.1;
        this.yd = yd * 0.1;
        this.zd = zd * 0.1;
        this.quadSize = 0.03F * (1.0F + this.random.nextFloat()); // particle size
        this.gravity = 0.3f;
    }

    @Override
    public void tick() {
        super.tick();
        this.yd -= 0.03;
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @OnlyIn(Dist.CLIENT)
    public record Factory(SpriteSet sprites) implements ParticleProvider<SimpleParticleType> {

        @Override
        public Particle createParticle(@NotNull SimpleParticleType type, @NotNull ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
            BloodParticle particle = new BloodParticle(level, x, y, z, xd, yd, zd);
            particle.pickSprite(sprites);
            return particle;
        }
    }
}