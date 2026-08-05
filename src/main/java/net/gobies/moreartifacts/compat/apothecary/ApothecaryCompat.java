package net.gobies.moreartifacts.compat.apothecary;

import net.gobies.apothecary.init.AAttributes;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.util.CurioHandler;
import net.gobies.moreartifacts.util.MAUtils;
import net.gobies.moreartifacts.util.ShieldHandler;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.gobies.apothecary.init.AEffects;

public class ApothecaryCompat {

    public static void loadCompat() {
        MinecraftForge.EVENT_BUS.register(new ApothecaryCompat());
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Player player = event.player;
            if (player.level().isClientSide()) return;
            if (player.getActiveEffects().isEmpty()) return;
            if (player.tickCount % 5 != 0) return;

            //Burning Immunity
            if (player.hasEffect(AEffects.Burning.get())) {
                if (CurioHandler.isCurioEquipped(player, MAItems.ObsidianSkull.get())) {
                    MAUtils.removeEffect(player, AEffects.Burning.get());
                }
                if (CurioHandler.isCurioEquipped(player, MAItems.ObsidianShield.get())) {
                    MAUtils.removeEffect(player, AEffects.Burning.get());
                }
                if (CurioHandler.isCurioEquipped(player, MAItems.AnkhShield.get())) {
                    MAUtils.removeEffect(player, AEffects.Burning.get());
                }
                if (CurioHandler.isCurioEquipped(player, MAItems.FireStone.get())) {
                    MAUtils.removeEffect(player, AEffects.Burning.get());
                }
                if (ShieldHandler.isShieldEquipped(player, MAItems.ObsidianShield.get())) {
                    MAUtils.removeEffect(player, AEffects.Burning.get());
                }
                if (ShieldHandler.isShieldEquipped(player, MAItems.AnkhShield.get())) {
                    MAUtils.removeEffect(player, AEffects.Burning.get());
                }
            }
            if (player.hasEffect(AEffects.BrokenArmor.get())) {
                if (CurioHandler.isCurioEquipped(player, MAItems.HeroShield.get())) {
                    MAUtils.removeEffect(player, AEffects.BrokenArmor.get());
                }
            }
        }
    }

    @SubscribeEvent
    public void onMobEffectApplicable(MobEffectEvent.Applicable event) {
        if (event.getEntity() instanceof Player player) {

            //Burning Immunity
            if (CurioHandler.isCurioEquipped(player, MAItems.ObsidianSkull.get())) {
                MAUtils.harmfulSpecificEffectImmune(event, AEffects.Burning.get());
            }
            if (CurioHandler.isCurioEquipped(player, MAItems.ObsidianShield.get())) {
                MAUtils.harmfulSpecificEffectImmune(event, AEffects.Burning.get());
            }
            if (CurioHandler.isCurioEquipped(player, MAItems.AnkhShield.get())) {
                MAUtils.harmfulSpecificEffectImmune(event, AEffects.Burning.get());
            }
            if (CurioHandler.isCurioEquipped(player, MAItems.FireStone.get())) {
                MAUtils.harmfulSpecificEffectImmune(event, AEffects.Burning.get());
            }
            if (ShieldHandler.isShieldEquipped(player, MAItems.ObsidianShield.get())) {
                MAUtils.harmfulSpecificEffectImmune(event, AEffects.Burning.get());
            }
            if (ShieldHandler.isShieldEquipped(player, MAItems.AnkhShield.get())) {
                MAUtils.harmfulSpecificEffectImmune(event, AEffects.Burning.get());
            }
            if (CurioHandler.isCurioEquipped(player, MAItems.HeroShield.get())) {
                MAUtils.harmfulSpecificEffectImmune(event, AEffects.BrokenArmor.get());
            }
        }
    }

    public static Attribute damageResistance() {
        return AAttributes.DAMAGE_RESISTANCE.get();
    }

    public static Attribute damageMultiplier() {
        return AAttributes.DAMAGE_MULTIPLIER.get();
    }

    public static Attribute jumpHeight() {
        return AAttributes.JUMP_HEIGHT.get();
    }

    public static Attribute projectileDamage() {
        return AAttributes.PROJECTILE_DAMAGE.get();
    }
}
