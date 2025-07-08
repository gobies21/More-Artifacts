package net.gobies.moreartifacts.item.artifacts;
import net.gobies.moreartifacts.Config;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.util.MAUtils;
import net.gobies.moreartifacts.util.ShieldHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class CobaltShieldItem extends ShieldItem implements ICurioItem {
    public CobaltShieldItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.UNCOMMON).durability(500));
        MinecraftForge.EVENT_BUS.register(this);
    }

    static {
        MinecraftForge.EVENT_BUS.register(CobaltShieldItem.class);
    }

    private static final UUID KNOCKBACK_RESISTANCE = UUID.randomUUID();

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            MAUtils.addAttributes(player, Attributes.KNOCKBACK_RESISTANCE, 1.0, AttributeModifier.Operation.ADDITION, String.valueOf(KNOCKBACK_RESISTANCE));

        }
    }

    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            MAUtils.removeAttributes(player, Attributes.KNOCKBACK_RESISTANCE, String.valueOf(KNOCKBACK_RESISTANCE));
        }
    }

    @SubscribeEvent
    public static void onLivingKnockBack(LivingKnockBackEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity instanceof Player player) {
            if (ShieldHandler.isShieldEquipped(player, MAItems.CobaltShield.get())) {
                event.setCanceled(true);
            }
        }
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, @NotNull ItemStack repair) {
        return toRepair.getItem() == this && repair.getItem() == Items.IRON_INGOT;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.cobalt_shield").withStyle(ChatFormatting.GRAY));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.hold.ctrl").withStyle(ChatFormatting.GRAY));
        if (Screen.hasControlDown()) {
            pTooltipComponents.remove(2);
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.normal.shield").withStyle(ChatFormatting.GRAY));
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.shield.iron").withStyle(ChatFormatting.GRAY));
            super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        }
    }
}