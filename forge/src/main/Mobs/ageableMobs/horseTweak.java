package net.MobAgeTweak.Mobs.Mobs.ageableMobs;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.animal.horse.Horse;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModContainer;
import org.jetbrains.annotations.NotNull;

public class horseTweak implements ageableMobInterface {
    private static int horseAgeCooldown = DEFAULT_COOLDOWN;

    public horseTweak(ModContainer modContainer) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onEntityJoinLevelEvent(@NotNull EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Horse horse && horse.isBaby()) {
            horse.setAge(-getCooldown() * 20);
        }
    }

    @Override
    public int getCooldown() {
        return horseAgeCooldown;
    }

    @Override
    public void setCooldown(int newCooldown) {
        horseAgeCooldown = newCooldown;
    }

    @Override
    public String getName() {
        return Horse.class.getSimpleName();
    }

    public static int handleCommands(CommandContext<CommandSourceStack> context, String command, ModContainer modContainer) {
        horseTweak horseTweakInstance = new horseTweak(modContainer);
        return ageableMobHandleCommands.handleCommands(context, command, horseTweakInstance);
    }
}
