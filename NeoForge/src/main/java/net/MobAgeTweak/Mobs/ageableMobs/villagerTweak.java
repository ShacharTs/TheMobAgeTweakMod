package net.MobAgeTweak.Mobs.ageableMobs;

import com.mojang.brigadier.context.CommandContext;
import net.MobAgeTweak.config.ConfigManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.npc.Villager;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import org.jetbrains.annotations.NotNull;

public class villagerTweak implements ageableMobInterface {
    public static int villagerAgeCooldown = DEFAULT_COOLDOWN; // Initialize with default cooldown

    public villagerTweak(ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(this);
        loadConfig();
    }
    @Override
    public void loadConfig() {
        villagerAgeCooldown = ConfigManager.loadCooldown(getName());
    }
    @Override
    public void saveConfig() {
        ConfigManager.saveCooldown(getName(), villagerAgeCooldown);
    }

    @SubscribeEvent
    public void onEntityJoinLevelEvent(@NotNull EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Villager villager && villager.isBaby()) {
            villager.setAge(-getCooldown() * 20);
        }
    }

    @Override
    public int getCooldown() {
        return villagerAgeCooldown;
    }

    @Override
    public void setCooldown(int newCooldown) {
        villagerAgeCooldown = newCooldown;
        saveConfig();
    }


    @Override
    public String getName() {
        return Villager.class.getSimpleName();
    }

    public static int handleCommands(CommandContext<CommandSourceStack> context, String command, ModContainer modContainer) {
        villagerTweak villagerTweakInstance = new villagerTweak(modContainer);
        return ageableMobHandleCommands.handleCommands(context, command, villagerTweakInstance);
    }
}
