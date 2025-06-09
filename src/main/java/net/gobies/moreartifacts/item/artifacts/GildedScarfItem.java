package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.Config;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.util.CurioHandler;
import net.minecraft.network.chat.Component;
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

public class GildedScarfItem extends Item implements ICurioItem {
    public GildedScarfItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.RARE).fireResistant());
    }

    static {
        MinecraftForge.EVENT_BUS.register(GildedScarfItem.class);
    }

    @Override
    public boolean makesPiglinsNeutral(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player attacker) {
            if (CurioHandler.isCurioEquipped(attacker, MAItems.GildedScarf.get())) {
                event.setAmount((float) (event.getAmount() * Config.GILDED_DAMAGE_DEALT.get()));
            }
        }
            if (event.getEntity() instanceof Player player) {
                if (CurioHandler.isCurioEquipped(player, MAItems.GildedScarf.get())) {
                    event.setAmount((float) (event.getAmount() * Config.GILDED_DAMAGE_TAKEN.get()));
                }
            }
        }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("§ePiglins are neutral"));
        pTooltipComponents.add(Component.literal(String.format("§6Reduces damage taken by §3%.1f%%", (100 - Config.GILDED_DAMAGE_TAKEN.get() * 100))));
        pTooltipComponents.add(Component.literal(String.format("§6Increases damage dealt by §3%.1f%%", (Config.GILDED_DAMAGE_DEALT.get() - 1) * 100)));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}








