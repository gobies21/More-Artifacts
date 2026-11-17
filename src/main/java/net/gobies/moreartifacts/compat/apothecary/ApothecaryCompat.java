package net.gobies.moreartifacts.compat.apothecary;

import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.item.artifacts.DragonEyeItem;
import net.gobies.moreartifacts.util.CurioHandler;
import net.gobies.moreartifacts.util.MAUtils;
import net.gobies.moreartifacts.util.ShieldHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.gobies.apothecary.init.AEffects;
import net.minecraftforge.registries.ForgeRegistries;

public class ApothecaryCompat {

    public static void loadCompat() {
        MinecraftForge.EVENT_BUS.register(new ApothecaryCompat());
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Player player = event.player;
            if (player.level().isClientSide()) return;
            if (player.tickCount % 10 != 0) return;
            if (player.getActiveEffects().isEmpty()) return;

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
            if (CurioHandler.isCurioEquipped(player, MAItems.HeroShield.get())) {
                MAUtils.removeEffect(player, AEffects.BrokenArmor.get());
            }
            if (CurioHandler.isCurioEquipped(player, MAItems.ShadowAegis.get())) {
                MAUtils.removeEffect(player, AEffects.BrokenArmor.get(), AEffects.RupturedArmor.get());
            }
            if (CurioHandler.isCurioEquipped(player, MAItems.StormFang.get())) {
                MAUtils.removeEffect(player, AEffects.Shocked.get());
            }
            ItemStack stack = CurioHandler.getEquippedCurio(player, MAItems.DragonEye.get());
            if (!stack.isEmpty()) {
                if (DragonEyeItem.isLightningDragonEye(stack)) {
                    MAUtils.removeEffect(player, shocked());
                }
            }
        }
    }

    @SubscribeEvent
    public void onMobEffectApplicable(MobEffectEvent.Applicable event) {
        if (event.getEntity() instanceof Player player) {
            if (CurioHandler.isCurioEquipped(player, MAItems.ObsidianSkull.get())) {
                MAUtils.makeEffectImmune(event, AEffects.Burning.get());
            }
            if (CurioHandler.isCurioEquipped(player, MAItems.ObsidianShield.get())) {
                MAUtils.makeEffectImmune(event, AEffects.Burning.get());
            }
            if (CurioHandler.isCurioEquipped(player, MAItems.AnkhShield.get())) {
                MAUtils.makeEffectImmune(event, AEffects.Burning.get());
            }
            if (CurioHandler.isCurioEquipped(player, MAItems.FireStone.get())) {
                MAUtils.makeEffectImmune(event, AEffects.Burning.get());
            }
            if (ShieldHandler.isShieldEquipped(player, MAItems.ObsidianShield.get())) {
                MAUtils.makeEffectImmune(event, AEffects.Burning.get());
            }
            if (ShieldHandler.isShieldEquipped(player, MAItems.AnkhShield.get())) {
                MAUtils.makeEffectImmune(event, AEffects.Burning.get());
            }
            if (CurioHandler.isCurioEquipped(player, MAItems.HeroShield.get())) {
                MAUtils.makeEffectImmune(event, AEffects.BrokenArmor.get());
            }
            if (CurioHandler.isCurioEquipped(player, MAItems.StormFang.get())) {
                MAUtils.makeEffectImmune(event, AEffects.Shocked.get());
            }
            ItemStack stack = CurioHandler.getEquippedCurio(player, MAItems.DragonEye.get());
            if (!stack.isEmpty()) {
                if (DragonEyeItem.isLightningDragonEye(stack)) {
                    MAUtils.makeEffectImmune(event, shocked());
                }
            }
        }
    }

    public static Attribute damageResistance() {
        return ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("apothecary", "damage_resistance"));
    }

    public static Attribute damageMultiplier() {
        return ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("apothecary", "damage_multiplier"));
    }

    public static Attribute jumpHeight() {
        return ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("apothecary", "jump_height"));
    }

    public static Attribute projectileDamage() {
        return ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("apothecary", "projectile_damage"));
    }

    public static Attribute magicShielding() {
        return ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("apothecary", "magic_shielding"));
    }

    public static MobEffect flight() {
        return ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("apothecary", "flight"));
    }

    public static MobEffect shocked() {
        return ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("apothecary", "shocked"));
    }
}
