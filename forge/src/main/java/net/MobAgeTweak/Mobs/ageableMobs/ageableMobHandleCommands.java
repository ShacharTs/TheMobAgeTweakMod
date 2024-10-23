package net.MobAgeTweak.Mobs.Mobs.ageableMobs;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModContainer;

public class ageableMobHandleCommands {
    public static final int DEFAULT_COOLDOWN = 1200;
    public static final String MOD_ID = "mobagetweak";
    int cooldown =0;

    public ageableMobHandleCommands(ModContainer modContainer) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public int getCooldown(int cooldown){
        return cooldown;
    }
    public void setCooldown(int newCooldown){
        cooldown = newCooldown;
    }


    public static int handleCommands(CommandContext<CommandSourceStack> context, String command, ageableMobInterface mob) {
        switch (command) {
            case "changecooldown":
                int newCooldown = IntegerArgumentType.getInteger(context, "seconds");
                mob.setCooldown(newCooldown);
                context.getSource().sendSystemMessage(Component.literal(String.format("Cooldown for %s changed to %d seconds", mob.getName(), newCooldown))
                        .withStyle(ChatFormatting.GREEN));
                return Command.SINGLE_SUCCESS;

            case "checkcooldown":
                context.getSource().sendSystemMessage(Component.literal("Current cooldown for " + mob.getName() + " is " + mob.getCooldown() + " seconds")
                        .withStyle(ChatFormatting.DARK_GREEN));
                return Command.SINGLE_SUCCESS;

            case "resetcooldown":
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
