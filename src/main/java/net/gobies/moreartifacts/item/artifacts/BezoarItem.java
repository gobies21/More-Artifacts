package net.gobies.moreartifacts.item.artifacts;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;

public class BezoarItem extends Item implements ICurioItem {
    public BezoarItem(Properties properties) {
        super(new Properties().stacksTo(1).rarity(Rarity.COMMON));
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        if (livingEntity instanceof Player player) {
            player.removeEffect(MobEffects.POISON);
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("§7Grants immunity to Poison"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}








