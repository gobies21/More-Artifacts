package net.gobies.moreartifacts.item.artifacts;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.gobies.moreartifacts.config.CommonConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
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

public class EchoGloveItem extends Item implements ICurioItem {
    public EchoGloveItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.RARE));
    }

    private static final UUID ATTACK_DAMAGE_UUID = UUID.randomUUID();
    private static final UUID ATTACK_SPEED_UUID = UUID.randomUUID();

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = LinkedHashMultimap.create();
        modifiers.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(uuid, String.valueOf(ATTACK_DAMAGE_UUID), CommonConfig.ECHO_GLOVE_DAMAGE.get(), AttributeModifier.Operation.MULTIPLY_BASE));
        modifiers.put(Attributes.ATTACK_SPEED, new AttributeModifier(uuid, String.valueOf(ATTACK_SPEED_UUID), CommonConfig.ECHO_GLOVE_ATTACK_SPEED.get(), AttributeModifier.Operation.MULTIPLY_BASE));

        return modifiers;
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        double ignoreChance = (CommonConfig.ECHO_GLOVE_IGNORE_CHANCE.get() * 100);
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.echo_glove.ignore_chance", String.format("%.1f", ignoreChance)).withStyle(ChatFormatting.DARK_AQUA));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}





