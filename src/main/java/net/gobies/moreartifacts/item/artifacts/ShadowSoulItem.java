package net.gobies.moreartifacts.item.artifacts;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.gobies.moreartifacts.compat.betterstealth.BetterStealthCompat;
import net.gobies.moreartifacts.event.ClientEvents;
import net.gobies.moreartifacts.util.MAUtils;
import net.gobies.moreartifacts.util.ModLoadedUtil;
import net.gobies.moreartifacts.util.SoulUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ShadowSoulItem extends SoulStoneItem {
    public ShadowSoulItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    /*

    // Upgrades by have it equip and killing mobs under certain conditions/etc
    TODO: Stage 1 = +10% Damage in dark areas, -5% Armor, gain more stealth in darkness
    TODO: Stage 2 = +20% Damage in dark areas, -10% Armor, gain even more stealth in darkness, increases speed/vision in dark areas
    TODO: Stage 3 = +30% Damage in dark areas, -15% Armor, gain even more stealth in darkness!, increases speed/vision in dark areas, gain an active ability to turn invisible in dark areas
    TODO: Stage 4 = +40% Damage in dark areas, (same other abilities as stage 3) + 20% Chance to summon shadow hands to hit targets when attacking, shadow hands will do 25% of your damage
    TODO: Stage 5 = +50% Damage in dark areas, (same other abilities as stage 3) + Shadow hands become permanent and can be toggled into different states/stances, they will still do 25% of your damage
     for weaker enemies with a specified max health will be crushed and instant killed by shadow hands (players excluded). The soul becomes entangled with yours removing the item from your inventory and becoming permanent (a way to remove souls will be added)


    // Stage 2-3: Cannot unequip in darkness
    // Stage 4: Cannot unequip at all
    // Stage 5: No longer an item
    // Icon in inventory to inform what soul the player has if any
    */

    private static final UUID SPEED = UUID.randomUUID();
    private static final UUID ARMOR = UUID.randomUUID();
    private static final UUID STEALTH = UUID.randomUUID();
    private static final Map<UUID, Map<MobEffect, ItemStack>> appliedInvisibility = new HashMap<>();

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = LinkedHashMultimap.create();
        int currentSoulStage = SoulUtil.getSoulStage(stack);
        double speedBonus = 0.15 + (currentSoulStage * 0.05);
        double armorPenalty = 0.05 + (currentSoulStage * 0.05);
        modifiers.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(uuid, String.valueOf(SPEED), speedBonus, AttributeModifier.Operation.MULTIPLY_BASE));
        modifiers.put(Attributes.ARMOR, new AttributeModifier(uuid, String.valueOf(ARMOR), -armorPenalty, AttributeModifier.Operation.MULTIPLY_BASE));

        return modifiers;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity().level().isClientSide()) {
            return;
        }

        if (slotContext.entity() instanceof Player player) {
            int stage = SoulUtil.getSoulStage(stack);
            if (stage >= 3) {
                UUID playerId = player.getUUID();
                appliedInvisibility.putIfAbsent(playerId, new HashMap<>());
                Map<MobEffect, ItemStack> effectMap = appliedInvisibility.get(playerId);

                CompoundTag tag = stack.getOrCreateTag();
                boolean keyPressed = enabled(stack);

                if (tag.getBoolean("Invisibility") != keyPressed) {
                    tag.putBoolean("Invisibility", keyPressed);
                }

                BlockPos pos = player.blockPosition();
                int lightLevel = player.level().getMaxLocalRawBrightness(pos);
                boolean isDarkArea = lightLevel <= 7;

                boolean invisibilityRequested = tag.getBoolean("Invisibility");

                boolean invisibilityActive = invisibilityRequested && isDarkArea;

                MobEffectInstance effect = player.getEffect(MobEffects.INVISIBILITY);
                ItemStack source = effectMap.get(MobEffects.INVISIBILITY);

                if (invisibilityActive) {
                    if (effect == null || source == null || !ItemStack.isSameItemSameTags(source, stack)) {
                        player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, -1, 0, true, false));
                        effectMap.put(MobEffects.INVISIBILITY, stack);
                    }
                } else {
                    if (effect != null && source != null && ItemStack.isSameItemSameTags(source, stack)) {
                        player.removeEffect(MobEffects.INVISIBILITY);
                        effectMap.remove(MobEffects.INVISIBILITY);
                    }
                }

                appliedInvisibility.put(playerId, effectMap);

                if (ModLoadedUtil.isBetterStealthLoaded()) {
                    updateStealthModifier(player, stack, isDarkArea);
                }
            }
        }
    }

    private static boolean enabled(ItemStack stack) {
        return stack.getOrCreateTag().getBoolean("Invisibility");
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            UUID playerId = player.getUUID();
            Map<MobEffect, ItemStack> effectMap = appliedInvisibility.getOrDefault(playerId, new HashMap<>());

            if (effectMap.containsKey(MobEffects.INVISIBILITY)) {
                if (player.hasEffect(MobEffects.INVISIBILITY)) {
                    player.removeEffect(MobEffects.INVISIBILITY);
                }
                effectMap.remove(MobEffects.INVISIBILITY);
            }
            appliedInvisibility.put(playerId, effectMap);
            MAUtils.removeAttributes(player, BetterStealthCompat.stealthAttribute(), String.valueOf(STEALTH));
        }
    }

    private void updateStealthModifier(Player player, ItemStack stack, boolean isDarkArea) {
        int level = SoulUtil.getSoulStage(stack);
        double value = level * 0.1;
        if (isDarkArea) {
            MAUtils.addAttributes(player, BetterStealthCompat.stealthAttribute(), value, AttributeModifier.Operation.ADDITION, String.valueOf(STEALTH));
        } else {
            MAUtils.removeAttributes(player, BetterStealthCompat.stealthAttribute(), String.valueOf(STEALTH));
        }
    }

    @Override
    public boolean canUnequip(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            if (player.isCreative()) return true;
            int stage = SoulUtil.getSoulStage(stack);
            if (stage >= 2 && stage <= 3) {
                BlockPos pos = player.blockPosition();
                int lightLevel = player.level().getMaxLocalRawBrightness(pos);
                if (lightLevel <= 7) {
                    return false;
                }
            } else if (stage == 4) {
                return false;
            }
        }

        return super.canUnequip(slotContext, stack);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        int stage = SoulUtil.getSoulStage(pStack);
        CompoundTag nbt = pStack.getOrCreateTag();
        int requiredKills = SoulUtil.REQUIRED_KILLS;
        int currentKills = nbt.getInt(SoulUtil.KILLS);
        double damageDealt = ((1.00 - 1) + (stage * 0.1)) * 100;
        double shadowHandChance = (0.25) * 100;
        // Lore Tooltips
        if (stage == 1) {
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.shadow_soul.stage1_lore").withStyle(ChatFormatting.GOLD));
        } else if (stage == 2) {
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.shadow_soul.stage2_lore").withStyle(ChatFormatting.GOLD));
        } else if (stage == 3) {
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.shadow_soul.stage3_lore").withStyle(ChatFormatting.GOLD));
        } else if (stage == 4) {
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.shadow_soul.stage4_lore").withStyle(ChatFormatting.GOLD));
        } else if (stage == 5) {
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.shadow_soul.stage5_lore").withStyle(ChatFormatting.GOLD));
        }
        // Damage Tooltips
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.shadow_soul.damage_dealt", String.format("%.1f", damageDealt)).withStyle(ChatFormatting.DARK_AQUA));
        if (stage >= 3) {
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.shadow_soul.invis").withStyle(ChatFormatting.GOLD).append(Component.literal(ClientEvents.SOUL_PASSIVE_KEY.getTranslatedKeyMessage().getString()).withStyle(ChatFormatting.YELLOW)).append(Component.translatable("tooltip.moreartifacts.toggle").withStyle(ChatFormatting.WHITE)));
        }

        // Shadow Hands Tooltips
        if (stage == 4) {
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.shadow_soul.shadow_hand_chance", String.format("%.1f", shadowHandChance)).withStyle(ChatFormatting.DARK_AQUA));
        } else if (stage == 5) {
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.shadow_soul.shadow_hands").withStyle(ChatFormatting.GOLD));
        }

        // Unequipped Tooltips
        if (stage >= 2 && stage <= 3) {
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.shadow_soul.unequip").withStyle(ChatFormatting.GOLD));
        } else if (stage == 4) {
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.shadow_soul.no_unequip").withStyle(ChatFormatting.GOLD));
        } else if (stage == 5) {
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.soul_connected").withStyle(ChatFormatting.GOLD));
        }

        // Compat Tooltips
        if (ModLoadedUtil.isBetterStealthLoaded()) {
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.hold.ctrl"));
            if (Screen.hasControlDown()) {
                pTooltipComponents.remove(stage + 2);

                pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.shadow_soul.stealth").withStyle(ChatFormatting.GRAY));
            }
        }
        if (stage == 1) {
            pTooltipComponents.add(Component.literal(" "));
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.shadow_soul.evolve_1").withStyle(ChatFormatting.GOLD));
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.shadow_soul.kills_required", currentKills, requiredKills).withStyle(ChatFormatting.RED));
        }
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}









