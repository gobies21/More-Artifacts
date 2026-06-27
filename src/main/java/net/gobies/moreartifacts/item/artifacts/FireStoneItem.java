package net.gobies.moreartifacts.item.artifacts;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.gobies.moreartifacts.compat.coldsweat.ColdSweatCompat;
import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.util.ModLoadedUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
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

public class FireStoneItem extends Item implements ICurioItem {
    public FireStoneItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.RARE).fireResistant());
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            if (player.isOnFire()) {
                player.extinguishFire();
            }
        }
    }

    private static final UUID HEAT_TOLERANCE = UUID.fromString("cd93e1bb-c47e-47e7-a913-2ae6a865a765");

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = LinkedHashMultimap.create();
        if (ModLoadedUtil.isColdSweatLoaded()) {
            modifiers.put(ColdSweatCompat.heatToleranceAttribute(), new AttributeModifier(uuid, String.valueOf(HEAT_TOLERANCE), 0.10, AttributeModifier.Operation.ADDITION));
        }
        return modifiers;
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        Style customOrange = Style.EMPTY.withColor(TextColor.fromRgb(0xF57314));
        double fireChance = CommonConfig.FIRE_STONE_CHANCE.get() * 100;
        int fireDuration = CommonConfig.FIRE_STONE_DURATION.get();
        double increasedDamage = (CommonConfig.FIRE_STONE_DAMAGE.get() - 1) * 100;
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.fire_stone.immunity").withStyle(customOrange));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.fire_stone.inflame", Component.literal("+" + String.format("%.1f", fireChance) + "%").withStyle(ChatFormatting.DARK_AQUA), Component.literal(String.valueOf(fireDuration)).withStyle(ChatFormatting.DARK_AQUA)).withStyle(customOrange));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.fire_stone.increased_damage", Component.literal("+" + String.format("%.1f", increasedDamage) + "%").withStyle(ChatFormatting.DARK_AQUA)).withStyle(customOrange));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.hold.ctrl"));
        if (Screen.hasControlDown()) {
            pTooltipComponents.remove(4);
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.fire_stone_ignite").withStyle(customOrange));
        }
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}


