package net.MobAgeTweak.Mobs.ageableMobs;

import com.mojang.brigadier.context.CommandContext;
import net.MobAgeTweak.config.ConfigManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import org.jetbrains.annotations.NotNull;


public class axolotlTweak implements ageableMobInterface {
    public static int axolotlAgeCooldown = DEFAULT_COOLDOWN;
    public axolotlTweak(ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(this);
        loadConfig();
    }

    @SubscribeEvent
    public void onEntityJoinLevelEvent(@NotNull EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Axolotl axolotl && axolotl.isBaby()) {
            axolotl.setAge(-getCooldown() * 20);
        }
    }

    @Override
    public int getCooldown() {
        return axolotlAgeCooldown;
    }

    @Override
    public void setCooldown(int newCooldown) {
        axolotlAgeCooldown = newCooldown;
        saveConfig();
    }
    @Override
    public void saveConfig() {
        ConfigManager.saveCooldown(getName(), axolotlAgeCooldown);
    }
    @Override
    public void loadConfig() {
        axolotlAgeCooldown = ConfigManager.loadCooldown(getName());
    }

    @Override
    public String getName() {
        return Axolotl.class.getSimpleName();
    }



    public static int handleCommands(CommandContext<CommandSourceStack> context, String command, ModContainer modContainer) {
        axolotlTweak axolotlTweakInstance = new axolotlTweak(modContainer);
        return ageableMobHandleCommands.handleCommands(context, command, axolotlTweakInstance);
    }
}
