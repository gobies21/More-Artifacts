package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.Config;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import javax.annotation.Nullable;
import java.util.List;

public class EnderianEyeItem extends Item implements ICurioItem {
    public EnderianEyeItem(Properties properties) {
        super(new Properties().stacksTo(1).rarity(Rarity.RARE));

    }

    static {
        MinecraftForge.EVENT_BUS.register(EnderianEyeItem.class);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("§6Grants the ability to teleport where you are looking"));
        pTooltipComponents.add(Component.literal(String.format("§6Max radius of §3%d §6blocks with a §3%.1f §6second cooldown", Config.ENDERIAN_EYE_RADIUS.get(), Config.ENDERIAN_EYE_COOLDOWN.get())));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}