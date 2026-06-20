package net.gobies.moreartifacts.item.artifacts;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.util.ModLoadedUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class TrueEnderianScarfItem extends Item implements ICurioItem {
    public TrueEnderianScarfItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.RARE));
    }

    private static final UUID ENTITY_REACH = UUID.fromString("dad6258c-89f4-4725-9b85-a12cc7a500c6");
    private static final UUID BLOCK_REACH = UUID.fromString("a6c6ef61-13c4-495d-b66d-1d035e53df7f");

    @Override
    public boolean isEnderMask(SlotContext slotContext, EnderMan enderMan, ItemStack stack) {
        LivingEntity var5 = slotContext.entity();
        return var5 instanceof Player && stack.getItem() instanceof TrueEnderianScarfItem;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = LinkedHashMultimap.create();
        modifiers.put(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(uuid, String.valueOf(ENTITY_REACH), CommonConfig.TRUE_ENDERIAN_REACH.get(), AttributeModifier.Operation.ADDITION));
        modifiers.put(ForgeMod.BLOCK_REACH.get(), new AttributeModifier(uuid, String.valueOf(BLOCK_REACH), CommonConfig.TRUE_ENDERIAN_REACH.get(), AttributeModifier.Operation.ADDITION));

        return modifiers;
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        double scarfDamageTaken = CommonConfig.TRUE_ENDERIAN_DAMAGE_REDUCTION.get() * 100;
        double scarfEvadeChance = CommonConfig.TRUE_ENDERIAN_EVADE.get() * 100;
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.true_enderian_scarf.neutral").withStyle(ChatFormatting.LIGHT_PURPLE));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.true_enderian_scarf.damage_taken", String.format("%.1f", scarfDamageTaken)).withStyle(ChatFormatting.DARK_AQUA));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.true_enderian_scarf.evade_chance", String.format("%.1f", scarfEvadeChance)).withStyle(ChatFormatting.DARK_AQUA));
        if (ModLoadedUtil.isEnhancedVisualsLoaded() && CommonConfig.TRUE_ENDERIAN_COMPAT.get()) {
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.hold.ctrl"));
            if (Screen.hasControlDown()) {
                pTooltipComponents.remove(4);
                pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.true_enderian_scarf.enhanced_visuals").withStyle(ChatFormatting.GRAY));
            }
            super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        }
    }
}




