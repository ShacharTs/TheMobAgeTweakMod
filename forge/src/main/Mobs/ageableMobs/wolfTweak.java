package net.MobAgeTweak.Mobs.Mobs.ageableMobs;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.animal.Wolf;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModContainer;
import org.jetbrains.annotations.NotNull;

public class wolfTweak implements ageableMobInterface {
    private static int wolfAgeCooldown = DEFAULT_COOLDOWN;

    public wolfTweak(ModContainer modContainer) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onEntityJoinLevelEvent(@NotNull EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Wolf wolf && wolf.isBaby()) {
            wolf.setAge(-getCooldown() * 20);
        }
    }

    @Override
    public int getCooldown() {
        return wolfAgeCooldown;
    }

    @Override
    public void setCooldown(int newCooldown) {
        wolfAgeCooldown = newCooldown;
    }

    @Override
    public String getName() {
        return Wolf.class.getSimpleName();
    }

    public static int handleCommands(CommandContext<CommandSourceStack> context, String command, ModContainer modContainer) {
        wolfTweak wolfTweakInstance = new wolfTweak(modContainer);
        return ageableMobHandleCommands.handleCommands(context, command, wolfTweakInstance);
    }
}
