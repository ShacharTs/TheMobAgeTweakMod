package net.MobAgeTweak.Mobs.ageableMobs;

import com.mojang.brigadier.context.CommandContext;
import net.MobAgeTweak.config.ConfigManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import org.jetbrains.annotations.NotNull;

public class skeletonHorseTweak implements ageableMobInterface {
    private static int skeletonHorseAgeCooldown = DEFAULT_COOLDOWN;

    public skeletonHorseTweak(ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(this);
        loadConfig();
    }

    @SubscribeEvent
    public void onEntityJoinLevelEvent(@NotNull EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof SkeletonHorse skeletonHorse && skeletonHorse.isBaby()) {
            skeletonHorse.setAge(-getCooldown() * 20);
        }
    }

    @Override
    public int getCooldown() {
        return skeletonHorseAgeCooldown;
    }

    @Override
    public void setCooldown(int newCooldown) {
        skeletonHorseAgeCooldown = newCooldown;
        saveConfig();
    }
    @Override
    public void loadConfig() {
        skeletonHorseAgeCooldown = ConfigManager.loadCooldown(getName());

    }

    @Override
    public void saveConfig() {
        ConfigManager.saveCooldown(getName(), skeletonHorseAgeCooldown);
    }

    @Override
    public String getName() {
        return SkeletonHorse.class.getSimpleName();
    }

    public static int handleCommands(CommandContext<CommandSourceStack> context, String command, ModContainer modContainer) {
        skeletonHorseTweak skeletonHorseTweakInstance = new skeletonHorseTweak(modContainer);
        return ageableMobHandleCommands.handleCommands(context, command, skeletonHorseTweakInstance);
    }
}
