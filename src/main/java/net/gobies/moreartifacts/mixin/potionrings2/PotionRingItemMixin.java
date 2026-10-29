package net.gobies.moreartifacts.mixin.potionrings2;

import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.util.CurioHandler;
import net.gobies.potionrings2.PRConfig;
import net.gobies.potionrings2.init.PRItems;
import net.gobies.potionrings2.item.potionrings.PotionRingItem;
import net.gobies.potionrings2.util.RingHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.SlotContext;

@Mixin(PotionRingItem.class)
public class PotionRingItemMixin {

    @Inject(
            method = "curioTick",
            at = @At("HEAD"),
            remap = false
    )
    public void curioTick(SlotContext slotContext, ItemStack stack, CallbackInfo ci) {
        LivingEntity entity = slotContext.entity();
        CompoundTag nbt = stack.getTag();
        if (nbt != null && nbt.contains("Effect")) {
            String effectIdString = nbt.getString("Effect");
            ResourceLocation effectId = new ResourceLocation(effectIdString);
            MobEffect effect = ForgeRegistries.MOB_EFFECTS.getValue(effectId);
            if (effect != null) {
                int ringCount = RingHandler.getEquippedRingCount(entity, PRItems.PotionRing.get(), effectIdString);
                int maxLevel = PRConfig.LEVEL.get() - 1;
                int effectLevel = ringCount - 1;

                if (entity instanceof Player player) {
                    if (effect == MobEffects.DAMAGE_RESISTANCE) {
                        if (CurioHandler.isCurioEquipped(player, MAItems.HeroShield.get())) {
                            effectLevel += CommonConfig.HERO_SHIELD_RES_LEVEL.get();
                        }
                        if (CurioHandler.isCurioEquipped(player, MAItems.ShadowAegis.get())) {
                            effectLevel += CommonConfig.SHADOW_AEGIS_RES_LEVEL.get();
                        }
                    }

                    if (effect == MobEffects.REGENERATION) {
                        if (CurioHandler.isCurioEquipped(player, MAItems.MelodyPlushie.get())) {
                            effectLevel += CommonConfig.PLUSHIE_REGEN_LEVEL.get();
                        }
                    }   
                }

                effectLevel = Math.min(effectLevel, maxLevel);
                MobEffectInstance mobEffect = entity.getEffect(effect);
                if (mobEffect == null || mobEffect.getDuration() <= 110 || mobEffect.getAmplifier() < effectLevel) {
                    entity.addEffect(new MobEffectInstance(effect, 210, effectLevel, true, false));
                }
            }
        }
    }
}