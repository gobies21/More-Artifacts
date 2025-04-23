package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.Config;
import net.gobies.moreartifacts.item.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static net.gobies.moreartifacts.init.MoreArtifactsCurioHandler.isCurioEquipped;

public class GoldenHeadgearItem extends Item implements ICurioItem {
    public GoldenHeadgearItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.UNCOMMON));
    }

    private static final UUID ARMOR_UUID = UUID.randomUUID();

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            var attribute = player.getAttribute(Attributes.ARMOR);
            if (attribute != null) {
                if (attribute.getModifier(ARMOR_UUID) == null && stack.getItem() instanceof GoldenHeadgearItem) {
                    attribute.addTransientModifier(
                            new AttributeModifier(ARMOR_UUID, "Golden Headgear Armor", Config.GOLDEN_HEADGEAR_ARMOR.get(), AttributeModifier.Operation.ADDITION));
                }
            }
        }
    }
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            Objects.requireNonNull(player.getAttribute(Attributes.ARMOR)).removeModifier(ARMOR_UUID);
        }
    }

    static {
        MinecraftForge.EVENT_BUS.register(GoldenHeadgearItem.class);
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (isCurioEquipped(player, ModItems.GoldenHeadgear.get())) {
                if (event.getSource().is(DamageTypes.ARROW)) {
                    event.setAmount((float) (event.getAmount() * Config.GOLDEN_HEADGEAR_ARROW_DAMAGE_TAKEN.get()));
                }
            }
        }
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public ICurio.@NotNull SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_GOLD, 1.0F, 1.0F);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal(String.format("§7+%d Increased armor", Config.GOLDEN_HEADGEAR_ARMOR.get().intValue())));
        pTooltipComponents.add(Component.literal(String.format("§7Reduces arrow damage taken by §3%.1f%%", (1.0 - Config.GOLDEN_HEADGEAR_ARROW_DAMAGE_TAKEN.get()) * 100)));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}








