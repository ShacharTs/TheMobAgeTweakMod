package net.MobAgeTweak.Mobs.Mobs.ageableMobs;

import com.mojang.brigadier.context.CommandContext;
import net.MobAgeTweak.Mobs.config.ConfigManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.animal.horse.Donkey;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModContainer;
import org.jetbrains.annotations.NotNull;

public class donkeyTweak implements ageableMobInterface {
    private static int donkeyAgeCooldown = DEFAULT_COOLDOWN;

    public donkeyTweak(ModContainer modContainer) {
        MinecraftForge.EVENT_BUS.register(this);
        loadConfig();
    }

    @SubscribeEvent
    public void onEntityJoinLevelEvent(@NotNull EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Donkey donkey  && donkey.isBaby()) {
            donkey.setAge(-getCooldown() * 20);
        }
    }

    @Override
    public int getCooldown() {
        return donkeyAgeCooldown;
    }

    @Override
    public void setCooldown(int newCooldown) {
        donkeyAgeCooldown = newCooldown;
        saveConfig();
    }
    @Override
    public void loadConfig() {
        donkeyAgeCooldown = ConfigManager.loadCooldown(getName());

    }

    @Override
    public void saveConfig() {
        ConfigManager.saveCooldown(getName(), donkeyAgeCooldown);
    }

    @Override
    public String getName() {
        return Donkey.class.getSimpleName();
    }

    public static int handleCommands(CommandContext<CommandSourceStack> context, String command, ModContainer modContainer) {
        donkeyTweak donkeyTweakInstance = new donkeyTweak(modContainer);
        return ageableMobHandleCommands.handleCommands(context, command, donkeyTweakInstance);
    }
}
