package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.Config;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.util.CurioHandler;
import net.gobies.moreartifacts.util.DamageManager;
import net.gobies.moreartifacts.util.ShieldHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class AnkhShieldItem extends ShieldItem implements ICurioItem {
    public AnkhShieldItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.EPIC).fireResistant().durability(1500));
        MinecraftForge.EVENT_BUS.register(this);
    }

    static {
        MinecraftForge.EVENT_BUS.register(AnkhShieldItem.class);
    }

    private static final UUID KNOCKBACK_RESISTANCE_UUID = UUID.randomUUID();

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            if (CurioHandler.isCurioEquipped(player, MAItems.AnkhShield.get())) {
                removeEffects(player);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Player player = event.player;
            if (ShieldHandler.isShieldEquipped(player, MAItems.AnkhShield.get())) {
                removeEffects(player);
            }
        }
    }

    public static void removeEffects(Player player) {
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

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            var attribute = player.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
            if (attribute != null) {
                if (attribute.getModifier(KNOCKBACK_RESISTANCE_UUID) == null) {
                    attribute.addTransientModifier(
                            new AttributeModifier(KNOCKBACK_RESISTANCE_UUID, "Ankh Shield knockback immunity", 1.0, AttributeModifier.Operation.ADDITION));
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
    public void onMobEffectApplicable(MobEffectEvent.Applicable event) {
        if (event.getEntity() instanceof Player player) {
            if (CurioHandler.isCurioEquipped(player, MAItems.AnkhShield.get()) || ShieldHandler.isShieldEquipped(player, MAItems.AnkhShield.get())) {
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

    @SubscribeEvent
    public static void onLivingKnockBack(LivingKnockBackEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity instanceof Player player) {
            if (ShieldHandler.isShieldEquipped(player, MAItems.AnkhShield.get())) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (CurioHandler.isCurioEquipped(player, MAItems.AnkhShield.get()) || ShieldHandler.isShieldEquipped(player, MAItems.AnkhShield.get())) {
                if (event.getSource().is(DamageTypes.ON_FIRE) || event.getSource().is(DamageTypes.IN_FIRE)) {
                    DamageManager.updateDamageReduction(player, event);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEntityAttacked(LivingAttackEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (CurioHandler.isCurioEquipped(player, MAItems.AnkhShield.get()) || ShieldHandler.isShieldEquipped(player, MAItems.AnkhShield.get())) {
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
        double fireDamageReduction = (1.0 - Config.ANKH_SHIELD_FIRE_DAMAGE_TAKEN.get()) * 100;
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.ankh_shield.immunity").withStyle(ChatFormatting.GOLD));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.ankh_shield.immunity_debuff").withStyle(ChatFormatting.GOLD));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.ankh_shield.fire_damage", String.format("%.1f", fireDamageReduction)).withStyle(ChatFormatting.DARK_AQUA));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.hold.ctrl").withStyle(ChatFormatting.GRAY));
        if (Screen.hasControlDown()) {
            pTooltipComponents.remove(4);
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.normal.shield").withStyle(ChatFormatting.GRAY));
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.shield.obsidian").withStyle(ChatFormatting.GRAY));
            super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        }
    }
}