package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.Config;
import net.gobies.moreartifacts.util.CurioHandler;
import net.gobies.moreartifacts.item.MAItems;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
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
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class EchoGloveItem extends Item implements ICurioItem {
    public EchoGloveItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.RARE));
    }

    static {
        MinecraftForge.EVENT_BUS.register(EchoGloveItem.class);
    }

    private static final UUID ATTACK_DAMAGE_UUID = UUID.randomUUID();
    private static final UUID ATTACK_SPEED_UUID = UUID.randomUUID();

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        LivingEntity livingEntity = slotContext.entity();
        if (livingEntity instanceof Player entity) {
            var attackDamage = entity.getAttribute(Attributes.ATTACK_DAMAGE);
            if (attackDamage != null) {
                if (attackDamage.getModifier(ATTACK_DAMAGE_UUID) == null && stack.getItem() instanceof EchoGloveItem) {
                    attackDamage.addTransientModifier(
                            new AttributeModifier(ATTACK_DAMAGE_UUID, "Echo Glove Attack Speed", Config.ECHO_GLOVE_ATTACK_SPEED.get(), AttributeModifier.Operation.MULTIPLY_BASE));
                }
            }
            var attackSpeed = entity.getAttribute(Attributes.ATTACK_SPEED);
            if (attackSpeed != null) {
                if (attackSpeed.getModifier(ATTACK_SPEED_UUID) == null && stack.getItem() instanceof EchoGloveItem) {
                    attackSpeed.addTransientModifier(
                            new AttributeModifier(ATTACK_SPEED_UUID, "Echo Glove Attack Damage", Config.ECHO_GLOVE_DAMAGE.get(), AttributeModifier.Operation.MULTIPLY_BASE));
                }
            }
        }
    }

    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            var attackDamage = player.getAttribute(Attributes.ATTACK_DAMAGE);
            var attackSpeed = player.getAttribute(Attributes.ATTACK_SPEED);
            if (attackDamage != null) {
                attackDamage.removeModifier(ATTACK_DAMAGE_UUID);
            }
            if (attackSpeed != null) {
                attackSpeed.removeModifier(ATTACK_SPEED_UUID);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event) {
        if (!event.isCanceled() && event.getSource().getEntity() instanceof LivingEntity attacker) {
            LivingEntity attackedEntity = event.getEntity();
            if (CurioHandler.isCurioEquipped(attacker, MAItems.EchoGlove.get())) {
                RandomSource random = attacker.getRandom();
                if (random.nextFloat() < Config.ECHO_GLOVE_IGNORE_CHANCE.get()) {
                    // reduce the invincibility time by a fixed number of ticks
                    attackedEntity.invulnerableTime = Math.max(0, attackedEntity.invulnerableTime - 5);
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
        pTooltipComponents.add(Component.literal(String.format("§6Increases melee damage dealt by §3%.1f%%", (Config.ECHO_GLOVE_DAMAGE.get() * 100))));
        pTooltipComponents.add(Component.literal(String.format("§6Increases melee attack speed by §3%.1f%%", (Config.ECHO_GLOVE_ATTACK_SPEED.get() * 100))));
        pTooltipComponents.add(Component.literal(String.format("§3%.1f%% §6Chance to ignore invulnerability frames on hit", (Config.ECHO_GLOVE_IGNORE_CHANCE.get() * 100))));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}





