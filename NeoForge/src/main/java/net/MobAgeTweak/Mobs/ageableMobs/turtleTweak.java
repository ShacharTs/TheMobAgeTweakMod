package net.MobAgeTweak.Mobs.ageableMobs;

import com.mojang.brigadier.context.CommandContext;
import net.MobAgeTweak.config.ConfigManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.animal.Turtle;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import org.jetbrains.annotations.NotNull;

public class turtleTweak implements ageableMobInterface {
    private static int turtleAgeCooldown = DEFAULT_COOLDOWN;

    public turtleTweak(ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(this);
        loadConfig();
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
        saveConfig();
    }
    @Override
    public void loadConfig() {
        turtleAgeCooldown = ConfigManager.loadCooldown(getName());

    }

    @Override
    public void saveConfig() {
        ConfigManager.saveCooldown(getName(), turtleAgeCooldown);
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
