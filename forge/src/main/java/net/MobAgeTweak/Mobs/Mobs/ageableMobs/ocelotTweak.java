package net.MobAgeTweak.Mobs.Mobs.ageableMobs;


import com.mojang.brigadier.context.CommandContext;
import net.MobAgeTweak.Mobs.config.ConfigManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModContainer;
import org.jetbrains.annotations.NotNull;

public class ocelotTweak implements ageableMobInterface {
    private static int ocelotAgeCooldown = DEFAULT_COOLDOWN;

    public ocelotTweak(ModContainer modContainer) {
        MinecraftForge.EVENT_BUS.register(this);
        loadConfig();
    }

    @SubscribeEvent
    public void onEntityJoinLevelEvent(@NotNull EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Ocelot ocelot && ocelot.isBaby()) {
            ocelot.setAge(-getCooldown() * 20);
        }
    }

    @Override
    public int getCooldown() {
        return ocelotAgeCooldown;
    }

    @Override
    public void setCooldown(int newCooldown) {
        ocelotAgeCooldown = newCooldown;
        saveConfig();
    }
    @Override
    public void loadConfig() {
        ocelotAgeCooldown = ConfigManager.loadCooldown(getName());

    }

    @Override
    public void saveConfig() {
        ConfigManager.saveCooldown(getName(), ocelotAgeCooldown);
    }

    @Override
    public String getName() {
        return Ocelot.class.getSimpleName();
    }

    public static int handleCommands(CommandContext<CommandSourceStack> context, String command, ModContainer modContainer) {
        ocelotTweak ocelotTweakInstance = new ocelotTweak(modContainer);
        return ageableMobHandleCommands.handleCommands(context, command, ocelotTweakInstance);
    }
}
