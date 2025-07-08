package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.Config;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.util.CurioHandler;
import net.gobies.moreartifacts.util.MAUtils;
import net.minecraft.ChatFormatting;
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

public class ObsidianSkullItem extends Item implements ICurioItem {
    public ObsidianSkullItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.UNCOMMON).fireResistant());
    }

    static {
        MinecraftForge.EVENT_BUS.register(ObsidianSkullItem.class);
    }


    @SubscribeEvent
    public static void onEntityAttacked(LivingAttackEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (CurioHandler.isCurioEquipped(player, MAItems.ObsidianSkull.get())) {
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
        double fireDamageReduction = (1.0 - Config.SKULL_FIRE_DAMAGE_TAKEN.get()) * 100;
        pTooltipComponents.add(Component.literal("§7Grants immunity to Burning"));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.obsidian_skull.fire_damage", String.format("%.1f", fireDamageReduction)).withStyle(ChatFormatting.DARK_AQUA));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}






