package net.MobAgeTweak.Mobs.ageableMobs;

import com.mojang.brigadier.context.CommandContext;
import net.MobAgeTweak.config.ConfigManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.animal.Cow;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModContainer;
import org.jetbrains.annotations.NotNull;

public class cowTweak implements ageableMobInterface {
    public static int cowAgeCooldown = DEFAULT_COOLDOWN;

    public cowTweak(ModContainer modContainer) {
        MinecraftForge.EVENT_BUS.register(this);
        loadConfig();
    }

    @SubscribeEvent
    public void onEntityJoinLevelEvent(@NotNull EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Cow cow && cow.isBaby()) {
            cow.setAge(-getCooldown() * 20);
        }
    }

    @Override
    public int getCooldown() {
        return cowAgeCooldown;
    }

    @Override
    public void setCooldown(int newCooldown) {
        cowAgeCooldown = newCooldown;
        saveConfig();
    }
    @Override
    public void loadConfig() {
        cowAgeCooldown = ConfigManager.loadCooldown(getName());

    }

    @Override
    public void saveConfig() {
        ConfigManager.saveCooldown(getName(), cowAgeCooldown);
    }

    @Override
    public String getName() {
        return Cow.class.getSimpleName();
    }



    public static int handleCommands(CommandContext<CommandSourceStack> context, String command, ModContainer modContainer) {
        cowTweak cowTweakInstance = new cowTweak(modContainer);
        return ageableMobHandleCommands.handleCommands(context, command, cowTweakInstance);
    }
}
