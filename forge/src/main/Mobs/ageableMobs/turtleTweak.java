package net.MobAgeTweak.Mobs.Mobs.ageableMobs;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.animal.Turtle;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModContainer;
import org.jetbrains.annotations.NotNull;

public class turtleTweak implements ageableMobInterface {
    private static int turtleAgeCooldown = DEFAULT_COOLDOWN;

    public turtleTweak(ModContainer modContainer) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onEntityJoinLevelEvent(@NotNull EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Turtle turtle && turtle.isBaby()) {
            turtle.setAge(-getCooldown() * 20);
        }
    }

    @Override
    public int getCooldown() {
        return turtleAgeCooldown;
    }

    @Override
    public void setCooldown(int newCooldown) {
        turtleAgeCooldown = newCooldown;
    }

    @Override
    public String getName() {
        return Turtle.class.getSimpleName();
    }

    public static int handleCommands(CommandContext<CommandSourceStack> context, String command, ModContainer modContainer) {
        turtleTweak turtleTweakInstance = new turtleTweak(modContainer);
        return ageableMobHandleCommands.handleCommands(context, command, turtleTweakInstance);
    }
}
