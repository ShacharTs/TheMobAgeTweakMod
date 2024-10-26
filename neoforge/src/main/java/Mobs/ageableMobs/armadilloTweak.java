package net.MobAgeTweak.Mobs.ageableMobs;

import com.mojang.brigadier.context.CommandContext;
import net.MobAgeTweak.config.ConfigManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.animal.armadillo.Armadillo;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import org.jetbrains.annotations.NotNull;

public class armadilloTweak implements ageableMobInterface {
    public static int armadilloAgeCooldown = DEFAULT_COOLDOWN;

    public armadilloTweak(ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(this);
        loadConfig();
    }

    @SubscribeEvent
    public void onEntityJoinLevelEvent(@NotNull EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Armadillo armadillo && armadillo.isBaby()) {
            armadillo.setAge(-getCooldown() * 20);
        }
    }

    @Override
    public int getCooldown() {
        return armadilloAgeCooldown;
    }

    @Override
    public void setCooldown(int newCooldown) {
        armadilloAgeCooldown = newCooldown;
        saveConfig();
    }
    @Override
    public void saveConfig() {
        ConfigManager.saveCooldown(getName(), armadilloAgeCooldown); // Save the updated cooldown
    }

    @Override
    public void loadConfig() {
        armadilloAgeCooldown = ConfigManager.loadCooldown(getName());
    }

    @Override
    public String getName() {
        return Armadillo.class.getSimpleName();
    }


    public static int handleCommands(CommandContext<CommandSourceStack> context, String command, ModContainer modContainer) {
        armadilloTweak armadilloTweakInstance = new armadilloTweak(modContainer);
        return ageableMobHandleCommands.handleCommands(context, command, armadilloTweakInstance);
    }
}
