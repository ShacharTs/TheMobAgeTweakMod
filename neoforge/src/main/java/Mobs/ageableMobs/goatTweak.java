package net.MobAgeTweak.Mobs.ageableMobs;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.animal.goat.Goat;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import org.jetbrains.annotations.NotNull;

public class goatTweak implements ageableMobInterface {
    private static int goatAgeCooldown = DEFAULT_COOLDOWN;

    public goatTweak(ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onEntityJoinLevelEvent(@NotNull EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Goat goat && goat.isBaby()) {
            goat.setAge(-getCooldown() * 20);
        }
    }

    @Override
    public int getCooldown() {
        return goatAgeCooldown;
    }

    @Override
    public void setCooldown(int newCooldown) {
        goatAgeCooldown = newCooldown;
    }

    @Override
    public String getName() {
        return Goat.class.getSimpleName();
    }

    public static int handleCommands(CommandContext<CommandSourceStack> context, String command, ModContainer modContainer) {
        goatTweak goatTweakInstance = new goatTweak(modContainer);
        return ageableMobHandleCommands.handleCommands(context, command, goatTweakInstance);
    }
}
