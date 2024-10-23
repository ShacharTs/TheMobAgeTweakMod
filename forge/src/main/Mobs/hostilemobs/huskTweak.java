package net.MobAgeTweak.Mobs.Mobs.hostilemobs;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.monster.Husk;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModContainer;
import org.jetbrains.annotations.NotNull;


public class huskTweak implements hostilleMobInterface {
    private static boolean onlyBaby = false;
    private static boolean disableBaby = false;

    public huskTweak(ModContainer modContainer) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onEntityJoinLevelEvent(@NotNull EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Husk husk) {
            if (disableBaby) {
                if (husk.isBaby()) {
                    husk.setBaby(false);
                }
            }
            if (onlyBaby) {
                husk.setBaby(true);
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
        return Husk.class.getSimpleName();
    }

    public static int handleCommands(CommandContext<CommandSourceStack> context, String command, ModContainer modContainer) {
        huskTweak huskTweakInstance = new huskTweak(modContainer);
        return hostileMobHandleCommands.handleCommands(context, command, huskTweakInstance);
    }
}