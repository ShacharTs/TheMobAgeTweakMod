package net.MobAgeTweak.Mobs.hostilemobs;

import com.mojang.brigadier.context.CommandContext;
import net.MobAgeTweak.config.ConfigManager;
import net.MobAgeTweak.config.ConfigManager.MobSettings;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.monster.Husk;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import org.jetbrains.annotations.NotNull;

public class huskTweak implements hostilleMobInterface {
    private static boolean onlyBaby;
    private static boolean disableBaby;

    public huskTweak(ModContainer modContainer) {
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
        if (event.getEntity() instanceof Husk husk) {
            if (disableBaby && husk.isBaby()) {
                husk.setBaby(false);
            }
            if (onlyBaby) {
                husk.setBaby(true);
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
        return Husk.class.getSimpleName();
    }

    public static int handleCommands(CommandContext<CommandSourceStack> context, String command, ModContainer modContainer) {
        huskTweak huskTweakInstance = new huskTweak(modContainer);
        return hostileMobHandleCommands.handleCommands(context, command, huskTweakInstance);
    }
}
