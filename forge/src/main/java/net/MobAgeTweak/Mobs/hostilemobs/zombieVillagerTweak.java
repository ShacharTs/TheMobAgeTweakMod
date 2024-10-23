package net.MobAgeTweak.Mobs.Mobs.hostilemobs;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.monster.ZombieVillager;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModContainer;
import org.jetbrains.annotations.NotNull;


public class zombieVillagerTweak implements hostilleMobInterface {
    private static boolean onlyBaby = false;
    private static boolean disableBaby = false;

    public zombieVillagerTweak(ModContainer modContainer) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onEntityJoinLevelEvent(@NotNull EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof ZombieVillager zombieVillager) {
            if (disableBaby) {
                if (zombieVillager.isBaby()) {
                    zombieVillager.setBaby(false);
                }
            }
            if (onlyBaby) {
                zombieVillager.setBaby(true);
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
        return ZombieVillager.class.getSimpleName();
    }

    public static int handleCommands(CommandContext<CommandSourceStack> context, String command, ModContainer modContainer) {
        zombieVillagerTweak zombieVillagerTweakInstance = new zombieVillagerTweak(modContainer);
        return hostileMobHandleCommands.handleCommands(context, command, zombieVillagerTweakInstance);
    }
}
