package net.MobAgeTweak.Mobs.Mobs.ageableMobs;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModContainer;
import org.jetbrains.annotations.NotNull;

public class skeletonHorseTweak implements ageableMobInterface {
    private static int skeletonHorseAgeCooldown = DEFAULT_COOLDOWN;

    public skeletonHorseTweak(ModContainer modContainer) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onEntityJoinLevelEvent(@NotNull EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof SkeletonHorse skeletonHorse && skeletonHorse.isBaby()) {
            skeletonHorse.setAge(-getCooldown() * 20);
        }
    }

    @Override
    public int getCooldown() {
        return skeletonHorseAgeCooldown;
    }

    @Override
    public void setCooldown(int newCooldown) {
        skeletonHorseAgeCooldown = newCooldown;
    }

    @Override
    public String getName() {
        return SkeletonHorse.class.getSimpleName();
    }

    public static int handleCommands(CommandContext<CommandSourceStack> context, String command, ModContainer modContainer) {
        skeletonHorseTweak skeletonHorseTweakInstance = new skeletonHorseTweak(modContainer);
        return ageableMobHandleCommands.handleCommands(context, command, skeletonHorseTweakInstance);
    }
}
