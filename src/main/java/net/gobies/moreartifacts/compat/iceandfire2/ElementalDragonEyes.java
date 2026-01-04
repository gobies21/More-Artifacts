package net.gobies.moreartifacts.compat.iceandfire2;

import net.gobies.moreartifacts.item.artifacts.DragonEyeItem;
import net.gobies.moreartifacts.util.ModLoadedUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ElementalDragonEyes extends DragonEyeItem {
    public ElementalDragonEyes(Properties properties) {
        super(properties);
    }

    public static void proccessEffects(Player player, ItemStack stack) {
        if (ModLoadedUtil.isIceandFireLoaded()) {
            if (isFireDragonEye(stack)) {
                if (!player.hasEffect(MobEffects.NIGHT_VISION) || !player.hasEffect(MobEffects.FIRE_RESISTANCE)) {
                    player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, -1, 0, true, false));
                    player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, -1, 0, true, false));
                }
            }
            if (isIceDragonEye(stack)) {
                if (!player.hasEffect(MobEffects.NIGHT_VISION)) {
                    player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, -1, 0, true, false));

                }
            }
            if (isLightningDragonEye(stack)) {
                if (!player.hasEffect(MobEffects.NIGHT_VISION)) {
                    player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, -1, 0, true, false));

                }
            }
        }
    }

    public static void removeEffects(Player player, ItemStack stack) {
        if (ModLoadedUtil.isIceandFireLoaded()) {
            if (isFireDragonEye(stack)) {
                if (player.hasEffect(MobEffects.NIGHT_VISION) || player.hasEffect(MobEffects.FIRE_RESISTANCE)) {
                    player.removeEffect(MobEffects.NIGHT_VISION);
                    player.removeEffect(MobEffects.FIRE_RESISTANCE);
                }
            }
            if (isIceDragonEye(stack)) {
                if (player.hasEffect(MobEffects.NIGHT_VISION)) {
                    player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, -1, 0, true, false));

                }
            }
            if (isLightningDragonEye(stack)) {
                if (player.hasEffect(MobEffects.NIGHT_VISION)) {
                    player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, -1, 0, true, false));

                }
            }
        }
    }
}
