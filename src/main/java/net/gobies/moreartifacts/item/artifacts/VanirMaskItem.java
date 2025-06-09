package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.Config;
import net.gobies.moreartifacts.util.CurioHandler;
import net.gobies.moreartifacts.init.MAItems;
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
import java.util.Objects;
import java.util.UUID;

public class VanirMaskItem extends Item implements ICurioItem {
    private static final UUID ARMOR_UUID = UUID.randomUUID();
    private static final UUID ARMOR_TOUGHNESS_UUID = UUID.randomUUID();
    private static final UUID MAX_HEALTH_UUID = UUID.randomUUID();
    private static final UUID MOVEMENT_SPEED_UUID = UUID.randomUUID();

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
            var Armor = player.getAttribute(Attributes.ARMOR);
            if (Armor != null && Armor.getModifier(ARMOR_UUID) == null) {
                Armor.addTransientModifier(
                        new AttributeModifier(ARMOR_UUID, "Vanir Mask Armor", Config.VANIR_MASK_ARMOR_INCREASE.get(), AttributeModifier.Operation.MULTIPLY_TOTAL));
            }

            var ArmorToughness = player.getAttribute(Attributes.ARMOR_TOUGHNESS);
            if (ArmorToughness != null && ArmorToughness.getModifier(ARMOR_TOUGHNESS_UUID) == null) {
                ArmorToughness.addTransientModifier(
                        new AttributeModifier(ARMOR_TOUGHNESS_UUID, "Vanir Mask Armor Toughness", Config.VANIR_MASK_ARMOR_TOUGHNESS_INCREASE.get(), AttributeModifier.Operation.MULTIPLY_TOTAL));
            }

            var MaxHealth = player.getAttribute(Attributes.MAX_HEALTH);
            if (MaxHealth != null && MaxHealth.getModifier(MAX_HEALTH_UUID) == null) {
                MaxHealth.addTransientModifier(
                        new AttributeModifier(MAX_HEALTH_UUID, "Vanir Mask Health", Config.VANIR_MASK_HEALTH_INCREASE.get(), AttributeModifier.Operation.MULTIPLY_TOTAL));
            }

            var Speed = player.getAttribute(Attributes.MOVEMENT_SPEED);
            if (Speed != null && Speed.getModifier(MOVEMENT_SPEED_UUID) == null) {
                Speed.addTransientModifier(
                        new AttributeModifier(MOVEMENT_SPEED_UUID, "Vanir Mask Speed", Config.VANIR_MASK_SPEED_INCREASE.get(), AttributeModifier.Operation.MULTIPLY_TOTAL));
            }
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            Objects.requireNonNull(player.getAttribute(Attributes.ARMOR)).removeModifier(ARMOR_UUID);
            Objects.requireNonNull(player.getAttribute(Attributes.ARMOR_TOUGHNESS)).removeModifier(ARMOR_TOUGHNESS_UUID);
            Objects.requireNonNull(player.getAttribute(Attributes.MAX_HEALTH)).removeModifier(MAX_HEALTH_UUID);
            Objects.requireNonNull(player.getAttribute(Attributes.MOVEMENT_SPEED)).removeModifier(MOVEMENT_SPEED_UUID);
        }
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.vanir.mask.bloodflow"));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.hold.ctrl"));
        if (Screen.hasControlDown()) {
            pTooltipComponents.remove(2);
            pTooltipComponents.add(Component.literal(" §3+" + Config.VANIR_MASK_DAMAGE_INCREASE.get() + " §6Damage Dealt"));
            pTooltipComponents.add(Component.literal(" §3+" + (Config.VANIR_MASK_HEALTH_INCREASE.get() * 100) + "% §6Max Health"));
            pTooltipComponents.add(Component.literal(" §3+" + (Config.VANIR_MASK_SPEED_INCREASE.get() * 100) + "% §6Speed"));
            pTooltipComponents.add(Component.literal(" §3+" + (Config.VANIR_MASK_ARMOR_INCREASE.get() * 100) + "% §6Armor"));
            pTooltipComponents.add(Component.literal(" §3+" + (Config.VANIR_MASK_ARMOR_TOUGHNESS_INCREASE.get() * 100) + "% §6Armor Toughness"));
            super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        }
    }

}










