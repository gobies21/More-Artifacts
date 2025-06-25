package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.Config;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.util.CurioHandler;
import net.gobies.moreartifacts.util.ShieldHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static java.util.UUID.randomUUID;

public class ObsidianShieldItem extends ShieldItem implements ICurioItem {
    public ObsidianShieldItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.RARE).fireResistant().durability(1000));
        MinecraftForge.EVENT_BUS.register(this);
    }

    private static final UUID KNOCKBACK_RESISTANCE_UUID = randomUUID();

    static {
        MinecraftForge.EVENT_BUS.register(ObsidianShieldItem.class);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            var attribute = player.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.KNOCKBACK_RESISTANCE);
            if (attribute != null) {
                if (attribute.getModifier(KNOCKBACK_RESISTANCE_UUID) == null) {
                    attribute.addTransientModifier(
                            new AttributeModifier(KNOCKBACK_RESISTANCE_UUID, "Obsidian Shield knockback immunity", 1.0, AttributeModifier.Operation.ADDITION));
                }
            }
        }
    }

    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            Objects.requireNonNull(player.getAttribute(Attributes.KNOCKBACK_RESISTANCE)).removeModifier(KNOCKBACK_RESISTANCE_UUID);
        }
    }

    @SubscribeEvent
    public static void onLivingKnockBack(LivingKnockBackEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity instanceof Player player) {
            if (ShieldHandler.isShieldEquipped(player, MAItems.ObsidianShield.get())) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (CurioHandler.isCurioEquipped(player, MAItems.ObsidianShield.get()) || ShieldHandler.isShieldEquipped(player, MAItems.ObsidianShield.get())) {
                if (event.getSource().is(DamageTypes.ON_FIRE) || event.getSource().is(DamageTypes.IN_FIRE)) {
                    event.setAmount((float) (event.getAmount() * Config.OBSIDIAN_SHIELD_FIRE_DAMAGE_TAKEN.get()));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEntityAttacked(LivingAttackEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (CurioHandler.isCurioEquipped(player, MAItems.ObsidianShield.get()) || ShieldHandler.isShieldEquipped(player, MAItems.ObsidianShield.get())) {
                if (event.getSource().is(DamageTypes.HOT_FLOOR)) {
                    event.setCanceled(true);
                }
            }
        }
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, @NotNull ItemStack repair) {
        return toRepair.getItem() == this && repair.getItem() == Items.OBSIDIAN;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        double fireDamageReduction = (1.0 - Config.OBSIDIAN_SHIELD_FIRE_DAMAGE_TAKEN.get()) * 100;
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.obsidian_shield.immunity").withStyle(ChatFormatting.GRAY));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.obsidian_shield.fire_damage", String.format("%.1f", fireDamageReduction)).withStyle(ChatFormatting.DARK_AQUA));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.hold.ctrl"));
        if (Screen.hasControlDown()) {
            pTooltipComponents.remove(3);
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.normal.shield").withStyle(ChatFormatting.GRAY));
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.shield.obsidian").withStyle(ChatFormatting.GRAY));
            super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        }
    }
}