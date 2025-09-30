package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.util.CurioHandler;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.util.MAUtils;
import net.minecraft.ChatFormatting;
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
        if (slotContext.entity() instanceof Player player) {
            MAUtils.addAttributes(player, Attributes.ATTACK_DAMAGE, CommonConfig.ECHO_GLOVE_DAMAGE.get(), AttributeModifier.Operation.MULTIPLY_BASE, String.valueOf(ATTACK_DAMAGE_UUID));
            MAUtils.addAttributes(player, Attributes.ATTACK_SPEED, CommonConfig.ECHO_GLOVE_ATTACK_SPEED.get(), AttributeModifier.Operation.MULTIPLY_BASE, String.valueOf(ATTACK_SPEED_UUID));
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            MAUtils.removeAttributes(player, Attributes.ATTACK_DAMAGE, String.valueOf(ATTACK_DAMAGE_UUID));
            MAUtils.removeAttributes(player, Attributes.ATTACK_SPEED, String.valueOf(ATTACK_SPEED_UUID));
        }
    }

    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event) {
        if (!event.isCanceled() && event.getSource().getEntity() instanceof LivingEntity attacker) {
            LivingEntity attackedEntity = event.getEntity();
            int gloveCount = CurioHandler.getCurioCount(attacker, MAItems.EchoGlove.get());
            for (int i = 0; i < gloveCount; i++) {
                RandomSource random = attacker.getRandom();
                double ignoreChance = CommonConfig.ECHO_GLOVE_IGNORE_CHANCE.get() * gloveCount;
                if (random.nextFloat() < ignoreChance) {
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
        double attackDamage = (CommonConfig.ECHO_GLOVE_DAMAGE.get() * 100);
        double attackSpeed = (CommonConfig.ECHO_GLOVE_ATTACK_SPEED.get() * 100);
        double ignoreChance = (CommonConfig.ECHO_GLOVE_IGNORE_CHANCE.get() * 100);
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.echo_glove.damage", String.format("%.1f", attackDamage)).withStyle(ChatFormatting.DARK_AQUA));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.echo_glove.speed", String.format("%.1f", attackSpeed)).withStyle(ChatFormatting.DARK_AQUA));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.echo_glove.ignore_chance", String.format("%.1f", ignoreChance)).withStyle(ChatFormatting.DARK_AQUA));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}





