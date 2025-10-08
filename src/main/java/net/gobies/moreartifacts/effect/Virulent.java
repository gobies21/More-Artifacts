package net.gobies.moreartifacts.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class Virulent extends MobEffect {
    public Virulent(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity livingEntity, int amplifier) {
        if (livingEntity.getHealth() > 2.0F) {
            livingEntity.hurt(livingEntity.damageSources().magic(), 2.0F);
        }
        if (livingEntity.hasEffect(MobEffects.POISON)) {
            livingEntity.removeEffect(MobEffects.POISON);
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        int j = 25 >> amplifier;
        if (j > 0) {
            return duration % j == 0;
        } else {
            return true;
        }
    }
}
