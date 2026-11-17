package net.gobies.moreartifacts.compat.iceandfire2;

import net.gobies.moreartifacts.item.artifacts.DragonEyeItem;
import net.gobies.moreartifacts.util.ModLoadedUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public class IceandFire2Compat extends DragonEyeItem {
    public IceandFire2Compat(Properties properties) {
        super(properties);
    }

    public static void proccessEffects(Player player, ItemStack stack) {
        if (ModLoadedUtil.isIceandFire2Loaded()) {
            MobEffect effect;
            if (isFireDragonEye(stack)) {
                effect = fireResistance();
                MobEffectInstance mobEffect = player.getEffect(effect);
                if (mobEffect == null || mobEffect.getDuration() <= 110) {
                    player.addEffect(new MobEffectInstance(effect, 210, 0, true, false));
                }
            }
            if (isIceDragonEye(stack)) {
                effect = frostResistance();
                MobEffectInstance mobEffect = player.getEffect(effect);
                if (mobEffect == null || mobEffect.getDuration() <= 110) {
                    player.addEffect(new MobEffectInstance(effect, 210, 0, true, false));
                }
            }
            if (isLightningDragonEye(stack)) {
                effect = voltageResistance();
                MobEffectInstance mobEffect = player.getEffect(effect);
                if (mobEffect == null || mobEffect.getDuration() <= 110) {
                    player.addEffect(new MobEffectInstance(effect, 210, 0, true, false));
                }
            }
        }
    }

    public static void removeEffects(Player player, ItemStack stack) {
        if (ModLoadedUtil.isIceandFireLoaded()) {
            if (isFireDragonEye(stack)) {
                if (player.hasEffect(fireResistance())) {
                    player.removeEffect(fireResistance());
                }
            }
            if (isIceDragonEye(stack)) {
                if (player.hasEffect(frostResistance())) {
                    player.removeEffect(frostResistance());
                }
            }
            if (isLightningDragonEye(stack)) {
                if (player.hasEffect(voltageResistance())) {
                    player.removeEffect(voltageResistance());
                }
            }
        }
    }

    public static MobEffect fireResistance() {
        return MobEffects.FIRE_RESISTANCE;
    }

    public static MobEffect frostResistance() {
        return ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("iceandfire2", "frost_resistance"));
    }

    public static MobEffect voltageResistance() {
        return ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("iceandfire2", "voltage_resistance"));
    }
}
