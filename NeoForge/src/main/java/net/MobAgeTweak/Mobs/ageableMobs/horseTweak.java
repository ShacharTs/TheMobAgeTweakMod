package net.MobAgeTweak.Mobs.ageableMobs;

import com.mojang.brigadier.context.CommandContext;
import net.MobAgeTweak.config.ConfigManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.animal.horse.Horse;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import org.jetbrains.annotations.NotNull;

public class horseTweak implements ageableMobInterface {
    private static int horseAgeCooldown = DEFAULT_COOLDOWN;

    public horseTweak(ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(this);
        loadConfig();
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
        saveConfig();
    }
    @Override
    public void loadConfig() {
        horseAgeCooldown = ConfigManager.loadCooldown(getName());

    }

    @Override
    public void saveConfig() {
        ConfigManager.saveCooldown(getName(), horseAgeCooldown);
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
