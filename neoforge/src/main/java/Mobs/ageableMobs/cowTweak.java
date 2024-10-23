package net.MobAgeTweak.Mobs.ageableMobs;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.animal.Cow;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import org.jetbrains.annotations.NotNull;

public class cowTweak implements ageableMobInterface {
    public static int cowAgeCooldown = DEFAULT_COOLDOWN;

    public cowTweak(ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onEntityJoinLevelEvent(@NotNull EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Cow cow && cow.isBaby()) {
            cow.setAge(-getCooldown() * 20);
        }
    }

    @Override
    public int getCooldown() {
        return cowAgeCooldown;
    }

    @Override
    public void setCooldown(int newCooldown) {
        cowAgeCooldown = newCooldown;
    }

    @Override
    public String getName() {
        return Cow.class.getSimpleName();
    }



    public static int handleCommands(CommandContext<CommandSourceStack> context, String command, ModContainer modContainer) {
        cowTweak cowTweakInstance = new cowTweak(modContainer);
        return ageableMobHandleCommands.handleCommands(context, command, cowTweakInstance);
    }
}
