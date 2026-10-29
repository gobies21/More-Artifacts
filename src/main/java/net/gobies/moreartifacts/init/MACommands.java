package net.gobies.moreartifacts.init;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.gobies.moreartifacts.MoreArtifacts;
import net.gobies.moreartifacts.helper.SoulAttributes;
import net.gobies.moreartifacts.helper.SoulType;
import net.gobies.moreartifacts.network.ManageRequests;
import net.gobies.moreartifacts.helper.SoulSyncHelper;
import net.gobies.moreartifacts.util.BrokenHeartSystem;
import net.gobies.moreartifacts.util.SoulUtil;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Locale;

@Mod.EventBusSubscriber(modid = MoreArtifacts.MOD_ID)
public class MACommands {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        dispatcher.register(
                Commands.literal("moreartifacts")
                        .requires(source -> source.hasPermission(2)) // OP
                        .then(Commands.literal("broken_heart")
                                // Sub Command: add
                                .then(Commands.literal("add")
                                        .then(Commands.argument("amount", IntegerArgumentType.integer(1)) // Minimum 1 heart
                                                .executes(ctx -> modifyHearts(ctx.getSource(), ctx.getSource().getPlayerOrException(), IntegerArgumentType.getInteger(ctx, "amount"), true))
                                                .then(Commands.argument("target", EntityArgument.player())
                                                        .executes(ctx -> modifyHearts(ctx.getSource(), EntityArgument.getPlayer(ctx, "target"), IntegerArgumentType.getInteger(ctx, "amount"), true))
                                                )
                                        )
                                )
                                // Sub Command: remove
                                .then(Commands.literal("remove")
                                        .then(Commands.argument("amount", IntegerArgumentType.integer(1))
                                                .executes(ctx -> modifyHearts(ctx.getSource(), ctx.getSource().getPlayerOrException(), IntegerArgumentType.getInteger(ctx, "amount"), false))
                                                .then(Commands.argument("target", EntityArgument.player())
                                                        .executes(ctx -> modifyHearts(ctx.getSource(), EntityArgument.getPlayer(ctx, "target"), IntegerArgumentType.getInteger(ctx, "amount"), false))
                                                )
                                        )
                                )
                                // Sub Command: clear
                                .then(Commands.literal("clear")
                                        .executes(ctx -> clearHearts(ctx.getSource(), ctx.getSource().getPlayerOrException()))
                                        .then(Commands.argument("target", EntityArgument.player())
                                                .executes(ctx -> clearHearts(ctx.getSource(), EntityArgument.getPlayer(ctx, "target")))
                                        )
                                )
                        )
                        .then(Commands.literal("soul")
                                // Sub Command: add
                                .then(Commands.literal("add")
                                        .then(Commands.argument("soulType", StringArgumentType.word())
                                                .suggests((ctx, builder) -> builder.suggest("Envy").suggest("Wrath").buildFuture())
                                                .executes(ctx -> modifySoulData(ctx.getSource(), ctx.getSource().getPlayerOrException(), SoulType.valueOf(StringArgumentType.getString(ctx, "soulType").toUpperCase(Locale.ROOT)), true))
                                                .then(Commands.argument("target", EntityArgument.player())
                                                        .executes(ctx -> modifySoulData(ctx.getSource(), EntityArgument.getPlayer(ctx, "target"), SoulType.valueOf(StringArgumentType.getString(ctx, "soulType").toUpperCase(Locale.ROOT)), true))
                                                )
                                        )
                                )
                                // Sub Command: remove
                                .then(Commands.literal("remove")
                                        .then(Commands.argument("soulType", StringArgumentType.word())
                                                .suggests((ctx, builder) -> builder.suggest("Envy").suggest("Wrath").buildFuture())
                                                .executes(ctx -> modifySoulData(ctx.getSource(), ctx.getSource().getPlayerOrException(), SoulType.valueOf(StringArgumentType.getString(ctx, "soulType").toUpperCase(Locale.ROOT)), false))
                                                .then(Commands.argument("target", EntityArgument.player())
                                                        .executes(ctx -> modifySoulData(ctx.getSource(), EntityArgument.getPlayer(ctx, "target"), SoulType.valueOf(StringArgumentType.getString(ctx, "soulType").toUpperCase(Locale.ROOT)), false))
                                                )
                                        )
                                )
                        )
        );
        // Invis accept command

