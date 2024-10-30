package net.MobAgeTweak.Mobs.ageableMobs;

import com.mojang.brigadier.context.CommandContext;
import net.MobAgeTweak.config.ConfigManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.animal.Fox;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import org.jetbrains.annotations.NotNull;

public class foxTweak implements ageableMobInterface{
    private static int foxAgeCooldown = DEFAULT_COOLDOWN;

    public foxTweak(ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(this);
        loadConfig();
    }
    @SubscribeEvent
    public void onEntityJoinLevelEvent(@NotNull EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Fox fox && fox.isBaby()) {
            fox.setAge(-getCooldown() * 20);
        }
    }

    @Override
    public int getCooldown() {
        return foxAgeCooldown;
    }

    @Override
    public void setCooldown(int newCooldown) {
        foxAgeCooldown = newCooldown;
        saveConfig();
    }
    @Override
    public void loadConfig() {
        foxAgeCooldown = ConfigManager.loadCooldown(getName());

    }

    @Override
    public void saveConfig() {
        ConfigManager.saveCooldown(getName(), foxAgeCooldown);
    }

    @Override
    public String getName() {
        return Fox.class.getSimpleName();
    }

    public static int handleCommands(CommandContext<CommandSourceStack> context, String command, ModContainer modContainer) {
        foxTweak foxTweakTweakInstance = new foxTweak(modContainer);
        return ageableMobHandleCommands.handleCommands(context, command, foxTweakTweakInstance);
    }
}
