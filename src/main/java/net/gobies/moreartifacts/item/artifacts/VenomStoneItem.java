package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.util.MAUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.effect.MobEffects;
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

public class VenomStoneItem extends Item implements ICurioItem {
    public VenomStoneItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.RARE));
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            MAUtils.removeEffect(player, MobEffects.POISON);
        }
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        Style poisonColor = Style.EMPTY.withColor(TextColor.fromRgb(MobEffects.POISON.getColor()));
        double poisonChance = CommonConfig.VENOM_STONE_CHANCE.get() * 100;
        int poisonLevel = CommonConfig.VENOM_STONE_LEVEL.get();
        int poisonDuration = CommonConfig.VENOM_STONE_DURATION.get();
        double increasedDamage = (CommonConfig.VENOM_STONE_DAMAGE.get() - 1) * 100;
        double deadlyChance = CommonConfig.VENOM_STONE_DEADLY_CHANCE.get() * 100;
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.venom_stone.immunity").withStyle(poisonColor));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.venom_stone.inflict_poison", Component.literal("+" + MAUtils.formatDecimal(poisonChance) + "%").withStyle(ChatFormatting.BLUE), Component.literal(String.valueOf(poisonLevel)).withStyle(ChatFormatting.BLUE), Component.literal(String.valueOf(poisonDuration)).withStyle(ChatFormatting.BLUE)).withStyle(poisonColor));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.venom_stone.increased_damage", Component.literal("+" + MAUtils.formatDecimal(increasedDamage) + "%").withStyle(ChatFormatting.BLUE)).withStyle(poisonColor));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.hold.ctrl"));
        if (Screen.hasControlDown()) {
            pTooltipComponents.remove(4);
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.venom_stone.deadly.chance", Component.literal("+" + MAUtils.formatDecimal(deadlyChance) + "%").withStyle(ChatFormatting.BLUE)).withStyle(poisonColor));
        }
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}









