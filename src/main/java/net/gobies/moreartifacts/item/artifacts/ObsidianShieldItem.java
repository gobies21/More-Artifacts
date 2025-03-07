package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.item.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
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
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class ObsidianShieldItem extends Item implements ICurioItem {
    public ObsidianShieldItem(Properties properties) {
        super(new Properties().stacksTo(1).rarity(Rarity.RARE));
    }

    private static final UUID KNOCKBACK_RESISTANCE_UUID = UUID.randomUUID();

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        if (livingEntity instanceof Player entity) {
            var attribute = entity.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.KNOCKBACK_RESISTANCE);
            if (attribute != null) {
                if (attribute.getModifier(KNOCKBACK_RESISTANCE_UUID) == null) {
                    attribute.addTransientModifier(
                            new AttributeModifier(KNOCKBACK_RESISTANCE_UUID, "Cobalt Shield knockback immunity", 1000.0, AttributeModifier.Operation.ADDITION));
                }
            }
        }
    }
    public void onUnequip(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        if (livingEntity instanceof Player entity) {
            entity.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.KNOCKBACK_RESISTANCE).removeModifier(KNOCKBACK_RESISTANCE_UUID);
        }
    }
    static {
        MinecraftForge.EVENT_BUS.register(ObsidianShieldItem.class);
    }
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getEntity()instanceof Player player) {
            CuriosApi.getCuriosHelper().findEquippedCurio(ModItems.ObsidianShield.get(), player).ifPresent((slot) -> {
                if (event.getSource().is(DamageTypes.ON_FIRE) || event.getSource().is(DamageTypes.IN_FIRE)) {
                    event.setAmount(event.getAmount() * 0.50f);
                }
            });
        }
    }
    @SubscribeEvent
    public static void onEntityAttacked(LivingAttackEvent event) {
        if (event.getEntity() instanceof Player player) {
            CuriosApi.getCuriosHelper().findEquippedCurio(ModItems.ObsidianShield.get(), player).ifPresent((slot) -> {
                if (event.getSource().is(DamageTypes.HOT_FLOOR)) {
                    event.setCanceled(true);

                }
            });
        }
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("§7Grants immunity to Knockback and Burning"));
        pTooltipComponents.add(Component.literal("§7Reduces fire damage taken by §350.0%"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}