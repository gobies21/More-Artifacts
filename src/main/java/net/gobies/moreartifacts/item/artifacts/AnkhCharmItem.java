package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.util.CurioHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;

public class AnkhCharmItem extends Item implements ICurioItem {
    public AnkhCharmItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.EPIC));
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
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


    @SubscribeEvent
    public void onMobEffectApplicable(MobEffectEvent.Applicable event) {
        if (event.getEntity() instanceof Player player) {
            if (CurioHandler.isCurioEquipped(player, MAItems.AnkhCharm.get())) {
            event.getEffectInstance();
            if (event.getEffectInstance().getEffect() == MobEffects.POISON ||
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
        }
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.ankh_charm").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}



