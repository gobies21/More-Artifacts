package net.gobies.moreartifacts.item.artifacts;

import com.google.common.collect.Maps;
import net.gobies.moreartifacts.Config;
import net.gobies.moreartifacts.MoreArtifacts;
import net.gobies.moreartifacts.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import javax.annotation.Nullable;
import java.util.*;

public class MechanicalClawItem extends Item implements ICurioItem {
    public MechanicalClawItem(Properties properties) {
        super(new Properties().stacksTo(1).rarity(Rarity.RARE));
    }

    private static final Map<Entity, Long> hitEntities = Maps.newHashMap();

    static {
        MinecraftForge.EVENT_BUS.register(MechanicalClawItem.class);
    }

    private static final UUID ATTACK_DAMAGE_UUID = UUID.randomUUID();

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        if (livingEntity instanceof Player entity) {
            var attribute = entity.getAttribute(Attributes.ATTACK_DAMAGE);
            if (attribute != null) {
                if (attribute.getModifier(ATTACK_DAMAGE_UUID) == null && stack.getItem() instanceof MechanicalClawItem) {
                    attribute.addTransientModifier(
                            new AttributeModifier(ATTACK_DAMAGE_UUID, "Mechanical Claw Attack Multiply", Config.MECHANICAL_CLAW_DAMAGE.get(), AttributeModifier.Operation.MULTIPLY_BASE));
                }
            }
        }
    }

    public void onUnequip(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        if (livingEntity instanceof Player entity) {
            Objects.requireNonNull(entity.getAttribute(Attributes.ATTACK_DAMAGE)).removeModifier(ATTACK_DAMAGE_UUID);
        }

    }

    //needs refining
    @SubscribeEvent
    public static void onEntityAttacked(LivingHurtEvent event) {
        if (event != null && event.getEntity() != null) {
            execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity(), event.getSource().getEntity());
        }
    }

    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
        execute(null, world, x, y, z, entity, sourceentity);
    }

    private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
        if (entity == null || sourceentity == null)
            return;
        if (sourceentity instanceof LivingEntity lv && CuriosApi.getCuriosHelper().findEquippedCurio(ModItems.MechanicalClaw.get(), lv).isPresent()) {
            if (!(entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("minecraft:skeletons"))) || entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("minecraft:frog_food")))
                    || entity instanceof AbstractGolem || entity instanceof SkeletonHorse)) {
                RandomSource random = ((LivingEntity) sourceentity).getRandom();
                if (random.nextFloat() < Config.MECHANICAL_CLAW_BLEED_CHANCE.get()) {
                    if (world instanceof ServerLevel level) {
                        MoreArtifacts.queueServerWork(20, () -> {
                            new Object() {
                                void timedLoop(int transliteration, int localtimestamp, int ticks) {
                                    if (entity.isAlive()) {
                                        if (world instanceof ServerLevel level) {
                                            entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.MAGIC)), Config.MECHANICAL_CLAW_BLEED_DAMAGE.get());
                                        }
                                    }
                                    final int tick2 = ticks;
                                    MoreArtifacts.queueServerWork(tick2, () -> {
                                        if (localtimestamp > transliteration + 1) {
                                            timedLoop(transliteration + 1, localtimestamp, tick2);
                                        }
                                    });
                                }
                            }.timedLoop(0, Config.MECHANICAL_CLAW_BLEED_DURATION.get(), 20);
                        });
                    }
                }
            }
        }
    }
        @Override
        public boolean canEquipFromUse (SlotContext slotContext, ItemStack stack){
            return true;
        }

        @Override
        public void appendHoverText (@NotNull ItemStack pStack, @Nullable Level pLevel, List < Component > pTooltipComponents, @NotNull TooltipFlag pIsAdvanced){
            pTooltipComponents.add(Component.literal(String.format("§3%.1f%% §6Increased melee damage dealt", (Config.MECHANICAL_CLAW_DAMAGE.get() * 100))));
            pTooltipComponents.add(Component.literal(String.format("§6Attacks have a §3%.1f%% §6chance to inflict §cBleed §6on hit", (Config.MECHANICAL_CLAW_BLEED_CHANCE.get() * 100))));
            pTooltipComponents.add(Component.literal(String.format("§cBleed §6deals §3%d §6damage every second for §3%d §6seconds", (Config.MECHANICAL_CLAW_BLEED_DAMAGE.get()), (Config.MECHANICAL_CLAW_BLEED_DURATION.get()))));
            super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        }
    }

