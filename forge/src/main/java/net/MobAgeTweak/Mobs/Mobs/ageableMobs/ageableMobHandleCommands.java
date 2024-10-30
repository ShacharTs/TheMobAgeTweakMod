package net.MobAgeTweak.Mobs.Mobs.ageableMobs;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraftforge.fml.ModContainer;

import static net.MobAgeTweak.Mobs.Mobs.ageableMobs.ageableMobInterface.DEFAULT_COOLDOWN;
import static net.MobAgeTweak.Mobs.mobAgeTweakMain.*;


public class ageableMobHandleCommands {
    public static final String MOD_ID = "mobagetweak";
    int cooldown;

    public ageableMobHandleCommands(ModContainer modContainer) {
    }

    public int getCooldown(int cooldown){
        return cooldown;
    }
    public void setCooldown(int newCooldown){
        cooldown = newCooldown;
    }


    public static int handleCommands(CommandContext<CommandSourceStack> context, String command, ageableMobInterface mob) {
        switch (command) {
            case change:
                int newCooldown = IntegerArgumentType.getInteger(context, "seconds");
                mob.setCooldown(newCooldown);
                context.getSource().sendSystemMessage(Component.literal(String.format("Cooldown for %s changed to %d seconds", mob.getName(), newCooldown))
                        .withStyle(ChatFormatting.GREEN));
                return Command.SINGLE_SUCCESS;

            case check:
                context.getSource().sendSystemMessage(Component.literal("Current cooldown for " + mob.getName() + " is " + mob.getCooldown() + " seconds")
                        .withStyle(ChatFormatting.DARK_GREEN));
                return Command.SINGLE_SUCCESS;

            case reset:
                mob.setCooldown(DEFAULT_COOLDOWN);
                context.getSource().sendSystemMessage(Component.literal("Cooldown for " + mob.getName() + " has been reset to default (20 mins)")
                        .withStyle(ChatFormatting.DARK_RED));
                return Command.SINGLE_SUCCESS;

            default:
                context.getSource().sendSystemMessage(Component.literal("Unknown command: " + command).withStyle(ChatFormatting.RED));
                return Command.SINGLE_SUCCESS;
        }
    }
}
