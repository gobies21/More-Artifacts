package net.gobies.moreartifacts.item.artifacts;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class CobaltShieldItem extends ShieldItem implements ICurioItem {
    public CobaltShieldItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.UNCOMMON));
    }

    private static final UUID KNOCKBACK_RESISTANCE_UUID = UUID.randomUUID();

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        if (livingEntity instanceof Player entity) {
            var attribute = entity.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.KNOCKBACK_RESISTANCE);
            if (attribute != null) {
                if (attribute.getModifier(KNOCKBACK_RESISTANCE_UUID) == null && stack.getItem() instanceof CobaltShieldItem) {
                    attribute.addTransientModifier(
                            new AttributeModifier(KNOCKBACK_RESISTANCE_UUID, "Cobalt Shield knockback immunity", 1000.0, AttributeModifier.Operation.ADDITION));
                }
            }
        }
    }

    public void onUnequip(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        if (livingEntity instanceof Player entity) {
            Objects.requireNonNull(entity.getAttribute(Attributes.KNOCKBACK_RESISTANCE)).removeModifier(KNOCKBACK_RESISTANCE_UUID);
        }
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("§7Grants immunity to Knockback"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}








