package net.MobAgeTweak.Mobs.Mobs.ageableMobs;

import com.mojang.brigadier.context.CommandContext;
import net.MobAgeTweak.Mobs.config.ConfigManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.animal.camel.Camel;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModContainer;
import org.jetbrains.annotations.NotNull;


public class camelTweak implements ageableMobInterface {
    public static int camelAgeCooldown = DEFAULT_COOLDOWN;

    public camelTweak(ModContainer modContainer) {
        MinecraftForge.EVENT_BUS.register(this);
        loadConfig();
    }

    @SubscribeEvent
    public void onEntityJoinLevelEvent(@NotNull EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Camel camel && camel.isBaby()) {
            camel.setAge(-getCooldown() * 20);
        }
    }

    @Override
    public int getCooldown() {
        return camelAgeCooldown;
    }

    @Override
    public void setCooldown(int newCooldown) {
        camelAgeCooldown = newCooldown;
        saveConfig();
    }

    @Override
    public String getName() {
        return Camel.class.getSimpleName();
    }

    @Override
    public void saveConfig() {
        ConfigManager.saveCooldown(getName(), camelAgeCooldown);
    }
    @Override
    public void loadConfig() {
        camelAgeCooldown = ConfigManager.loadCooldown(getName());
    }


    public static int handleCommands(CommandContext<CommandSourceStack> context, String command, ModContainer modContainer) {
        camelTweak camelTweakInstance = new camelTweak(modContainer);
        return ageableMobHandleCommands.handleCommands(context, command, camelTweakInstance);
    }
}
