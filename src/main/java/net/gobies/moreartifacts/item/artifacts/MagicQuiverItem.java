package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.Config;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.util.CurioHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
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

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player attacker) {
            if (CurioHandler.isCurioEquipped(attacker, MAItems.MagicQuiver.get())) {
                if (event.getSource().is(DamageTypes.ARROW)) {
                    event.setAmount((float) (event.getAmount() * Config.MAGIC_QUIVER_DAMAGE.get()));
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
        pTooltipComponents.add(Component.literal(String.format("§7Increases arrow damage by §3%.1f%%", (Config.MAGIC_QUIVER_DAMAGE.get() - 1) * 100)));
        pTooltipComponents.add(Component.literal(String.format("§3%.1f%% §7Chance to not consume arrows", (Config.MAGIC_QUIVER_AMMO.get() * 100))));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}








