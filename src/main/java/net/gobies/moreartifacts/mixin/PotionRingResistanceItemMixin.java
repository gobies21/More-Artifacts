package net.gobies.moreartifacts.mixin;

import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.util.CurioHandler;
import net.gobies.potionrings2.item.ModItems;
import net.gobies.potionrings2.item.potionrings.PotionRingResistanceItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import top.theillusivec4.curios.api.SlotContext;

@Mixin(PotionRingResistanceItem.class)
public abstract class PotionRingResistanceItemMixin {
    /**
     * @author gobies
     * @reason let moreartifacts handle resistance ring event for better compatibility
     */
    @Overwrite(remap = false)
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        int ringCount = CurioHandler.getEquippedCuriosCount(entity, ModItems.PotionRingResistance.get(), MAItems.HeroShield.get());

        int effectLevel = Math.min(ringCount - 1, 2);
        entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, -1, effectLevel, true, false));
    }

    /**
     * @author gobies
     * @reason let moreartifacts handle resistance ring event for better compatibility
     */
    @Overwrite(remap = false)
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        int ringCount = CurioHandler.getEquippedCuriosCount(entity, ModItems.PotionRingResistance.get(), MAItems.HeroShield.get());

        if (ringCount > 0) {
            int effectLevel = Math.min(ringCount - 1, 2);
            entity.removeEffect(MobEffects.DAMAGE_RESISTANCE);
            entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, -1, effectLevel, true, false));
        } else {
            entity.removeEffect(MobEffects.DAMAGE_RESISTANCE);
        }
    }
}