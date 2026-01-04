package net.gobies.moreartifacts.mixin;

import net.gobies.moreartifacts.init.MAItems;
import net.gobies.potionrings2.init.PRItems;
import net.gobies.potionrings2.item.potionrings.PotionRingItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

import java.util.stream.IntStream;

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
                if (effect == MobEffects.DAMAGE_RESISTANCE) {
                    int ringCount = moreArtifacts$getEquippedRingCount(entity, PRItems.PotionRing.get(), MAItems.HeroShield.get(), effectIdString);
                    int effectLevel = Math.min(ringCount - 1, 2);
                    entity.addEffect(new MobEffectInstance(effect, -1, effectLevel, true, false));
                } else if (effect == MobEffects.REGENERATION) {
                    int ringCount = moreArtifacts$getEquippedRingCount(entity, PRItems.PotionRing.get(), MAItems.MelodyPlushie.get(), effectIdString);
                    int effectLevel = Math.min(ringCount - 1, 2);
                    entity.addEffect(new MobEffectInstance(effect, -1, effectLevel, true, false));
                }
            }
        }
    }

    @Unique
    private static int moreArtifacts$getEquippedRingCount(LivingEntity entity, @NotNull Item targetItem, @Nullable Item optionalItem, String effectIdString) {
        return CuriosApi.getCuriosInventory(entity).resolve().map(handler -> (int) handler.getCurios().values().stream()
                .flatMap(curio -> {
                    int slots = curio.getSlots();
                    return IntStream.range(0, slots).mapToObj(slotIndex -> curio.getStacks().getStackInSlot(slotIndex));
                }).filter(itemStack -> {
                    Item stackItem = itemStack.getItem();
                    if (stackItem == targetItem && itemStack.getTag() != null) {
                        return itemStack.hasTag() && effectIdString.equals(itemStack.getTag().getString("Effect"));
                    }
                    return optionalItem != null && stackItem == optionalItem;
                }).count()).orElse(0);
    }
}