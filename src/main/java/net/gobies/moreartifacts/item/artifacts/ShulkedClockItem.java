package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.util.CurioHandler;
import net.gobies.moreartifacts.init.MAItems;
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

public class ShulkedClockItem extends Item implements ICurioItem {
    public ShulkedClockItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.RARE));
        MinecraftForge.EVENT_BUS.register(this);
    }
    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            if (player.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) {
                player.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
            }
            if (player.hasEffect(MobEffects.LEVITATION)) {
                player.removeEffect(MobEffects.LEVITATION);
            }
        }
    }
    @SubscribeEvent
    public void onMobEffectApplicable(MobEffectEvent.Applicable event) {
        if (event.getEntity() instanceof Player player) {
            event.getEffectInstance();
            if (event.getEffectInstance().getEffect() == MobEffects.MOVEMENT_SLOWDOWN || event.getEffectInstance().getEffect() == MobEffects.LEVITATION) {
                if (CurioHandler.isCurioEquipped(player, MAItems.ShulkedClock.get())) {
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
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.shulked_clock").withStyle(ChatFormatting.GRAY));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}








