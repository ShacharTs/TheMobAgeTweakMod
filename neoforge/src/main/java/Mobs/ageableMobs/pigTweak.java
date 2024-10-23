package net.MobAgeTweak.Mobs.ageableMobs;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.animal.Pig;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import org.jetbrains.annotations.NotNull;

public class pigTweak implements ageableMobInterface {
    private static int pigAgeCooldown = DEFAULT_COOLDOWN;

    public pigTweak(ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(this);
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
