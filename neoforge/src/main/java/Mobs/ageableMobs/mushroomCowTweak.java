package net.MobAgeTweak.Mobs.ageableMobs;

import com.mojang.brigadier.context.CommandContext;
import net.MobAgeTweak.config.ConfigManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.animal.MushroomCow;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import org.jetbrains.annotations.NotNull;

public class mushroomCowTweak implements ageableMobInterface {
    private static int mushroomCowAgeCooldown = DEFAULT_COOLDOWN;

    public mushroomCowTweak(ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(this);
        loadConfig();
    }

    @SubscribeEvent
    public void onEntityJoinLevelEvent(@NotNull EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof MushroomCow mushroomCow && mushroomCow.isBaby()) {
            mushroomCow.setAge(-getCooldown() * 20);
        }
    }

    @Override
    public int getCooldown() {
        return mushroomCowAgeCooldown;
    }

    @Override
    public void setCooldown(int newCooldown) {
        mushroomCowAgeCooldown = newCooldown;
        saveConfig();
    }
    @Override
    public void loadConfig() {
        mushroomCowAgeCooldown = ConfigManager.loadCooldown(getName());

    }

    @Override
    public void saveConfig() {
        ConfigManager.saveCooldown(getName(), mushroomCowAgeCooldown);
    }

    @Override
    public String getName() {
        return MushroomCow.class.getSimpleName();
    }

    public static int handleCommands(CommandContext<CommandSourceStack> context, String command, ModContainer modContainer) {
        mushroomCowTweak mushroomCowTweakInstance = new mushroomCowTweak(modContainer);
        return ageableMobHandleCommands.handleCommands(context, command, mushroomCowTweakInstance);
    }
}
