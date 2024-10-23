package net.MobAgeTweak.Mobs.Mobs.hostilemobs;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.monster.piglin.Piglin;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModContainer;
import org.jetbrains.annotations.NotNull;


public class piglinTweak implements hostilleMobInterface {
    private static boolean onlyBaby = false;
    private static boolean disableBaby = false;

    public piglinTweak(ModContainer modContainer) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onEntityJoinLevelEvent(@NotNull EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Piglin piglin) {
            if (disableBaby) {
                if (piglin.isBaby()) {
                    piglin.setBaby(false);
                }
            }
            if (onlyBaby) {
                piglin.setBaby(true);
            }
        }
    }

    @Override
    public void setDisableBaby(boolean bool) {
        disableBaby = bool;
    }

    @Override
    public boolean getDisableBaby() {
        return disableBaby;
    }

    @Override
    public boolean getOnlyBaby() {
        return onlyBaby;
    }

    @Override
    public void setOnlyBaby(boolean bool) {
        onlyBaby = bool;
    }

    @Override
    public String getName() {
        return Piglin.class.getSimpleName();
    }

    public static int handleCommands(CommandContext<CommandSourceStack> context, String command, ModContainer modContainer) {
        piglinTweak piglinTweakInstance = new piglinTweak(modContainer);
        return hostileMobHandleCommands.handleCommands(context, command, piglinTweakInstance);
    }
}