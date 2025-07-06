package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.Config;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import javax.annotation.Nullable;
import java.util.List;

public class MagicQuiverItem extends Item implements ICurioItem {
    public MagicQuiverItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.UNCOMMON));
        MinecraftForge.EVENT_BUS.register(this);
    }

    static {
        MinecraftForge.EVENT_BUS.register(MagicQuiverItem.class);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        double arrowDamage = (Config.MAGIC_QUIVER_DAMAGE.get() - 1) * 100;
        double arrowSave = (Config.MAGIC_QUIVER_AMMO.get() * 100);
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.magic_quiver.damage", String.format("%.1f", arrowDamage)).withStyle(ChatFormatting.DARK_AQUA));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.magic_quiver.save_arrow", String.format("%.1f", arrowSave)).withStyle(ChatFormatting.DARK_AQUA));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}








