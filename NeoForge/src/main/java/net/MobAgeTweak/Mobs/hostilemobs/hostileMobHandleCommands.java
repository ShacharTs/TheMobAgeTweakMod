package net.MobAgeTweak.Mobs.hostilemobs;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.neoforged.fml.ModContainer;

import static net.MobAgeTweak.mobAgeTweakMain.*;

public class hostileMobHandleCommands {
    public static final String MOD_ID = "mobagetweak";
    public hostileMobHandleCommands(ModContainer modContainer) {
    }

    public static int handleCommands(CommandContext<CommandSourceStack> context, String command, hostilleMobInterface mob) {
        switch (command) {
            case disableBaby:
                mob.setDisableBaby(true);
                mob.setOnlyBaby(false);
                context.getSource().sendSystemMessage(Component.literal("Baby " + mob.getName() + " will no longer spawn")
                        .withStyle(ChatFormatting.GREEN));
                return Command.SINGLE_SUCCESS;

            case enableBaby:
                mob.setDisableBaby(false);
                context.getSource().sendSystemMessage(Component.literal("Baby " + mob.getName() + " will spawn")
                        .withStyle(ChatFormatting.DARK_RED));
                return Command.SINGLE_SUCCESS;

            case enableOnlyBaby:
                mob.setDisableBaby(false);
                mob.setOnlyBaby(true);
                context.getSource().sendSystemMessage(Component.literal("WARNING: Only baby " + mob.getName() + " will spawn")
                        .withStyle(ChatFormatting.DARK_RED));
                return Command.SINGLE_SUCCESS;

            case disableOnlyBaby:
                mob.setOnlyBaby(false);
                context.getSource().sendSystemMessage(Component.literal("Adult and baby " + mob.getName() + " will spawn")
                        .withStyle(ChatFormatting.GREEN));
                return Command.SINGLE_SUCCESS;

            default:
                context.getSource().sendSystemMessage(Component.literal("Unknown command: " + command).withStyle(ChatFormatting.RED));
                return Command.SINGLE_SUCCESS;
        }
    }


}
