package net.MobAgeTweak.Mobs.Mobs.hostilemobs;


import com.mojang.brigadier.context.CommandContext;
import net.MobAgeTweak.Mobs.config.ConfigManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModContainer;
import org.jetbrains.annotations.NotNull;

public class zombifiedPiglinTweak implements hostilleMobInterface {
    private static boolean onlyBaby;
    private static boolean disableBaby;

    public zombifiedPiglinTweak(ModContainer modContainer) {
        MinecraftForge.EVENT_BUS.register(this);
        loadSettings();
    }

    private void loadSettings() {
        ConfigManager.MobSettings settings = ConfigManager.getMobSettings(getName());
        onlyBaby = settings.isOnlyBaby();
        disableBaby = settings.isDisableBaby();
    }

    @SubscribeEvent
    public void onEntityJoinLevelEvent(@NotNull EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof ZombifiedPiglin zombifiedPiglin) {
            if (disableBaby && zombifiedPiglin.isBaby()) {
                zombifiedPiglin.setBaby(false);
            }
            if (onlyBaby) {
                zombifiedPiglin.setBaby(true);
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
        return ZombifiedPiglin.class.getSimpleName();
    }

    public static int handleCommands(CommandContext<CommandSourceStack> context, String command, ModContainer modContainer) {
        zombifiedPiglinTweak zombifiedPiglinTweakInstance = new zombifiedPiglinTweak(modContainer);
        return hostileMobHandleCommands.handleCommands(context, command, zombifiedPiglinTweakInstance);
    }
}
