package net.gobies.moreartifacts.item.artifacts;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class ShackleItem extends Item implements ICurioItem {
    public ShackleItem(Properties properties) {
        super(new Properties().stacksTo(1).rarity(Rarity.COMMON));
    }

    private static final UUID ARMOR_UUID = UUID.randomUUID();

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        if (livingEntity instanceof Player entity) {
            var attribute = entity.getAttribute(Attributes.ARMOR);
            if (attribute != null) {
                if (attribute.getModifier(ARMOR_UUID) == null && stack.getItem() instanceof ShackleItem) {
                    attribute.addTransientModifier(
                            new AttributeModifier(ARMOR_UUID, "Shackle Armor", 1.0, AttributeModifier.Operation.ADDITION));
                }
            }
        }
    }

    public void onUnequip(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        if (livingEntity instanceof Player entity) {
            entity.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ARMOR).removeModifier(ARMOR_UUID);
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("§7+1 Increased armor"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}








