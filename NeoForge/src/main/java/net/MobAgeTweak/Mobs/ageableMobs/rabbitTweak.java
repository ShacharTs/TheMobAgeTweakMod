package net.MobAgeTweak.Mobs.ageableMobs;

import com.mojang.brigadier.context.CommandContext;
import net.MobAgeTweak.config.ConfigManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.animal.Rabbit;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import org.jetbrains.annotations.NotNull;

public class rabbitTweak implements ageableMobInterface {
    private static int rabbitAgeCooldown = DEFAULT_COOLDOWN;

    public rabbitTweak(ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(this);
        loadConfig();
    }

    @SubscribeEvent
    public void onEntityJoinLevelEvent(@NotNull EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Rabbit rabbit && rabbit.isBaby()) {
            rabbit.setAge(-getCooldown() * 20);
        }
    }

    @Override
    public int getCooldown() {
        return rabbitAgeCooldown;
    }

    @Override
    public void setCooldown(int newCooldown) {
        rabbitAgeCooldown = newCooldown;
        saveConfig();
    }
    @Override
    public void loadConfig() {
        rabbitAgeCooldown = ConfigManager.loadCooldown(getName());

    }

    @Override
    public void saveConfig() {
        ConfigManager.saveCooldown(getName(), rabbitAgeCooldown);
    }

    @Override
    public String getName() {
        return Rabbit.class.getSimpleName();
    }

    public static int handleCommands(CommandContext<CommandSourceStack> context, String command, ModContainer modContainer) {
        rabbitTweak rabbitTweakInstance = new rabbitTweak(modContainer);
        return ageableMobHandleCommands.handleCommands(context, command, rabbitTweakInstance);
    }
}
