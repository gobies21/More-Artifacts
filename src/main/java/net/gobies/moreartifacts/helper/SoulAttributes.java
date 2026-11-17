package net.gobies.moreartifacts.helper;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.gobies.moreartifacts.MoreArtifacts;
import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.util.ManaUtil;
import net.gobies.moreartifacts.util.SoulUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SoulAttributes {

    // Config driven soul attributes
    public record AttributeHolder(Attribute attribute, String attributeId, UUID uuid, double base, double increment, double maxStageValue, AttributeModifier.Operation operation) {
        public double getValueForStage(int stage) {
            if (stage >= 5 && !Double.isNaN(maxStageValue)) {
                return maxStageValue;
            }
            return base + (increment * (stage - 1));
        }
    }

    public static List<AttributeHolder> ENVY_ATTRIBUTES = new ArrayList<>();
    public static List<AttributeHolder> WRATH_ATTRIBUTES = new ArrayList<>();

    public static void bakeConfig() {
        ENVY_ATTRIBUTES = parse(CommonConfig.ENVY_ATTRIBUTES.get(), SoulType.ENVY.key);
        WRATH_ATTRIBUTES = parse(CommonConfig.WRATH_ATTRIBUTES.get(), SoulType.WRATH.key);
    }

    private static List<AttributeHolder> parse(List<? extends String> entries, String prefix) {
        List<AttributeHolder> holderList = new ArrayList<>();
        if (entries == null) return holderList;

        for (String entry : entries) {
            String[] tokens = entry.split(";");
            if (tokens.length < 4) continue;

            String attributeId = tokens[0].trim();
            ResourceLocation location = ResourceLocation.tryParse(attributeId);
            if (location == null) continue;

            Attribute attribute = ForgeRegistries.ATTRIBUTES.getValue(location);
            if (attribute == null) continue;

            try {
                double base = Double.parseDouble(tokens[1].trim());
                double increment = Double.parseDouble(tokens[2].trim());
                AttributeModifier.Operation operation;

                double maxLevelValue = Double.NaN;
                if (tokens.length >= 5) {
                    maxLevelValue = Double.parseDouble(tokens[3].trim());
                    operation = AttributeModifier.Operation.valueOf(tokens[4].trim().toUpperCase());
                } else {
                    operation = AttributeModifier.Operation.valueOf(tokens[3].trim().toUpperCase());
                }
                UUID uuid = UUID.nameUUIDFromBytes((prefix + attributeId).getBytes(StandardCharsets.UTF_8));

                holderList.add(new AttributeHolder(attribute, attributeId, uuid, base, increment, maxLevelValue, operation));
            } catch (IllegalArgumentException e) {
                MoreArtifacts.LOGGER.error("Failed to parse soul config attributes");
            }
        }
        return holderList;
    }

    private static void processConfig(List<AttributeHolder> attributeList, AttributeProcessor processor) {
        if (attributeList == null) return;

        for (AttributeHolder holder : attributeList) {
            double value = holder.getValueForStage(5);

            processor.accept(holder.attribute, holder.uuid, holder.attributeId, value, holder.operation);
        }
    }

    private static void removeConfigAttributes(Player player, List<AttributeHolder> attributeList) {
        if (attributeList == null || attributeList.isEmpty()) return;
        for (AttributeHolder holder : attributeList) {
            AttributeInstance instance = player.getAttribute(holder.attribute);
            if (instance != null) {
                AttributeModifier modifier = instance.getModifier(holder.uuid);
                if (modifier != null) {
                    instance.removeModifier(modifier);
                }
            }
        }
    }

    public static Multimap<Attribute, AttributeModifier> getCurioModifiers(UUID uuid, ItemStack stack, List<AttributeHolder> attributeHolder, SoulType type) {
        Multimap<Attribute, AttributeModifier> modifiers = LinkedHashMultimap.create();
        if (attributeHolder == null || attributeHolder.isEmpty()) return modifiers;

        int soulStage = SoulUtil.getSoulStage(stack);
        String uuidString = uuid.toString();

        for (AttributeHolder holder : attributeHolder) {
            double finalValue = holder.getValueForStage(soulStage);

            UUID curioUuid = UUID.nameUUIDFromBytes((type.key + uuidString + ":" + holder.attributeId).getBytes(StandardCharsets.UTF_8));

            modifiers.put(holder.attribute, new AttributeModifier(curioUuid, "Curio Soul Bonus: " + holder.attributeId, finalValue, holder.operation));
        }

        return modifiers;
    }

    public static void manageEnvyAttributes(Player player) {
        processConfig(ENVY_ATTRIBUTES, (attribute, uuid, name, value, operation) -> {
            AttributeInstance instance = player.getAttribute(attribute);
            if (instance != null) {
                AttributeModifier existing = instance.getModifier(uuid);
                if (existing == null || existing.getAmount() != value || existing.getOperation() != operation) {
                    instance.removeModifier(uuid);
                    instance.addPermanentModifier(new AttributeModifier(uuid, "Envy: " + name, value, operation));
                }
            }
        });
    }

    public static void manageWrathAttributes(Player player) {
        processConfig(WRATH_ATTRIBUTES, (attribute, uuid, name, value, operation) -> {
            AttributeInstance instance = player.getAttribute(attribute);
            if (instance != null) {
                AttributeModifier existing = instance.getModifier(uuid);
                if (existing == null || existing.getAmount() != value || existing.getOperation() != operation) {
                    instance.removeModifier(uuid);
                    instance.addPermanentModifier(new AttributeModifier(uuid, "Wrath: " + name, value, operation));
                }
            }
        });
    }

    public static void removeEnvyAttributes(ServerPlayer serverPlayer) {
        removeConfigAttributes(serverPlayer, ENVY_ATTRIBUTES);

        if (serverPlayer.hasEffect(MobEffects.INVISIBILITY)) {
            serverPlayer.removeEffect(MobEffects.INVISIBILITY);
        }

        serverPlayer.getAbilities().flying = false;
        serverPlayer.getAbilities().mayfly = false;
        serverPlayer.onUpdateAbilities();

        ManaUtil.setMana(serverPlayer, ManaUtil.getMaxMana(serverPlayer));
    }

    public static void removeWrathAttributes(ServerPlayer serverPlayer) {
        removeConfigAttributes(serverPlayer, WRATH_ATTRIBUTES);

        if (serverPlayer.hasEffect(MobEffects.DAMAGE_BOOST)) {
            serverPlayer.removeEffect(MobEffects.DAMAGE_BOOST);
        }
    }

    @FunctionalInterface
    private interface AttributeProcessor {
        void accept(Attribute attribute, UUID uuid, String name, double value, AttributeModifier.Operation operation);
    }
}