package net.MobAgeTweak.Mobs.Mobs.ageableMobs;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.animal.Pig;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModContainer;
import org.jetbrains.annotations.NotNull;

public class pigTweak implements ageableMobInterface {
    private static int pigAgeCooldown = DEFAULT_COOLDOWN;

    public pigTweak(ModContainer modContainer) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onEntityJoinLevelEvent(@NotNull EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Pig pig && pig.isBaby()) {
            pig.setAge(-getCooldown() * 20);
        }
    }

    @Override
    public int getCooldown() {
        return pigAgeCooldown;
    }

    @Override
    public void setCooldown(int newCooldown) {
        pigAgeCooldown = newCooldown;
    }

    @Override
    public String getName() {
        return Pig.class.getSimpleName();
    }

    public static int handleCommands(CommandContext<CommandSourceStack> context, String command, ModContainer modContainer) {
        pigTweak pigTweakInstance = new pigTweak(modContainer);
        return ageableMobHandleCommands.handleCommands(context, command, pigTweakInstance);
    }
}
