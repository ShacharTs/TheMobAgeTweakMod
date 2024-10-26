package net.MobAgeTweak.Mobs.ageableMobs;

import com.mojang.brigadier.context.CommandContext;
import net.MobAgeTweak.config.ConfigManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.animal.Cat;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import org.jetbrains.annotations.NotNull;

public class catTweak implements ageableMobInterface {
    public static int catAgeCooldown = DEFAULT_COOLDOWN;

    public catTweak(ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(this);
        loadConfig();
    }

    @SubscribeEvent
    public void onEntityJoinLevelEvent(@NotNull EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Cat cat && cat.isBaby()) {
            cat.setAge(-getCooldown() * 20);
        }
    }

    @Override
    public int getCooldown() {
        return catAgeCooldown;
    }

    @Override
    public void setCooldown(int newCooldown) {
        catAgeCooldown = newCooldown;
        saveConfig();
    }
    @Override
    public void loadConfig() {
        catAgeCooldown = ConfigManager.loadCooldown(getName());

    }

    @Override
    public void saveConfig() {
        ConfigManager.saveCooldown(getName(), catAgeCooldown);
    }

    @Override
    public String getName() {
        return Cat.class.getSimpleName();
    }


    public static int handleCommands(CommandContext<CommandSourceStack> context, String command, ModContainer modContainer) {
        catTweak catTweakInstance = new catTweak(modContainer);
        return ageableMobHandleCommands.handleCommands(context, command, catTweakInstance);
    }
}
