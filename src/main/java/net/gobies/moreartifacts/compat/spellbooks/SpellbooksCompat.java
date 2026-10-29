package net.gobies.moreartifacts.compat.spellbooks;

import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.gobies.moreartifacts.util.ManaUtil;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;

public class SpellbooksCompat {

    private static class LazyHolder {
        private static final Attribute MAX_MANA = AttributeRegistry.MAX_MANA.get();
    }

    public static float getMana(Player player) {
        return MagicData.getPlayerMagicData(player).getMana();
    }

    public static void setMana(Player player, float amount) {
        MagicData.getPlayerMagicData(player).setMana(amount);
    }

    public static float getMaxMana(Player player) {
        var attributeInstance = player.getAttribute(LazyHolder.MAX_MANA);

        if (attributeInstance != null) {
            return (float) attributeInstance.getValue();
        }
        return ManaUtil.DEFAULT_MAX_MANA;
    }
}