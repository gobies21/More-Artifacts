package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.compat.coldsweat.ColdSweatCompat;
import net.gobies.moreartifacts.compat.iceandfire2.ElementalDragonEyes;
import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.entity.FriendlyEndermanAI;
import net.gobies.moreartifacts.util.MAUtils;
import net.gobies.moreartifacts.util.ModLoadedUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class DragonEyeItem extends Item implements ICurioItem {

    public DragonEyeItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.RARE));
    }

    private static final UUID HEAT_RESISTANCE = UUID.randomUUID();
    private static final UUID COLD_RESISTANCE = UUID.randomUUID();

    @Override
    public boolean isEnderMask(SlotContext slotContext, EnderMan enderMan, ItemStack stack) {
        return isEnderDragonEye(stack);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            if (isEnderDragonEye(stack)) {
                if (!player.hasEffect(MobEffects.NIGHT_VISION)) {
                    player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, -1, 0, true, false));
                }
                double followingDistance = CommonConfig.ENDER_DRAGON_EYE_FOLLOW_DISTANCE.get(); // Value halved for Y distance
                for (Entity entity : player.level().getEntities(player, player.getBoundingBox().inflate(followingDistance, followingDistance / 2, followingDistance))) {
                    if (entity instanceof EnderMan enderMan) {
                        FriendlyEndermanAI friendlyEndermanAI = new FriendlyEndermanAI(enderMan, player);
                        friendlyEndermanAI.updateEndermanAI();                    }
                }
            } else {
                ElementalDragonEyes.proccessEffects(player, stack);
            }
        }
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            if (isFireDragonEye(stack)) {
                MAUtils.addAttributes(player, ColdSweatCompat.heatResistanceAttribute(), 1.0, AttributeModifier.Operation.ADDITION, String.valueOf(HEAT_RESISTANCE));
            }
            if (isIceDragonEye(stack)) {
                MAUtils.addAttributes(player, ColdSweatCompat.coldResistanceAttribute(), 1.0, AttributeModifier.Operation.ADDITION, String.valueOf(COLD_RESISTANCE));
            }
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            if (isEnderDragonEye(stack)) {
                if (player.hasEffect(MobEffects.NIGHT_VISION)) {
                    player.removeEffect(MobEffects.NIGHT_VISION);
                }
                double followingDistance = CommonConfig.ENDER_DRAGON_EYE_FOLLOW_DISTANCE.get(); // Value halved for Y distance
                for (Entity entity : player.level().getEntities(player, player.getBoundingBox().inflate(followingDistance, followingDistance / 2, followingDistance))) {
                    if (entity instanceof EnderMan enderMan) {
                        FriendlyEndermanAI friendlyEndermanAI = new FriendlyEndermanAI(enderMan, player);
                        friendlyEndermanAI.revertEndermanAI();
                    }
                }
            }
            if (isFireDragonEye(stack)) {
                MAUtils.removeAttributes(player, ColdSweatCompat.heatResistanceAttribute(), String.valueOf(HEAT_RESISTANCE));
            }
            if (isIceDragonEye(stack)) {
                MAUtils.removeAttributes(player, ColdSweatCompat.coldResistanceAttribute(), String.valueOf(COLD_RESISTANCE));
            }
            ElementalDragonEyes.removeEffects(player, stack);
        }
    }

    public static boolean isFireDragonEye(ItemStack stack) {
        CompoundTag nbt = stack.getTag();
        return nbt != null && nbt.getBoolean("Fire");
    }

    public static boolean isIceDragonEye(ItemStack stack) {
        CompoundTag nbt = stack.getTag();
        return nbt != null && nbt.getBoolean("Ice");
    }

    public static boolean isLightningDragonEye(ItemStack stack) {
        CompoundTag nbt = stack.getTag();
        return nbt != null && nbt.getBoolean("Lightning");
    }

    public static boolean isEnderDragonEye(ItemStack stack) {
        return !isFireDragonEye(stack) && !isIceDragonEye(stack) && !isLightningDragonEye(stack);
    }

    public static ItemStack createEnderDragonEye() {
        ItemStack enderDragonEye = new ItemStack(MAItems.DragonEye.get());
        isEnderDragonEye(enderDragonEye);
        return enderDragonEye;
    }

    public static ItemStack createFireDragonEye() {
        ItemStack fireDragonEye = new ItemStack(MAItems.DragonEye.get());
        CompoundTag nbt = new CompoundTag();
        nbt.putBoolean("Fire", true);
        fireDragonEye.setTag(nbt);
        return fireDragonEye;
    }

    public static ItemStack createIceDragonEye() {
        ItemStack iceDragonEye = new ItemStack(MAItems.DragonEye.get());
        CompoundTag nbt = new CompoundTag();
        nbt.putBoolean("Ice", true);
        iceDragonEye.setTag(nbt);
        return iceDragonEye;
    }

    public static ItemStack createLightningDragonEye() {
        ItemStack lightningDragonEye = new ItemStack(MAItems.DragonEye.get());
        CompoundTag nbt = new CompoundTag();
        nbt.putBoolean("Lightning", true);
        lightningDragonEye.setTag(nbt);
        return lightningDragonEye;
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack stack) {
        if (isFireDragonEye(stack)) {
            return Component.translatable("item.moreartifacts.fire_dragon_eye");
        } else if (isIceDragonEye(stack)) {
            return Component.translatable("item.moreartifacts.ice_dragon_eye");
        } else if (isLightningDragonEye(stack)) {
            return Component.translatable("item.moreartifacts.lightning_dragon_eye");
        } else {
            return Component.translatable("item.moreartifacts.dragon_eye");
        }
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public @NotNull Rarity getRarity(@NotNull ItemStack pStack) {
        if (isEnderDragonEye(pStack)) {
            return Rarity.EPIC;
        }
        return Rarity.RARE;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        if (isFireDragonEye(stack)) {
            tooltip.add(Component.translatable("tooltip.moreartifacts.dragon_eye").withStyle(ChatFormatting.GOLD));
            tooltip.add(Component.translatable("tooltip.moreartifacts.fire_dragon_eye.resistance").withStyle(ChatFormatting.GOLD));
        } else if (isIceDragonEye(stack)) {
            tooltip.add(Component.translatable("tooltip.moreartifacts.dragon_eye").withStyle(ChatFormatting.GOLD));
            tooltip.add(Component.translatable("tooltip.moreartifacts.ice_dragon_eye.resistance").withStyle(ChatFormatting.GOLD));
        } else if (isLightningDragonEye(stack)) {
            tooltip.add(Component.translatable("tooltip.moreartifacts.dragon_eye").withStyle(ChatFormatting.GOLD));
            tooltip.add(Component.translatable("tooltip.moreartifacts.lightning_dragon_eye.resistance").withStyle(ChatFormatting.GOLD));
        } else {
            double summonChance = CommonConfig.ENDER_DRAGON_EYE_SUMMON_CHANCE.get() * 100;
            tooltip.add(Component.translatable("tooltip.moreartifacts.dragon_eye").withStyle(ChatFormatting.GOLD));
            tooltip.add(Component.translatable("tooltip.moreartifacts.ender_dragon_eye.endermen").withStyle(ChatFormatting.LIGHT_PURPLE));
            tooltip.add(Component.translatable("tooltip.moreartifacts.ender_dragon_eye.summon_enderman", String.format("%.1f", summonChance)).withStyle(ChatFormatting.DARK_AQUA));
            if (ModLoadedUtil.isEnhancedVisualsLoaded() && CommonConfig.ENDER_DRAGON_EYE_COMPAT.get()) {
                tooltip.add(Component.translatable("tooltip.moreartifacts.hold.ctrl"));
                if (Screen.hasControlDown()) {
                    tooltip.remove(4);
                    tooltip.add(Component.translatable("tooltip.moreartifacts.true_enderian_scarf.enhanced_visuals").withStyle(ChatFormatting.GRAY));
                }
            }
        }
        super.appendHoverText(stack, level, tooltip, flag);
    }
}