package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.Config;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class MechanicalGloveItem extends Item implements ICurioItem {
    public MechanicalGloveItem(Properties properties) {
        super(new Properties().stacksTo(1).rarity(Rarity.COMMON));
    }

    private static final UUID ATTACK_DAMAGE_UUID = UUID.randomUUID();

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        if (livingEntity instanceof Player entity) {
            var attribute = entity.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE);
            if (attribute != null) {
                if (attribute.getModifier(ATTACK_DAMAGE_UUID) == null && stack.getItem() instanceof MechanicalGloveItem) {
                    attribute.addTransientModifier(
                            new AttributeModifier(ATTACK_DAMAGE_UUID, "Mechanical Glove Attack Damage", Config.MECHANICAL_ATTACK.get(), AttributeModifier.Operation.ADDITION));
                }
        }
    }
        }

    public void onUnequip(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        if (livingEntity instanceof Player entity) {
            Objects.requireNonNull(entity.getAttribute(Attributes.ATTACK_DAMAGE)).removeModifier(ATTACK_DAMAGE_UUID);
        }
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal(String.format("§7+%d §7Increased melee damage dealt", Config.MECHANICAL_ATTACK.get().intValue())));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}




