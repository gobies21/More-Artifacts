package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.Config;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.util.CurioHandler;
import net.gobies.moreartifacts.util.MAUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import javax.annotation.Nullable;
import java.util.*;

public class MechanicalClawItem extends Item implements ICurioItem {
    public MechanicalClawItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.RARE));
    }

    static {
        MinecraftForge.EVENT_BUS.register(MechanicalClawItem.class);
    }

    private static final UUID ATTACK_DAMAGE_UUID = UUID.randomUUID();
    
    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            MAUtils.addAttributes(player, Attributes.ATTACK_DAMAGE, Config.MECHANICAL_CLAW_DAMAGE.get(), AttributeModifier.Operation.MULTIPLY_BASE, String.valueOf(ATTACK_DAMAGE_UUID));

        }
    }

    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            MAUtils.removeAttributes(player, Attributes.ATTACK_DAMAGE, String.valueOf(ATTACK_DAMAGE_UUID));
        }
    }
    
    private static final Map<UUID, Integer> BLEEDING_ENTITIES = new HashMap<>();

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        if (!(event.getSource().getDirectEntity() instanceof Player player))
            return;

        if (!event.getSource().is(DamageTypes.PLAYER_ATTACK))
            return;

        LivingEntity target = event.getEntity();
        if (target instanceof AbstractSkeleton || target instanceof AbstractGolem || target instanceof Slime)
            return;

        if (CurioHandler.isCurioEquipped(player, MAItems.MechanicalClaw.get())) {
            UUID targetId = target.getUUID();
            if (target.getRandom().nextFloat() < Config.MECHANICAL_CLAW_BLEED_CHANCE.get()) {
                BLEEDING_ENTITIES.put(targetId, Config.MECHANICAL_CLAW_BLEED_DURATION.get() * 20);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.level().isClientSide) return;

        UUID entityId = entity.getUUID();
        if (BLEEDING_ENTITIES.containsKey(entityId)) {
            int remainingTicks = BLEEDING_ENTITIES.get(entityId);
            if (remainingTicks <= 0) {
                BLEEDING_ENTITIES.remove(entityId);
                return;
            }

            if (remainingTicks % 20 == 0) {
                DamageSource bleedDamage = new DamageSource(entity.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.MAGIC));
                int prevInvulnerableTime = entity.invulnerableTime;
                entity.hurt(bleedDamage, Config.MECHANICAL_CLAW_BLEED_DAMAGE.get());
                entity.invulnerableTime = prevInvulnerableTime;
            }

            BLEEDING_ENTITIES.put(entityId, remainingTicks - 1);
        }
    }

        @Override
        public boolean canEquipFromUse (SlotContext slotContext, ItemStack stack){
            return true;
        }

        @Override
        public void appendHoverText (@NotNull ItemStack pStack, @Nullable Level pLevel, List < Component > pTooltipComponents, @NotNull TooltipFlag pIsAdvanced){
            double clawDamage = (Config.MECHANICAL_CLAW_DAMAGE.get() * 100);
            double bleedChance = (Config.MECHANICAL_CLAW_BLEED_CHANCE.get() * 100);
            int bleedDamage = (Config.MECHANICAL_CLAW_BLEED_DAMAGE.get());
            int bleedDuration = (Config.MECHANICAL_CLAW_BLEED_DURATION.get());
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.mechanical_claw.damage", String.format("%.1f", clawDamage)).withStyle(ChatFormatting.DARK_AQUA));
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.mechanical_claw.bleed_chance", String.format("%.1f", bleedChance)).withStyle(ChatFormatting.DARK_AQUA));
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.mechanical_claw.bleed_damage_duration", bleedDamage, bleedDuration).withStyle(ChatFormatting.DARK_AQUA));
            super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        }
    }


