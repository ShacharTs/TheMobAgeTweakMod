package net.MobAgeTweak.Mobs.Mobs.hostilemobs;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;


public interface hostilleMobInterface {
    void setDisableBaby(boolean bool);

    boolean getDisableBaby();

    boolean getOnlyBaby();

    void setOnlyBaby(boolean bool);


    String getName();

    static int handleCommands(CommandContext<CommandSourceStack> context, String command) {
        return 0;
    }
}


