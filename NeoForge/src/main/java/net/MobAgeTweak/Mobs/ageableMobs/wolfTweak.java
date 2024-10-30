package net.MobAgeTweak.Mobs.ageableMobs;

import com.mojang.brigadier.context.CommandContext;
import net.MobAgeTweak.config.ConfigManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.animal.Wolf;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import org.jetbrains.annotations.NotNull;

public class wolfTweak implements ageableMobInterface {
    private static int wolfAgeCooldown = DEFAULT_COOLDOWN;

    public wolfTweak(ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(this);
        loadConfig();
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
        saveConfig();
    }
    @Override
    public void loadConfig() {
        wolfAgeCooldown = ConfigManager.loadCooldown(getName());

    }

    @Override
    public void saveConfig() {
        ConfigManager.saveCooldown(getName(), wolfAgeCooldown);
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
