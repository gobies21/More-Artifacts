package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.Config;
import net.gobies.moreartifacts.item.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;

import static net.gobies.moreartifacts.init.MoreArtifactsCurioHandler.isCurioEquipped;

public class ObsidianSkullItem extends Item implements ICurioItem {
    public ObsidianSkullItem(Properties properties) {
        super(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
    }

    static {
        MinecraftForge.EVENT_BUS.register(ObsidianSkullItem.class);
    }
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getEntity()instanceof Player player) {
            if (isCurioEquipped(player, ModItems.ObsidianSkull.get())) {
                if (event.getSource().is(DamageTypes.ON_FIRE) || event.getSource().is(DamageTypes.IN_FIRE)) {
                    event.setAmount((float) (event.getAmount() * Config.SKULL_FIRE_DAMAGE_TAKEN.get()));
                }
            }
        }
    }
@SubscribeEvent
public static void onEntityAttacked(LivingAttackEvent event) {
    if (event.getEntity() instanceof Player player) {
        if (isCurioEquipped(player, ModItems.ObsidianSkull.get())) {
            if (event.getSource().is(DamageTypes.HOT_FLOOR)) {
                event.setCanceled(true);

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
        pTooltipComponents.add(Component.literal("§7Grants immunity to Burning"));
        pTooltipComponents.add(Component.literal(String.format("§7Reduces fire damage taken by §3%.1f%%", (1.0 - Config.SKULL_FIRE_DAMAGE_TAKEN.get()) * 100)));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}






