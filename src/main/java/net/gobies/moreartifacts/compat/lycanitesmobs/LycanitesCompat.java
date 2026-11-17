package net.gobies.moreartifacts.compat.lycanitesmobs;

import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.item.artifacts.DragonEyeItem;
import net.gobies.moreartifacts.util.CurioHandler;
import net.gobies.moreartifacts.util.MAUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class LycanitesCompat {

    public static void loadCompat() {
        MinecraftForge.EVENT_BUS.register(new LycanitesCompat());
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Player player = event.player;
            if (player.level().isClientSide()) return;
            if (player.tickCount % 10 != 0) return;
            if (player.getActiveEffects().isEmpty()) return;

            ItemStack stack = CurioHandler.getEquippedCurio(player, MAItems.DragonEye.get());
            if (!stack.isEmpty()) {
                if (DragonEyeItem.isLightningDragonEye(stack)) {
                    MAUtils.removeEffect(player, paralysis());
                }
            }
            if (CurioHandler.isCurioEquipped(player, MAItems.StormFang.get())) {
                MAUtils.removeEffect(player, paralysis());
            }
            if (CurioHandler.isCurioEquipped(player, MAItems.MelodyPlushie.get())) {
                MAUtils.removeEffect(player, fear(), insomnia());
            }
        }
    }

    @SubscribeEvent
    public void onMobEffectApplicable(MobEffectEvent.Applicable event) {
        if (event.getEntity() instanceof Player player) {
            ItemStack stack = CurioHandler.getEquippedCurio(player, MAItems.DragonEye.get());
            if (!stack.isEmpty()) {
                if (DragonEyeItem.isLightningDragonEye(stack)) {
                    MAUtils.makeEffectImmune(event, paralysis());
                }
            }
            if (CurioHandler.isCurioEquipped(player, MAItems.StormFang.get())) {
                MAUtils.makeEffectImmune(event, paralysis());
            }
            if (CurioHandler.isCurioEquipped(player, MAItems.MelodyPlushie.get())) {
                MAUtils.makeEffectImmune(event, fear(), insomnia());
            }
        }
    }

    public static MobEffect paralysis() {
        return ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("lycanitesmobs", "paralysis"));
    }

    public static MobEffect fear() {
        return ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("lycanitesmobs", "fear"));
    }

    public static MobEffect insomnia() {
        return ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("lycanitesmobs", "insomnia"));
    }
}
