package net.MobAgeTweak.Mobs.Mobs.ageableMobs;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.animal.Rabbit;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModContainer;
import org.jetbrains.annotations.NotNull;

public class rabbitTweak implements ageableMobInterface {
    private static int rabbitAgeCooldown = DEFAULT_COOLDOWN;

    public rabbitTweak(ModContainer modContainer) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onEntityJoinLevelEvent(@NotNull EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Rabbit rabbit && rabbit.isBaby()) {
            rabbit.setAge(-getCooldown() * 20);
        }
    }

    @Override
    public int getCooldown() {
        return rabbitAgeCooldown;
    }

    @Override
    public void setCooldown(int newCooldown) {
        rabbitAgeCooldown = newCooldown;
    }

    @Override
    public String getName() {
        return Rabbit.class.getSimpleName();
    }

    public static int handleCommands(CommandContext<CommandSourceStack> context, String command, ModContainer modContainer) {
        rabbitTweak rabbitTweakInstance = new rabbitTweak(modContainer);
        return ageableMobHandleCommands.handleCommands(context, command, rabbitTweakInstance);
    }
}
