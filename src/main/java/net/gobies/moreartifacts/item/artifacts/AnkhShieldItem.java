package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.Config;
import net.gobies.moreartifacts.item.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageTypes;
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
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static net.gobies.moreartifacts.init.MoreArtifactsCurioHandler.isCurioEquipped;

public class AnkhShieldItem extends Item implements ICurioItem {
    public AnkhShieldItem(Properties properties) {
        super(new Properties().stacksTo(1).rarity(Rarity.EPIC));
        MinecraftForge.EVENT_BUS.register(this);
    }

    static {
        MinecraftForge.EVENT_BUS.register(AnkhShieldItem.class);
    }

    private static final UUID KNOCKBACK_RESISTANCE_UUID = UUID.randomUUID();

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            if (player.hasEffect(MobEffects.POISON)) {
                player.removeEffect(MobEffects.POISON);
            }
            if (player.hasEffect(MobEffects.WITHER)) {
                player.removeEffect(MobEffects.WITHER);
            }
            if (player.hasEffect(MobEffects.HUNGER)) {
                player.removeEffect(MobEffects.HUNGER);
            }
            if (player.hasEffect(MobEffects.CONFUSION)) {
                player.removeEffect(MobEffects.CONFUSION);
            }
            if (player.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) {
                player.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
            }
            if (player.hasEffect(MobEffects.LEVITATION)) {
                player.removeEffect(MobEffects.LEVITATION);
            }
            if (player.hasEffect(MobEffects.DIG_SLOWDOWN)) {
                player.removeEffect(MobEffects.DIG_SLOWDOWN);
            }
            if (player.hasEffect(MobEffects.WEAKNESS)) {
                player.removeEffect(MobEffects.WEAKNESS);
            }
            if (player.hasEffect(MobEffects.BLINDNESS)) {
                player.removeEffect(MobEffects.BLINDNESS);
            }
            if (player.hasEffect(MobEffects.DARKNESS)) {
                player.removeEffect(MobEffects.DARKNESS);
            }
        }
    }
    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            var attribute = player.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
            if (attribute != null) {
                if (attribute.getModifier(KNOCKBACK_RESISTANCE_UUID) == null) {
                    attribute.addTransientModifier(
                            new AttributeModifier(KNOCKBACK_RESISTANCE_UUID, "Ankh Shield knockback immunity", 1000.0, AttributeModifier.Operation.ADDITION));
                }
            }
        }
    }

    @SubscribeEvent
    public void onMobEffectApplicable(MobEffectEvent.Applicable event) {
        if (event.getEntity() instanceof Player player) {
            event.getEffectInstance();
            if (event.getEffectInstance().getEffect() == MobEffects.POISON || event.getEffectInstance().getEffect() == MobEffects.WITHER || event.getEffectInstance().getEffect() == MobEffects.HUNGER || event.getEffectInstance().getEffect() == MobEffects.CONFUSION || event.getEffectInstance().getEffect() == MobEffects.MOVEMENT_SLOWDOWN || event.getEffectInstance().getEffect() == MobEffects.LEVITATION || event.getEffectInstance().getEffect() == MobEffects.DIG_SLOWDOWN || event.getEffectInstance().getEffect() == MobEffects.WEAKNESS || event.getEffectInstance().getEffect() == MobEffects.BLINDNESS || event.getEffectInstance().getEffect() == MobEffects.DARKNESS) {
                if (isCurioEquipped(player, ModItems.AnkhShield.get())) {
                    event.setResult(MobEffectEvent.Result.DENY);
                }
            }
        }
    }
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            Objects.requireNonNull(player.getAttribute(Attributes.KNOCKBACK_RESISTANCE)).removeModifier(KNOCKBACK_RESISTANCE_UUID);
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (isCurioEquipped(player, ModItems.AnkhShield.get())) {
                if (event.getSource().is(DamageTypes.ON_FIRE) || event.getSource().is(DamageTypes.IN_FIRE)) {
                    event.setAmount((float) (event.getAmount() * Config.ANKH_SHIELD_FIRE_DAMAGE_TAKEN.get()));
                }
            }
        }
    }
    @SubscribeEvent
    public static void onEntityAttacked(LivingAttackEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (isCurioEquipped(player, ModItems.AnkhShield.get())) {
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
        pTooltipComponents.add(Component.literal("§6Grants immunity to Knockback and Burning"));
        pTooltipComponents.add(Component.literal("§6Grants immunity to most debuffs"));
        pTooltipComponents.add(Component.literal(String.format("§6Reduces fire damage taken by §3%.1f%%", (1.0 - Config.ANKH_SHIELD_FIRE_DAMAGE_TAKEN.get()) * 100)));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}