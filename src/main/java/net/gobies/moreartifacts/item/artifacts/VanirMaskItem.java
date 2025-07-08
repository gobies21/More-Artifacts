package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.Config;
import net.gobies.moreartifacts.util.CurioHandler;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.util.MAUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class VanirMaskItem extends Item implements ICurioItem {
    private static final UUID ARMOR = UUID.randomUUID();
    private static final UUID ARMOR_TOUGHNESS = UUID.randomUUID();
    private static final UUID MAX_HEALTH = UUID.randomUUID();
    private static final UUID MOVEMENT_SPEED = UUID.randomUUID();

    public VanirMaskItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.EPIC));
    }

    static {
        MinecraftForge.EVENT_BUS.register(VanirMaskItem.class);
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            if (CurioHandler.isCurioEquipped(player, MAItems.VanirMask.get())) {
                event.setAmount((float) (event.getAmount() + Config.VANIR_MASK_DAMAGE_INCREASE.get()));
            }
        }
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            MAUtils.addAttributes(player, Attributes.ARMOR, Config.VANIR_MASK_ARMOR_INCREASE.get(), AttributeModifier.Operation.ADDITION, String.valueOf(ARMOR));
            MAUtils.addAttributes(player, Attributes.ARMOR_TOUGHNESS, Config.VANIR_MASK_ARMOR_TOUGHNESS_INCREASE.get(), AttributeModifier.Operation.ADDITION, String.valueOf(ARMOR_TOUGHNESS));
            MAUtils.addAttributes(player, Attributes.MAX_HEALTH, Config.VANIR_MASK_HEALTH_INCREASE.get(), AttributeModifier.Operation.ADDITION, String.valueOf(MAX_HEALTH));
            MAUtils.addAttributes(player, Attributes.MOVEMENT_SPEED, Config.VANIR_MASK_SPEED_INCREASE.get(), AttributeModifier.Operation.ADDITION, String.valueOf(MOVEMENT_SPEED));
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            MAUtils.removeAttributes(player, Attributes.ARMOR, String.valueOf(ARMOR));
            MAUtils.removeAttributes(player, Attributes.ARMOR_TOUGHNESS, String.valueOf(ARMOR_TOUGHNESS));
            MAUtils.removeAttributes(player, Attributes.MAX_HEALTH, String.valueOf(MAX_HEALTH));
            MAUtils.removeAttributes(player, Attributes.MOVEMENT_SPEED, String.valueOf(MOVEMENT_SPEED));
        }
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        double damageIncrease = Config.VANIR_MASK_DAMAGE_INCREASE.get();
        double healthIncrease = Config.VANIR_MASK_HEALTH_INCREASE.get() * 100;
        double speedIncrease = Config.VANIR_MASK_SPEED_INCREASE.get() * 100;
        double armorIncrease = Config.VANIR_MASK_ARMOR_INCREASE.get() * 100;
        double armorToughnessIncrease = Config.VANIR_MASK_ARMOR_TOUGHNESS_INCREASE.get() * 100;
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.vanir.mask.bloodflow").withStyle(ChatFormatting.GOLD));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.hold.ctrl"));
        if (Screen.hasControlDown()) {
            pTooltipComponents.remove(2);
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.vanir_mask.damage", String.format("%.1f", damageIncrease)).withStyle(ChatFormatting.DARK_AQUA));
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.vanir_mask.health", String.format("%.1f", healthIncrease)).withStyle(ChatFormatting.DARK_AQUA));
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.vanir_mask.speed", String.format("%.1f", speedIncrease)).withStyle(ChatFormatting.DARK_AQUA));
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.vanir_mask.armor", String.format("%.1f", armorIncrease)).withStyle(ChatFormatting.DARK_AQUA));
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.vanir_mask.armor_toughness", String.format("%.1f", armorToughnessIncrease)).withStyle(ChatFormatting.DARK_AQUA));
            super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        }
    }

}










