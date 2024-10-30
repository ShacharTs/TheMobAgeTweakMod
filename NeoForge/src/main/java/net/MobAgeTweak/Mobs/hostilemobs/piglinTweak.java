package net.MobAgeTweak.Mobs.hostilemobs;

import com.mojang.brigadier.context.CommandContext;
import net.MobAgeTweak.config.ConfigManager;
import net.MobAgeTweak.config.ConfigManager.MobSettings;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import org.jetbrains.annotations.NotNull;

public class piglinTweak implements hostilleMobInterface {
    private static boolean onlyBaby;
    private static boolean disableBaby;

    public piglinTweak(ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(this);
        loadSettings();
    }

    private void loadSettings() {
        MobSettings settings = ConfigManager.getMobSettings(getName());
        onlyBaby = settings.isOnlyBaby();
        disableBaby = settings.isDisableBaby();
    }

    @SubscribeEvent
    public void onEntityJoinLevelEvent(@NotNull EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Piglin piglin) {
            if (disableBaby && piglin.isBaby()) {
                piglin.setBaby(false);
            }
            if (onlyBaby) {
                piglin.setBaby(true);
            }
        }
    }

    @Override
    public void setDisableBaby(boolean bool) {
        disableBaby = bool;
        ConfigManager.saveHostileSetting(getName(), "disableBaby", bool);
    }

    @Override
    public boolean getDisableBaby() {
        return disableBaby;
    }

    @Override
    public boolean getOnlyBaby() {
        return onlyBaby;
    }

    @Override
    public void setOnlyBaby(boolean bool) {
        onlyBaby = bool;
        ConfigManager.saveHostileSetting(getName(), "onlyBaby", bool);
    }

    @Override
    public String getName() {
        return Piglin.class.getSimpleName();
    }

    public static int handleCommands(CommandContext<CommandSourceStack> context, String command, ModContainer modContainer) {
        piglinTweak piglinTweakInstance = new piglinTweak(modContainer);
        return hostileMobHandleCommands.handleCommands(context, command, piglinTweakInstance);
    }
}
