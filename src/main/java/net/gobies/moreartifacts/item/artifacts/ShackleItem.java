package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.Config;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
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
import java.util.Objects;
import java.util.UUID;

public class ShackleItem extends Item implements ICurioItem {
    public ShackleItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.COMMON));
    }

    private static final UUID ARMOR_UUID = UUID.randomUUID();

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            var attribute = player.getAttribute(Attributes.ARMOR);
            if (attribute != null) {
                if (attribute.getModifier(ARMOR_UUID) == null && stack.getItem() instanceof ShackleItem) {
                    attribute.addTransientModifier(
                            new AttributeModifier(ARMOR_UUID, "Shackle Armor", Config.SHACKLE_ARMOR.get(), AttributeModifier.Operation.ADDITION));
                }
            }
        }
    }

    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            Objects.requireNonNull(player.getAttribute(Attributes.ARMOR)).removeModifier(ARMOR_UUID);
        }
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        int shackleArmor = Config.SHACKLE_ARMOR.get();
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.shackle.armor", shackleArmor).withStyle(ChatFormatting.DARK_AQUA));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}