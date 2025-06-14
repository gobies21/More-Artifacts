package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.Config;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.util.CurioHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
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

public class MoltenQuiverItem extends Item implements ICurioItem {
    public MoltenQuiverItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.RARE));
    }

    static {
        MinecraftForge.EVENT_BUS.register(MoltenQuiverItem.class);
    }
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player attacker) {
            if (CurioHandler.isCurioEquipped(attacker, MAItems.MoltenQuiver.get())) {
                if (event.getSource().is(DamageTypes.ARROW)) {
                    event.setAmount((float) (event.getAmount() * Config.MOLTEN_QUIVER_DAMAGE.get()));
                    LivingEntity target = event.getEntity();
                    if (!target.isOnFire()) {
                        target.setSecondsOnFire(Config.MOLTEN_QUIVER_DURATION.get());
                    }
                    if (target.isOnFire()) {
                        event.setAmount((float) (event.getAmount() * Config.MOLTEN_QUIVER_ONFIRE_DAMAGE.get()));
                    }
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
        pTooltipComponents.add(Component.literal(String.format("§6Increases arrow damage by §3%.1f%%", (Config.MOLTEN_QUIVER_DAMAGE.get() - 1) * 100)));
        pTooltipComponents.add(Component.literal(String.format("§3%.1f%% §6Chance to not consume arrows", (Config.MOLTEN_QUIVER_AMMO.get() * 100))));
        pTooltipComponents.add(Component.literal("§6Lights attacked enemies on fire"));
        pTooltipComponents.add(Component.literal(String.format("§6Deals §3%.1f%% §6increased damage to ignited enemies", (Config.MOLTEN_QUIVER_ONFIRE_DAMAGE.get() - 1) * 100)));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}








