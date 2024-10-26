package net.MobAgeTweak.Mobs.ageableMobs;

import com.mojang.brigadier.context.CommandContext;
import net.MobAgeTweak.config.ConfigManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.monster.Strider;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModContainer;
import org.jetbrains.annotations.NotNull;

public class striderTweak implements ageableMobInterface {
    private static int striderAgeCooldown = DEFAULT_COOLDOWN;

    public striderTweak(ModContainer modContainer) {
        MinecraftForge.EVENT_BUS.register(this);
        loadConfig();
    }

    @SubscribeEvent
    public void onEntityJoinLevelEvent(@NotNull EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Strider strider && strider.isBaby()) {
            strider.setAge(-getCooldown() * 20);
        }
    }

    @Override
    public int getCooldown() {
        return striderAgeCooldown;
    }

    @Override
    public void setCooldown(int newCooldown) {
        striderAgeCooldown = newCooldown;
        saveConfig();
    }
    @Override
    public void loadConfig() {
        striderAgeCooldown = ConfigManager.loadCooldown(getName());

    }

    @Override
    public void saveConfig() {
        ConfigManager.saveCooldown(getName(), striderAgeCooldown);
    }

    @Override
    public String getName() {
        return Strider.class.getSimpleName();
    }

    public static int handleCommands(CommandContext<CommandSourceStack> context, String command, ModContainer modContainer) {
        striderTweak striderTweakInstance = new striderTweak(modContainer);
        return ageableMobHandleCommands.handleCommands(context, command, striderTweakInstance);
    }
}
