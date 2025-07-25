package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.util.CurioHandler;
import net.gobies.moreartifacts.util.ModLoadedUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;

public class IceStoneItem extends Item implements ICurioItem {
    public IceStoneItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.RARE));
    }

    static {
        MinecraftForge.EVENT_BUS.register(IceStoneItem.class);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            if (player.isFreezing()) {
                player.setTicksFrozen(-1);
            }
        }
    }

    @SubscribeEvent
    public static void onEntityAttacked(LivingAttackEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (CurioHandler.isCurioEquipped(player, MAItems.IceStone.get())) {
                if (event.getSource().is(DamageTypes.FREEZE)) {
                    event.setCanceled(true);

                }
            }
        }
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        double iceChance = CommonConfig.ICE_STONE_CHANCE.get() * 100;
        int iceDuration = CommonConfig.ICE_STONE_DURATION.get();
        double increasedDamage = (CommonConfig.ICE_STONE_DAMAGE.get() - 1) * 100;
        double encaseChance = (CommonConfig.ICE_STONE_ENCASED_CHANCE.get() * 100);
        int encaseDuration = CommonConfig.ICE_STONE_ENCASED_DURATION.get();
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.ice_stone.immunity").withStyle(ChatFormatting.AQUA));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.ice_stone.freeze", String.format("%.1f", iceChance), iceDuration).withStyle(ChatFormatting.DARK_AQUA));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.ice_stone.increased_damage", String.format("%.1f",  increasedDamage)).withStyle(ChatFormatting.DARK_AQUA));
        if (ModLoadedUtil.isIceandFireLoaded() && (CommonConfig.ICE_STONE_COMPAT.get())) {
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.hold.ctrl"));
            if (Screen.hasControlDown()) {
                pTooltipComponents.remove(4);
                pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.ice_stone.encased", String.format("%.1f", encaseChance), encaseDuration).withStyle(ChatFormatting.DARK_AQUA));
            }
            super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        }
    }
}








