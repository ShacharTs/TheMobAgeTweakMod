package net.MobAgeTweak.Mobs.ageableMobs;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.animal.Bee;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import org.jetbrains.annotations.NotNull;


public class beeTweak implements ageableMobInterface {
    public static int beeAgeCooldown = DEFAULT_COOLDOWN;

    public beeTweak(ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onEntityJoinLevelEvent(@NotNull EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Bee bee && bee.isBaby()) {
            bee.setAge(-getCooldown() * 20);
        }
    }

    @Override
    public int getCooldown() {
        return beeAgeCooldown;
    }

    @Override
    public void setCooldown(int newCooldown) {
        beeAgeCooldown = newCooldown;
    }

    @Override
    public String getName() {
        return Bee.class.getSimpleName();
    }



    public static int handleCommands(CommandContext<CommandSourceStack> context, String command, ModContainer modContainer) {
        beeTweak beeTweakInstance = new beeTweak(modContainer);
        return ageableMobHandleCommands.handleCommands(context, command, beeTweakInstance);
    }
}
