package net.gobies.moreartifacts.item.artifacts;

import net.minecraft.world.item.Item;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import javax.annotation.Nullable;
import java.util.List;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BalloonItem extends Item implements ICurioItem {
    @SubscribeEvent
    public void onPlayerJump(LivingEvent.LivingJumpEvent event) {
        if (event.getEntity() instanceof Player player) {
            var optionalBalloon = top.theillusivec4.curios.api.CuriosApi.getCuriosHelper()
                    .findEquippedCurio(this, player);
            if (optionalBalloon.isPresent()) {
                player.setDeltaMovement(player.getDeltaMovement().add(0, 0.4, 0));
            }
        }
    }

    @SubscribeEvent
    public void onFallDamageReduction(net.minecraftforge.event.entity.living.LivingFallEvent event) {
        if (event.getEntity() instanceof Player player) {
            var optionalBalloon = top.theillusivec4.curios.api.CuriosApi.getCuriosHelper()
                    .findEquippedCurio(this, player);
            if (optionalBalloon.isPresent()) {
                event.setDistance(event.getDistance() * 0.5f);
            }
        }
    }

    private static int jumpTimer = 0;

    public BalloonItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.COMMON));
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level
            pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("§7Increases jump height"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}









