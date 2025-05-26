package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.Config;
import net.gobies.moreartifacts.util.CurioHandler;
import net.gobies.moreartifacts.item.MAItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class MelodyPlushieItem extends Item implements ICurioItem {
    public MelodyPlushieItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.EPIC));
    }

    static {
        MinecraftForge.EVENT_BUS.register(MelodyPlushieItem.class);
    }

    private static final UUID MAX_HEALTH = UUID.randomUUID();

    @SubscribeEvent
    public static void onPlayerWakeUp(PlayerWakeUpEvent event) {
        Player player = event.getEntity();
        if (!player.level().isClientSide && player.isSleeping()) {
            if (CurioHandler.isCurioEquipped(player, MAItems.MelodyPlushie.get())) {
                player.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 20 * Config.PLUSHIE_DURATION.get(), Config.PLUSHIE_HEALTH_BOOST_LEVEL.get() - 1, true, true));
            }
        }
    }


    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            if (!player.hasEffect(MobEffects.REGENERATION)) {
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, -1, Config.PLUSHIE_REGEN_LEVEL.get() - 1, false, false));
            }
        }
    }

    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            var attribute = player.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH);
            if (attribute != null) {
                if (attribute.getModifier(MAX_HEALTH) == null) {
                    attribute.addTransientModifier(
                            new AttributeModifier(MAX_HEALTH, "Plushie Max Health", Config.PLUSHIE_HEALTH.get(), AttributeModifier.Operation.MULTIPLY_BASE));
                }
            }
        }
    }

    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            player.removeEffect(MobEffects.REGENERATION);
            Objects.requireNonNull(player.getAttribute(Attributes.MAX_HEALTH)).removeModifier(MAX_HEALTH);
            player.setHealth(player.getHealth() - 0.1F);
        }
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("§dGrants Regeneration"));
        pTooltipComponents.add(Component.literal(String.format("§3+%.1f%% §dIncreased max health", Config.PLUSHIE_HEALTH.get() * 100)));
        pTooltipComponents.add(Component.literal("§dIf equip when sleeping, grants a buff"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}






