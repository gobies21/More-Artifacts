package net.gobies.moreartifacts.init;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.gobies.moreartifacts.MoreArtifacts;
import net.gobies.moreartifacts.util.BrokenHeartSystem;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
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
            AttributeModifier modifier = attribute.getModifier(BrokenHeartSystem.BROKEN_HEART_HEALTH_UUID);
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
}