package net.MobAgeTweak.Mobs.ageableMobs;

import com.mojang.brigadier.context.CommandContext;
import net.MobAgeTweak.config.ConfigManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.animal.goat.Goat;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModContainer;
import org.jetbrains.annotations.NotNull;

public class goatTweak implements ageableMobInterface {
    private static int goatAgeCooldown = DEFAULT_COOLDOWN;

    public goatTweak(ModContainer modContainer) {
        MinecraftForge.EVENT_BUS.register(this);
        loadConfig();
    }

    @SubscribeEvent
    public void onEntityJoinLevelEvent(@NotNull EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Goat goat && goat.isBaby()) {
            goat.setAge(-getCooldown() * 20);
        }
    }

    @Override
    public int getCooldown() {
        return goatAgeCooldown;
    }

    @Override
    public void setCooldown(int newCooldown) {
        goatAgeCooldown = newCooldown;
        saveConfig();
    }
    @Override
    public void loadConfig() {
        goatAgeCooldown = ConfigManager.loadCooldown(getName());

    }

    @Override
    public void saveConfig() {
        ConfigManager.saveCooldown(getName(), goatAgeCooldown);
    }

    @Override
    public String getName() {
        return Goat.class.getSimpleName();
    }

    public static int handleCommands(CommandContext<CommandSourceStack> context, String command, ModContainer modContainer) {
        goatTweak goatTweakInstance = new goatTweak(modContainer);
        return ageableMobHandleCommands.handleCommands(context, command, goatTweakInstance);
    }
}
