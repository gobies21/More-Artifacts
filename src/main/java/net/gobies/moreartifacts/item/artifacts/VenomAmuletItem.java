package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.Config;
import net.gobies.moreartifacts.util.CurioHandler;
import net.gobies.moreartifacts.item.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffects;
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

public class VenomAmuletItem extends Item implements ICurioItem {
    public VenomAmuletItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.UNCOMMON));
    }

    static {
        MinecraftForge.EVENT_BUS.register(VenomAmuletItem.class);
    }

    @SubscribeEvent
    public static void onPlayerAttacked(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (CurioHandler.isCurioEquipped(player, ModItems.VenomAmulet.get())) {
                if (event.getSource().getEntity() instanceof LivingEntity attacker) {
                    if (player.getRandom().nextFloat() < Config.VENOM_AMULET_POISON_CHANCE.get()) {
                        attacker.addEffect(new net.minecraft.world.effect.MobEffectInstance(MobEffects.POISON, 20 *Config.VENOM_AMULET_POISON_DURATION.get(), Config.VENOM_AMULET_POISON_LEVEL.get() - 1));
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
        pTooltipComponents.add(Component.literal("§7Has a chance to poison attackers"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}








