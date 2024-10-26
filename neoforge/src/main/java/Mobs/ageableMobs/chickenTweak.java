package net.MobAgeTweak.Mobs.ageableMobs;

import com.mojang.brigadier.context.CommandContext;
import net.MobAgeTweak.config.ConfigManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.animal.Chicken;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import org.jetbrains.annotations.NotNull;


public class chickenTweak implements ageableMobInterface {
    public static int chickenAgeCooldown = DEFAULT_COOLDOWN;

    public chickenTweak(ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(this);
        loadConfig();
    }

    @SubscribeEvent
    public void onEntityJoinLevelEvent(@NotNull EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Chicken chicken && chicken.isBaby()) {
            chicken.setAge(-getCooldown() * 20);
        }
    }

    @Override
    public int getCooldown() {
        return chickenAgeCooldown;
    }

    @Override
    public void setCooldown(int newCooldown) {
        chickenAgeCooldown = newCooldown;
        saveConfig();
    }
    @Override
    public void loadConfig() {
        chickenAgeCooldown = ConfigManager.loadCooldown(getName());

    }

    @Override
    public void saveConfig() {
        ConfigManager.saveCooldown(getName(), chickenAgeCooldown);
    }

    @Override
    public String getName() {
        return Chicken.class.getSimpleName();
    }



    public static int handleCommands(CommandContext<CommandSourceStack> context, String command, ModContainer modContainer) {
        chickenTweak chickenTweakInstance = new chickenTweak(modContainer);
        return ageableMobHandleCommands.handleCommands(context, command, chickenTweakInstance);
    }
}
