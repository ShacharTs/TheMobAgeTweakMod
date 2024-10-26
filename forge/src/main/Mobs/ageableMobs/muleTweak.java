package net.MobAgeTweak.Mobs.ageableMobs;

import com.mojang.brigadier.context.CommandContext;
import net.MobAgeTweak.config.ConfigManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.animal.horse.Mule;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModContainer;
import org.jetbrains.annotations.NotNull;

public class muleTweak implements ageableMobInterface {
    private static int muleAgeCooldown = DEFAULT_COOLDOWN;

    public muleTweak(ModContainer modContainer) {
        MinecraftForge.EVENT_BUS.register(this);
        loadConfig();
    }

    @SubscribeEvent
    public void onEntityJoinLevelEvent(@NotNull EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Mule mule && mule.isBaby()) {
            mule.setAge(-getCooldown() * 20);
        }
    }

    @Override
    public int getCooldown() {
        return muleAgeCooldown;
    }

    @Override
    public void setCooldown(int newCooldown) {
        muleAgeCooldown = newCooldown;
        saveConfig();
    }
    @Override
    public void loadConfig() {
        muleAgeCooldown = ConfigManager.loadCooldown(getName());

    }

    @Override
    public void saveConfig() {
        ConfigManager.saveCooldown(getName(), muleAgeCooldown);
    }

    @Override
    public String getName() {
        return Mule.class.getSimpleName();
    }

    public static int handleCommands(CommandContext<CommandSourceStack> context, String command, ModContainer modContainer) {
        muleTweak muleTweakInstance = new muleTweak(modContainer);
        return ageableMobHandleCommands.handleCommands(context, command, muleTweakInstance);
    }
}
