package net.gobies.moreartifacts.util;

import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;

import java.util.Objects;

public class MAUtils {

    public static boolean isFireDMGReduced(LivingHurtEvent event) {
        return event.getSource().is(DamageTypes.ON_FIRE) || event.getSource().is(DamageTypes.IN_FIRE);
    }

    public static void isBurningImmune(LivingAttackEvent event) {
        if (event.getSource().is(DamageTypes.HOT_FLOOR)) {
            event.setCanceled(true);
        }
    }

    public static void isHarmfulEffectApplicable(MobEffectEvent event) {
        if (Objects.requireNonNull(event.getEffectInstance()).getEffect() == MobEffects.POISON ||
                event.getEffectInstance().getEffect() == MobEffects.WITHER ||
                event.getEffectInstance().getEffect() == MobEffects.HUNGER ||
                event.getEffectInstance().getEffect() == MobEffects.CONFUSION ||
                event.getEffectInstance().getEffect() == MobEffects.MOVEMENT_SLOWDOWN ||
                event.getEffectInstance().getEffect() == MobEffects.LEVITATION ||
                event.getEffectInstance().getEffect() == MobEffects.DIG_SLOWDOWN ||
                event.getEffectInstance().getEffect() == MobEffects.WEAKNESS ||
                event.getEffectInstance().getEffect() == MobEffects.BLINDNESS ||
                event.getEffectInstance().getEffect() == MobEffects.DARKNESS) {
            event.setResult(MobEffectEvent.Result.DENY);
        }
    }

    public static void isHarmfulEffectRemovable(Player player) {
        if (player.hasEffect(MobEffects.POISON)) {
            player.removeEffect(MobEffects.POISON);
        }
        if (player.hasEffect(MobEffects.WITHER)) {
            player.removeEffect(MobEffects.WITHER);
        }
        if (player.hasEffect(MobEffects.HUNGER)) {
            player.removeEffect(MobEffects.HUNGER);
        }
        if (player.hasEffect(MobEffects.CONFUSION)) {
            player.removeEffect(MobEffects.CONFUSION);
        }
        if (player.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) {
            player.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
        }
        if (player.hasEffect(MobEffects.LEVITATION)) {
            player.removeEffect(MobEffects.LEVITATION);
        }
        if (player.hasEffect(MobEffects.DIG_SLOWDOWN)) {
            player.removeEffect(MobEffects.DIG_SLOWDOWN);
        }
        if (player.hasEffect(MobEffects.WEAKNESS)) {
            player.removeEffect(MobEffects.WEAKNESS);
        }
        if (player.hasEffect(MobEffects.BLINDNESS)) {
            player.removeEffect(MobEffects.BLINDNESS);
        }
        if (player.hasEffect(MobEffects.DARKNESS)) {
            player.removeEffect(MobEffects.DARKNESS);
        }
    }
}