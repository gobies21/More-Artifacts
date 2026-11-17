package net.gobies.moreartifacts.item.souls;

import com.google.common.collect.Multimap;
import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.event.ClientEvents;
import net.gobies.moreartifacts.helper.SoulAttributes;
import net.gobies.moreartifacts.helper.SoulType;
import net.gobies.moreartifacts.util.MAUtils;
import net.gobies.moreartifacts.util.ModLoadedUtil;
import net.gobies.moreartifacts.util.SoulUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class EnvySoulItem extends SoulItem {
    public EnvySoulItem(Properties properties) {
        super(properties);
    }

    /*

    // Stage 2-3: Cannot unequip in darkness
    // Stage 4: Cannot unequip at all
    // Stage 5: No longer an item
    // Icon in inventory to inform what soul the player has if any
    */

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        return SoulAttributes.getCurioModifiers(uuid, stack, SoulAttributes.ENVY_ATTRIBUTES, SoulType.ENVY);
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

                MobEffectInstance effect = player.getEffect(MobEffects.INVISIBILITY);
                ItemStack source = effectMap.get(MobEffects.INVISIBILITY);

                if (abilityActive) {
                    if (effect == null || source == null || !ItemStack.isSameItemSameTags(source, stack)) {
                        player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 210, 0, true, false));
                        effectMap.put(MobEffects.INVISIBILITY, stack);
                    } else if (effect.getDuration() <= 110) {
                        player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 210, 0, true, false));
                    }
                } else {
                    if (effect != null && source != null && ItemStack.isSameItemSameTags(source, stack)) {
                        player.removeEffect(MobEffects.INVISIBILITY);
                        effectMap.remove(MobEffects.INVISIBILITY);
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
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            UUID playerId = player.getUUID();
            Map<MobEffect, ItemStack> effectMap = appliedEffect.getOrDefault(playerId, new HashMap<>());

            if (effectMap.containsKey(MobEffects.INVISIBILITY)) {
                if (player.hasEffect(MobEffects.INVISIBILITY)) {
                    player.removeEffect(MobEffects.INVISIBILITY);
                }
                effectMap.remove(MobEffects.INVISIBILITY);
            }
            appliedEffect.put(playerId, effectMap);
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
        int currentDamage = nbt.getInt(SoulUtil.SoulDamage);
        double magicConversion = stage * CommonConfig.ENVY_MAGIC_DAMAGE_CONVERSION.get() * 100;
        double magicDamage = stage * CommonConfig.ENVY_MAGIC_DAMAGE.get() * 100;
        double costReduction = CommonConfig.ENVY_SPELLBOOKS_SPELL_COST_REDUCTION.get() * 100;
        MobEffect effect = ForgeRegistries.MOB_EFFECTS.getValue(ResourceLocation.tryParse(CommonConfig.ENVY_EFFECT.get()));

        // Lore Tooltips
        if (stage == 1) {
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.envy_soul.stage1_lore").withStyle(ChatFormatting.DARK_PURPLE).withStyle(ChatFormatting.ITALIC));
        } else if (stage == 2) {
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.envy_soul.stage2_lore").withStyle(ChatFormatting.DARK_PURPLE).withStyle(ChatFormatting.ITALIC));
        } else if (stage == 3) {
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.envy_soul.stage3_lore").withStyle(ChatFormatting.DARK_PURPLE).withStyle(ChatFormatting.ITALIC));
        } else if (stage == 4) {
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.envy_soul.stage4_lore").withStyle(ChatFormatting.DARK_PURPLE).withStyle(ChatFormatting.ITALIC));
        } else if (stage == 5) {
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.envy_soul.stage5_lore").withStyle(ChatFormatting.DARK_PURPLE).withStyle(ChatFormatting.ITALIC));
        }
        // Damage Tooltips
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.envy_soul.magic_damage_conversion", MAUtils.formatDecimal(magicConversion)).withStyle(ChatFormatting.BLUE));
        if (!ModLoadedUtil.isApothecaryLoaded()) {
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.envy_soul.magic_damage", MAUtils.formatDecimal(magicDamage)).withStyle(ChatFormatting.BLUE));
        }
        if (stage >= 3) {
            if (effect != null) {
                pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.envy_soul.effect", effect.getDisplayName()).withStyle(ChatFormatting.LIGHT_PURPLE).append(Component.literal(ClientEvents.SOUL_PASSIVE_KEY.getTranslatedKeyMessage().getString()).withStyle(ChatFormatting.YELLOW)).append(Component.translatable("tooltip.moreartifacts.toggle").withStyle(ChatFormatting.WHITE)));
            }
        }

        if (stage == 5) {
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.soul_mana").withStyle(ChatFormatting.LIGHT_PURPLE));
        }

        // Unequipped Tooltips
        if (stage >= 2 && stage <= 3) {
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.envy_soul.unequip").withStyle(ChatFormatting.GRAY));
        } else if (stage == 4) {
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.soul.no_unequip").withStyle(ChatFormatting.GRAY));
        } else if (stage == 5) {
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.soul_connected").withStyle(ChatFormatting.GRAY));
        }

        // Ability Tooltip
        if (stage == 5) {
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.hold.ctrl"));
            if (Screen.hasControlDown()) {
                pTooltipComponents.remove(stage + 1);
                pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.soul_mana_shield_desc").withStyle(ChatFormatting.LIGHT_PURPLE));
                int manaCost = CommonConfig.ENVY_MANA_SHIELD_COST.get().intValue();
                if (ModLoadedUtil.isIronsSpellbooksLoaded() && CommonConfig.IRONS_SPELLBOOKS_MANA_COMPAT.get()) {
                    manaCost = CommonConfig.ENVY_SPELLBOOKS_MANA_SHIELD_COST.get().intValue();
                }
                pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.envy_soul.flight").withStyle(ChatFormatting.LIGHT_PURPLE));
                pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.soul_mana_shield_ability_toggle").withStyle(ChatFormatting.LIGHT_PURPLE).append(Component.literal(ClientEvents.SOUL_ACTIVE_KEY.getTranslatedKeyMessage().getString()).withStyle(ChatFormatting.YELLOW)).append(Component.translatable("tooltip.moreartifacts.toggle").withStyle(ChatFormatting.WHITE)).append(Component.literal(" (" + manaCost + " SMP)").withStyle(ChatFormatting.DARK_PURPLE)));
                if (ModLoadedUtil.isIronsSpellbooksLoaded() && CommonConfig.IRONS_SPELLBOOKS_MANA_COMPAT.get()) {
                    pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.envy_soul.spell_cost_reduction", MAUtils.formatDecimal(costReduction)).withStyle(ChatFormatting.BLUE));
                }
            }
        }
        // Evolve Tooltips
        if (stage == 1) {
            pTooltipComponents.add(Component.literal(" "));
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.soul.evolve").withStyle(ChatFormatting.GOLD));
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.soul.damage_required", currentDamage, CommonConfig.ENVY_EVOLVE_DAMAGE_1.get().intValue()).withStyle(ChatFormatting.RED));
        } else if (stage == 2) {
            pTooltipComponents.add(Component.literal(" "));
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.soul.evolve").withStyle(ChatFormatting.GOLD));
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.soul.damage_required", currentDamage, CommonConfig.ENVY_EVOLVE_DAMAGE_2.get().intValue()).withStyle(ChatFormatting.RED));
        } else if (stage == 3) {
            pTooltipComponents.add(Component.literal(" "));
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.soul.evolve").withStyle(ChatFormatting.GOLD));
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.soul.damage_required", currentDamage, CommonConfig.ENVY_EVOLVE_DAMAGE_3.get().intValue()).withStyle(ChatFormatting.RED));
        } else if (stage == 4) {
            pTooltipComponents.add(Component.literal(" "));
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.soul.evolve").withStyle(ChatFormatting.GOLD));
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.soul.damage_required", currentDamage, CommonConfig.ENVY_EVOLVE_DAMAGE_4.get().intValue()).withStyle(ChatFormatting.RED));
        }

        if (stage == 5) {
            pTooltipComponents.add(Component.literal(" "));
            pTooltipComponents.add(Component.literal("Immunities:").withStyle(ChatFormatting.GOLD));
            pTooltipComponents.add(Component.literal("- Blindness").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(MobEffects.BLINDNESS.getColor()))));
            pTooltipComponents.add(Component.literal("- Darkness").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(MobEffects.DARKNESS.getColor()))));
            pTooltipComponents.add(Component.literal("- Slowness").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(MobEffects.MOVEMENT_SLOWDOWN.getColor()))));
            if (ModLoadedUtil.isColdSweatLoaded()) pTooltipComponents.add(Component.literal("- Hypothermia").withStyle(ChatFormatting.AQUA));
        }
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}