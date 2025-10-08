package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.util.MAUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class NaturesMantleItem extends Item implements ICurioItem {
    private int standStillCounter = 0;
    private int randomDuration = 0;
    private static final UUID SPEED = UUID.randomUUID();

    public NaturesMantleItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.UNCOMMON));
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            Level level = player.level();
            if (level.isDay()) {
                if (!player.walkAnimation.isMoving() && !player.isSprinting()) {
                    if (standStillCounter == 0) {
                        standStillCounter = 1;
                        randomDuration = level.random.nextInt(400) + 400;
                    } else {
                        standStillCounter++;
                        if (standStillCounter >= randomDuration && !player.hasEffect(MobEffects.REGENERATION)) {
                            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 125, 0, false, false, false));
                            standStillCounter = 0;
                        }
                    }
                } else {
                    standStillCounter = 0;
                    if (player.hasEffect(MobEffects.REGENERATION)) {
                        player.removeEffect(MobEffects.REGENERATION);
                    }
                }
            } else {
                standStillCounter = 0;
                if (player.hasEffect(MobEffects.REGENERATION)) {
                    player.removeEffect(MobEffects.REGENERATION);
                }
            }
        }
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
        MAUtils.addAttributes(player, Attributes.MOVEMENT_SPEED, CommonConfig.NATURES_MANTLE_SPEED_INCREASE.get(), AttributeModifier.Operation.MULTIPLY_BASE, String.valueOf(SPEED));
}
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            MAUtils.removeAttributes(player, Attributes.MOVEMENT_SPEED, String.valueOf(SPEED));
            if (player.hasEffect(MobEffects.REGENERATION)) {
                player.removeEffect(MobEffects.REGENERATION);
            }
        }
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        double speedIncrease = CommonConfig.NATURES_MANTLE_SPEED_INCREASE.get() * 100;
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.natures_mantle.regeneration").withStyle(ChatFormatting.GRAY));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.natures_mantle.day").withStyle(ChatFormatting.GRAY));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.natures_mantle.speed", String.format("%.1f", speedIncrease)).withStyle(ChatFormatting.DARK_AQUA));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}