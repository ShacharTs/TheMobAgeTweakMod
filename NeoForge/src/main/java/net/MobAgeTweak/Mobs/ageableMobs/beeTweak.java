package net.MobAgeTweak.Mobs.ageableMobs;

import com.mojang.brigadier.context.CommandContext;
import net.MobAgeTweak.config.ConfigManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.animal.Bee;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import org.jetbrains.annotations.NotNull;


public class beeTweak implements ageableMobInterface {
    public static int beeAgeCooldown = DEFAULT_COOLDOWN;

    public beeTweak(ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(this);
        loadConfig();
    }

    @SubscribeEvent
    public void onEntityJoinLevelEvent(@NotNull EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Bee bee && bee.isBaby()) {
            bee.setAge(-getCooldown() * 20);
        }
    }

    @Override
    public int getCooldown() {
        return beeAgeCooldown;
    }

    @Override
    public void setCooldown(int newCooldown) {
        beeAgeCooldown = newCooldown;
        saveConfig();
    }

    @Override
    public String getName() {
        return Bee.class.getSimpleName();
    }

    @Override
    public void loadConfig() {
        beeAgeCooldown = ConfigManager.loadCooldown(getName());

    }

    @Override
    public void saveConfig() {
        ConfigManager.saveCooldown(getName(), beeAgeCooldown);
    }


    public static int handleCommands(CommandContext<CommandSourceStack> context, String command, ModContainer modContainer) {
        beeTweak beeTweakInstance = new beeTweak(modContainer);
        return ageableMobHandleCommands.handleCommands(context, command, beeTweakInstance);
    }
}
