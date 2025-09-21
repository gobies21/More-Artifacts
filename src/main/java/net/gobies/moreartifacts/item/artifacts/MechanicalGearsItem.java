package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.util.CurioHandler;
import net.gobies.moreartifacts.util.MAUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;
import java.util.UUID;

public class MechanicalGearsItem extends Item implements ICurioItem {
    public MechanicalGearsItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.UNCOMMON));
    }

    static {
        MinecraftForge.EVENT_BUS.register(MechanicalGearsItem.class);
    }

    private static final UUID SPEED = UUID.randomUUID();

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            MAUtils.addAttributes(player, Attributes.MOVEMENT_SPEED, CommonConfig.MECHANICAL_GEARS_SPEED.get(), AttributeModifier.Operation.MULTIPLY_BASE, String.valueOf(SPEED));
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            MAUtils.removeAttributes(player, Attributes.MOVEMENT_SPEED, String.valueOf(SPEED));
        }
    }

    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event) {
        if (event.getEntity() instanceof Player player && event.getSource().getEntity() != null) {
            if (CurioHandler.isCurioEquipped(player, MAItems.MechanicalGears.get())) {
                if (player.getRandom().nextFloat() < 0.05) {
                    double x = player.getX();
                    double z = player.getZ();
                    double attackerX = event.getSource().getEntity().getX();
                    double attackerZ = event.getSource().getEntity().getZ();

                    double angle = Math.atan2(attackerZ - z, attackerX - x);
                    boolean side = player.getRandom().nextBoolean();
                    double deltaXZ = Math.cos(angle + (side ? (Math.PI / 2D) : (-Math.PI / 2D)));
                    Vec3 knockbackVector = new Vec3(deltaXZ, 0.3D, deltaXZ).normalize().scale(1.0D);

                    player.setDeltaMovement(knockbackVector);
                    player.hurtMarked = true;
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
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        double movementSpeed = CommonConfig.MECHANICAL_GEARS_SPEED.get() * 100;
        double dodgeChance = CommonConfig.MECHANICAL_GEARS_DODGE_CHANCE.get() * 100;
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.mechanical_gears.speed", String.format("%.1f", movementSpeed)).withStyle(ChatFormatting.DARK_AQUA));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.mechanical_gears.dodge", String.format("%.1f", dodgeChance)).withStyle(ChatFormatting.DARK_AQUA));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
