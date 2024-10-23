package net.MobAgeTweak.Mobs.ageableMobs;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.animal.Ocelot;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import org.jetbrains.annotations.NotNull;

public class ocelotTweak implements ageableMobInterface {
    private static int ocelotAgeCooldown = DEFAULT_COOLDOWN;

    public ocelotTweak(ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onEntityJoinLevelEvent(@NotNull EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Ocelot ocelot && ocelot.isBaby()) {
            ocelot.setAge(-getCooldown() * 20);
        }
    }

    @Override
    public int getCooldown() {
        return ocelotAgeCooldown;
    }

    @Override
    public void setCooldown(int newCooldown) {
        ocelotAgeCooldown = newCooldown;
    }

    @Override
    public String getName() {
        return Ocelot.class.getSimpleName();
    }

    public static int handleCommands(CommandContext<CommandSourceStack> context, String command, ModContainer modContainer) {
        ocelotTweak ocelotTweakInstance = new ocelotTweak(modContainer);
        return ageableMobHandleCommands.handleCommands(context, command, ocelotTweakInstance);
    }
}
