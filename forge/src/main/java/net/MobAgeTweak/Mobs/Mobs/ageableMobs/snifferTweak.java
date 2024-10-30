package net.MobAgeTweak.Mobs.Mobs.ageableMobs;


import com.mojang.brigadier.context.CommandContext;
import net.MobAgeTweak.Mobs.config.ConfigManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.animal.sniffer.Sniffer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModContainer;
import org.jetbrains.annotations.NotNull;

public class snifferTweak implements ageableMobInterface {
    private static int snifferAgeCooldown = DEFAULT_COOLDOWN;

    public snifferTweak(ModContainer modContainer) {
        MinecraftForge.EVENT_BUS.register(this);
        loadConfig();
    }

    @SubscribeEvent
    public void onEntityJoinLevelEvent(@NotNull EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Sniffer sniffer && sniffer.isBaby()) {
            sniffer.setAge(-getCooldown() * 20);
        }
    }

    @Override
    public int getCooldown() {
        return snifferAgeCooldown;
    }

    @Override
    public void setCooldown(int newCooldown) {
        snifferAgeCooldown = newCooldown;
        saveConfig();
    }
    @Override
    public void loadConfig() {
        snifferAgeCooldown = ConfigManager.loadCooldown(getName());

    }

    @Override
    public void saveConfig() {
        ConfigManager.saveCooldown(getName(), snifferAgeCooldown);
    }

    @Override
    public String getName() {
        return Sniffer.class.getSimpleName();
    }

    public static int handleCommands(CommandContext<CommandSourceStack> context, String command, ModContainer modContainer) {
        snifferTweak snifferTweakInstance = new snifferTweak(modContainer);
        return ageableMobHandleCommands.handleCommands(context, command, snifferTweakInstance);
    }
}
