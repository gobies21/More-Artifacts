package net.gobies.moreartifacts.util;

import net.gobies.moreartifacts.config.CommonConfig;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

public class LuckHelper {
    public static boolean roll(Player player, double baseChance, double luckBonus) {
        double luck = player.getAttributeValue(Attributes.LUCK);
        if (!CommonConfig.ENABLE_LUCK_FACTOR.get()) {
            return player.getRandom().nextFloat() < baseChance;
        } else {
            float bonus = (float) (luck * luckBonus);
            double finalChance = baseChance + bonus;
            if (finalChance > 1.0f) finalChance = 1.0f;
            if (finalChance < 0.0f) finalChance = 0.0f;

            return player.getRandom().nextFloat() < finalChance;
        }
    }
}