package net.gobies.moreartifacts.item.souls;

import com.google.common.collect.Multimap;
import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.event.ClientEvents;
import net.gobies.moreartifacts.helper.SoulAttributes;
import net.gobies.moreartifacts.helper.SoulType;
import net.gobies.moreartifacts.util.SoulUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class WrathSoulItem extends SoulItem {
    public WrathSoulItem(Properties properties) {
        super(properties);
    }

    /*

    // Upgrades by have it equip and killing mobs under certain conditions/etc
    TODO: Stage 1 = Attacks have a chance to heal for half a heart
    TODO: Stage 2 = Attacks have a chance to heal for a full heart
    TODO: Stage 3 = Attacks heal for 10% of damage dealt
    TODO: Stage 4 = Attacks heal for 15% of damage dealt + 1 Heart
    TODO: Stage 5 = Attacks heal for 20% of damage dealt + 2 Hearts (scale +10%)


    // Stage 2-3: Cannot unequip while at full health
    // Stage 4: Cannot unequip at all
    // Stage 5: No longer an item
    // Icon in inventory to inform what soul the player has if any
    */

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        return SoulAttributes.getCurioModifiers(uuid, stack, SoulAttributes.WRATH_ATTRIBUTES, "curio_wrath:");
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity().level().isClientSide()) return;

        if (slotContext.entity() instanceof Player player) {
            int stage = SoulUtil.getSoulStage(stack);
            if (stage >= 3) {
                String soulType = SoulUtil.getSoul(stack.getItem());
                if (SoulType.UNKNOWN.key.equals(soulType)) return;

                UUID playerId = player.getUUID();
                appliedEffect.putIfAbsent(playerId, new HashMap<>());
                Map<MobEffect, ItemStack> effectMap = appliedEffect.get(playerId);

                CompoundTag tag = stack.getOrCreateTag();

                boolean keyPressed = enabled(stack, soulType);

                if (tag.getBoolean(soulType) != keyPressed) {
                    tag.putBoolean(soulType, keyPressed);
                }

                BlockPos pos = player.blockPosition();
                int lightLevel = player.level().getMaxLocalRawBrightness(pos);
                boolean isDarkArea = lightLevel <= 7;

                boolean abilityRequested = tag.getBoolean(soulType);
                boolean abilityActive = abilityRequested && isDarkArea;

                MobEffectInstance effect = player.getEffect(MobEffects.DAMAGE_BOOST);
                ItemStack source = effectMap.get(MobEffects.DAMAGE_BOOST);

                if (abilityActive) {
                    if (effect == null || source == null || !ItemStack.isSameItemSameTags(source, stack)) {
                        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 210, 0, true, false));
                        effectMap.put(MobEffects.DAMAGE_BOOST, stack);
                    } else if (effect.getDuration() <= 110) {
                        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 210, 0, true, false));
                    }
                } else {
                    if (effect != null && source != null && ItemStack.isSameItemSameTags(source, stack)) {
                        player.removeEffect(MobEffects.DAMAGE_BOOST);
                        effectMap.remove(MobEffects.DAMAGE_BOOST);
                    }
                }

                appliedEffect.put(playerId, effectMap);
            }
        }
    }

    private static boolean enabled(ItemStack stack, String soulType) {
        return stack.getOrCreateTag().getBoolean(soulType);
    }

    @Override
    public boolean canUnequip(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            if (player.isCreative()) return true;
            int stage = SoulUtil.getSoulStage(stack);
            if (stage >= 2 && stage <= 3) {
                if (player.getHealth() < player.getMaxHealth()) {
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
        int currentDamage = nbt.getInt(SoulUtil.SoulDamage);

        // Lore Tooltips
        if (stage == 1) {
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.blood_soul.stage1_lore").withStyle(ChatFormatting.DARK_RED).withStyle(ChatFormatting.ITALIC));
        } else if (stage == 2) {
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.blood_soul.stage2_lore").withStyle(ChatFormatting.DARK_RED).withStyle(ChatFormatting.ITALIC));
        } else if (stage == 3) {
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.blood_soul.stage3_lore").withStyle(ChatFormatting.DARK_RED).withStyle(ChatFormatting.ITALIC));
        } else if (stage == 4) {
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.blood_soul.stage4_lore").withStyle(ChatFormatting.DARK_RED).withStyle(ChatFormatting.ITALIC));
        } else if (stage == 5) {
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.blood_soul.stage5_lore").withStyle(ChatFormatting.DARK_RED).withStyle(ChatFormatting.ITALIC));
        }

        if (stage >= 3) {
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.wrath_soul.effect").withStyle(ChatFormatting.RED).append(Component.literal(ClientEvents.SOUL_PASSIVE_KEY.getTranslatedKeyMessage().getString()).withStyle(ChatFormatting.YELLOW)).append(Component.translatable("tooltip.moreartifacts.toggle").withStyle(ChatFormatting.WHITE)));
        }

        if (stage == 4) {
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.blood_soul.blood_rage1").withStyle(ChatFormatting.RED));
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.blood_soul.blood_rage_stack").withStyle(ChatFormatting.RED));
        } else if (stage == 5) {
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.blood_soul.blood_rage2").withStyle(ChatFormatting.RED));
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.blood_soul.blood_rage_stack").withStyle(ChatFormatting.RED));
        }

        // Unequipped Tooltips
        if (stage >= 2 && stage <= 3) {
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.blood_soul.unequip").withStyle(ChatFormatting.GRAY));
        } else if (stage == 4) {
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.soul.no_unequip").withStyle(ChatFormatting.GRAY));
        } else if (stage == 5) {
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.soul_connected").withStyle(ChatFormatting.GRAY));
        }


        // Evolve Tooltips
        if (stage == 1) {
            pTooltipComponents.add(Component.literal(" "));
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.soul.evolve").withStyle(ChatFormatting.GOLD));
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.soul.damage_required", currentDamage, CommonConfig.WRATH_EVOLVE_DAMAGE_1.get().intValue()).withStyle(ChatFormatting.RED));
        } else if (stage == 2) {
            pTooltipComponents.add(Component.literal(" "));
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.soul.evolve").withStyle(ChatFormatting.GOLD));
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.soul.damage_required", currentDamage, CommonConfig.WRATH_EVOLVE_DAMAGE_2.get().intValue()).withStyle(ChatFormatting.RED));
        } else if (stage == 3) {
            pTooltipComponents.add(Component.literal(" "));
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.soul.evolve").withStyle(ChatFormatting.GOLD));
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.soul.damage_required", currentDamage, CommonConfig.WRATH_EVOLVE_DAMAGE_3.get().intValue()).withStyle(ChatFormatting.RED));
        } else if (stage == 4) {
            pTooltipComponents.add(Component.literal(" "));
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.soul.evolve").withStyle(ChatFormatting.GOLD));
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.soul.damage_required", currentDamage, CommonConfig.WRATH_EVOLVE_DAMAGE_4.get().intValue()).withStyle(ChatFormatting.RED));
        }

        // Immunity Tooltips
        if (stage == 5) {
            pTooltipComponents.add(Component.literal(" "));
            pTooltipComponents.add(Component.literal("Immunities:").withStyle(ChatFormatting.GOLD));
            pTooltipComponents.add(Component.literal("- §cWeakness").withStyle(ChatFormatting.RED));
            pTooltipComponents.add(Component.literal("- §cMining Fatigue").withStyle(ChatFormatting.RED));
        }
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}