        dispatcher.register(Commands.literal("wormhole_accept")
                .requires(source -> true)
                .executes(ctx -> {
                    if (ctx.getSource().getEntity() instanceof ServerPlayer player) {
                        ManageRequests.handleResponse(player, true);
                    }
                    return 1;
                })
        );
        // Invis deny command
        dispatcher.register(Commands.literal("wormhole_deny")
                .requires(source -> true)
                .executes(ctx -> {
                    if (ctx.getSource().getEntity() instanceof ServerPlayer player) {
                        ManageRequests.handleResponse(player, false);
                    }
                    return 1;
                })
        );
    }

    private static int modifyHearts(CommandSourceStack source, ServerPlayer target, int amount, boolean isAdding) {
        if (isAdding) {
            AttributeInstance attribute = target.getAttribute(Attributes.MAX_HEALTH);
            if (attribute == null) {
                source.sendFailure(Component.literal("Failed to fetch max health attribute for " + target.getScoreboardName()));
                return 0;
            }

            int currentBrokenHearts = BrokenHeartSystem.getBrokenHearts(target);
            AttributeModifier modifier = attribute.getModifier(BrokenHeartSystem.BROKEN_HEART_HEALTH);
            double currentModifierValue = modifier != null ? Math.abs(modifier.getAmount()) : 0.0;

            float maxHealth = target.getMaxHealth() + (float) currentModifierValue;
            int totalPossibleContainers = (int) Math.ceil(maxHealth / 2.0F);

            int allowedMaxBrokenHearts = totalPossibleContainers - 1;
            int requestedTotal = currentBrokenHearts + amount;

            if (currentBrokenHearts >= allowedMaxBrokenHearts) {
                source.sendFailure(Component.literal(target.getScoreboardName() + " has already reached the maximum allowed of Broken Hearts"));
                return 0;
            }

            if (requestedTotal > allowedMaxBrokenHearts) {
                amount = allowedMaxBrokenHearts - currentBrokenHearts;
                source.sendSuccess(() -> Component.literal("Clamped value to not exceed Max Health"), false);
            }

            BrokenHeartSystem.addBrokenHearts(target, amount);

            final int finalAmount = amount;
            source.sendSuccess(() -> Component.literal("Added " + finalAmount + " Broken Hearts to " + target.getScoreboardName()), true);
        } else {
            BrokenHeartSystem.removeBrokenHearts(target, amount);

            final int finalAmount1 = amount;
            source.sendSuccess(() -> Component.literal("Removed " + finalAmount1 + " Broken Hearts from " + target.getScoreboardName()), true);
        }

        AttributeInstance attribute = target.getAttribute(Attributes.MAX_HEALTH);
        if (attribute != null) {
            attribute.setBaseValue(attribute.getBaseValue());
        }

        return 1;
    }

    private static int clearHearts(CommandSourceStack source, ServerPlayer target) {
        BrokenHeartSystem.clearBrokenHearts(target);

        AttributeInstance attribute = target.getAttribute(Attributes.MAX_HEALTH);
        if (attribute != null) {
            attribute.setBaseValue(attribute.getBaseValue());
        }

        source.sendSuccess(() -> Component.literal("Cleared all broken hearts from " + target.getScoreboardName()), true);
        return 1;
    }

    private static int modifySoulData(CommandSourceStack source, ServerPlayer target, SoulType soulType, boolean isAdding) {
        CompoundTag persistentData = target.getPersistentData();

        if (isAdding) {
            if (persistentData.contains(SoulUtil.SOUL_KEY)) {
                source.sendFailure(Component.literal(target.getScoreboardName() + " already has an active soul"));
                return 0;
            }
            switch (soulType) {
                case ENVY -> {
                    persistentData.putString(SoulUtil.SOUL_KEY, SoulType.ENVY.key);
                    persistentData.putBoolean(SoulUtil.ABILITY_TOGGLE, true);
                }
                case WRATH -> {
                    persistentData.putString(SoulUtil.SOUL_KEY, SoulType.WRATH.key);
                    // TODO
                }
                default -> {
                    source.sendFailure(Component.literal("Invalid soul type! Use Suggestions"));
                    return 0;
                }
            }

            SoulSyncHelper.syncSouls(target);
            source.sendSuccess(() -> Component.literal("Successfully gave soul data to " + target.getScoreboardName()), true);
        } else {
            String activeSoul = persistentData.getString(SoulUtil.SOUL_KEY);
            persistentData.remove(SoulUtil.SOUL_KEY);

            if (SoulType.ENVY.key.equals(activeSoul)) {
                SoulAttributes.removeEnvyAttributes(target);
            } else if (SoulType.WRATH.key.equals(activeSoul)) {
                SoulAttributes.removeWrathAttributes(target);
            }
            source.sendSuccess(() -> Component.literal("Successfully removed soul data from " + target.getScoreboardName()), true);
        }

        SoulSyncHelper.syncSouls(target);
        return 1;
    }
}