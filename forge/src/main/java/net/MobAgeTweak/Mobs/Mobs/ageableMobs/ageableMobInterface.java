package net.MobAgeTweak.Mobs.Mobs.ageableMobs;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;

public interface ageableMobInterface {
    String MOD_ID = "mobagetweak";
    int DEFAULT_COOLDOWN = 1200;
    int getCooldown();
    void setCooldown(int newCooldown);
     String getName();
     void loadConfig();
     void saveConfig();





    static int handleCommands(CommandContext<CommandSourceStack> context, String command, ageableMobInterface mobHandler) {
        return ageableMobHandleCommands.handleCommands(context, command, mobHandler);
    }

}
