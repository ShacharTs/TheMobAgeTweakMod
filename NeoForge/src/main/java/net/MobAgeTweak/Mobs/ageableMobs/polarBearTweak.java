package net.MobAgeTweak.Mobs.ageableMobs;

import com.mojang.brigadier.context.CommandContext;
import net.MobAgeTweak.config.ConfigManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.animal.PolarBear;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import org.jetbrains.annotations.NotNull;

public class polarBearTweak implements ageableMobInterface {
    private static int polarBearAgeCooldown = DEFAULT_COOLDOWN;

    public polarBearTweak(ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(this);
        loadConfig();
    }

    @SubscribeEvent
    public void onEntityJoinLevelEvent(@NotNull EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof PolarBear polarBear && polarBear.isBaby()) {
            polarBear.setAge(-getCooldown() * 20);
        }
    }

    @Override
    public int getCooldown() {
        return polarBearAgeCooldown;
    }

    @Override
    public void setCooldown(int newCooldown) {
        polarBearAgeCooldown = newCooldown;
        saveConfig();
    }
    @Override
    public void loadConfig() {
        polarBearAgeCooldown = ConfigManager.loadCooldown(getName());

    }

    @Override
    public void saveConfig() {
        ConfigManager.saveCooldown(getName(), polarBearAgeCooldown);
    }

    @Override
    public String getName() {
        return PolarBear.class.getSimpleName();
    }

    public static int handleCommands(CommandContext<CommandSourceStack> context, String command, ModContainer modContainer) {
        polarBearTweak polarBearTweakInstance = new polarBearTweak(modContainer);
        return ageableMobHandleCommands.handleCommands(context, command, polarBearTweakInstance);
    }
}
