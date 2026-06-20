package net.gobies.moreartifacts.util;

import net.gobies.moreartifacts.config.CommonConfig;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class LuckHelper {
    public static boolean roll(LivingEntity livingEntity, double baseChance, double luckBonus) {
        double luck = livingEntity.getAttributeValue(Attributes.LUCK);
        if (!CommonConfig.ENABLE_LUCK_FACTOR.get()) {
            return livingEntity.getRandom().nextFloat() < baseChance;
        } else {
            float bonus = (float) (luck * luckBonus);
            double finalChance = baseChance + bonus;
            if (finalChance > 1.0f) finalChance = 1.0f;
            if (finalChance < 0.0f) finalChance = 0.0f;

            return livingEntity.getRandom().nextFloat() < finalChance;
        }
    }
}