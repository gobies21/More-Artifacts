package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.util.CurioHandler;
import net.gobies.moreartifacts.util.MAUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
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

public class FireStoneItem extends Item implements ICurioItem {
    public FireStoneItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.RARE).fireResistant());
    }

    static {
        MinecraftForge.EVENT_BUS.register(FireStoneItem.class);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            if (player.isOnFire()) {
                player.extinguishFire();
            }
        }
    }

    @SubscribeEvent
    public static void onEntityAttacked(LivingAttackEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (CurioHandler.isCurioEquipped(player, MAItems.FireStone.get())) {
                MAUtils.makeBurningImmune(event);
            }
        }
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        double fireChance = CommonConfig.FIRE_STONE_CHANCE.get() * 100;
        int fireDuration = CommonConfig.FIRE_STONE_DURATION.get();
        double increasedDamage = (CommonConfig.FIRE_STONE_DAMAGE.get() - 1) * 100;
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.fire_stone.immunity").withStyle(ChatFormatting.RED));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.fire_stone.inflame", String.format("%.1f", fireChance), fireDuration).withStyle(ChatFormatting.DARK_AQUA));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.fire_stone.increased_damage", String.format("%.1f",  increasedDamage)).withStyle(ChatFormatting.DARK_AQUA));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.hold.ctrl"));
        if (Screen.hasControlDown()) {
            pTooltipComponents.remove(4);
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.fire_stone_ignite").withStyle(ChatFormatting.RED));
            super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        }
    }
}


