package net.MobAgeTweak.Mobs.ageableMobs;

import com.mojang.brigadier.context.CommandContext;
import net.MobAgeTweak.config.ConfigManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.animal.Panda;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModContainer;
import org.jetbrains.annotations.NotNull;

public class pandaTweak implements ageableMobInterface{
    private static int pandaAgeCooldown = DEFAULT_COOLDOWN;

    public pandaTweak(ModContainer modContainer) {
        MinecraftForge.EVENT_BUS.register(this);
        loadConfig();
    }
    @SubscribeEvent
    public void onEntityJoinLevelEvent(@NotNull EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Panda panda && panda.isBaby()) {
            panda.setAge(-getCooldown() * 20);
        }
    }

    @Override
    public int getCooldown() {
        return pandaAgeCooldown;
    }

    @Override
    public void setCooldown(int newCooldown) {
        pandaAgeCooldown = newCooldown;
        saveConfig();
    }
    @Override
    public void loadConfig() {
        pandaAgeCooldown = ConfigManager.loadCooldown(getName());

    }

    @Override
    public void saveConfig() {
        ConfigManager.saveCooldown(getName(), pandaAgeCooldown);
    }

    @Override
    public String getName() {
        return Panda.class.getSimpleName();
    }

    public static int handleCommands(CommandContext<CommandSourceStack> context, String command, ModContainer modContainer) {
        pandaTweak pandaTweakInstance = new pandaTweak(modContainer);
        return ageableMobHandleCommands.handleCommands(context, command, pandaTweakInstance);
    }
}
