package net.MobAgeTweak.Mobs.Mobs.ageableMobs;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.animal.armadillo.Armadillo;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModContainer;
import org.jetbrains.annotations.NotNull;

public class armadilloTweak implements ageableMobInterface {
    public static int armadilloAgeCooldown = DEFAULT_COOLDOWN;

    public armadilloTweak(ModContainer modContainer) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onEntityJoinLevelEvent(@NotNull EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Armadillo armadillo && armadillo.isBaby()) {
            armadillo.setAge(-getCooldown() * 20);
        }
    }

    @Override
    public int getCooldown() {
        return armadilloAgeCooldown;
    }

    @Override
    public void setCooldown(int newCooldown) {
        armadilloAgeCooldown = newCooldown;
    }

    @Override
    public String getName() {
        return Armadillo.class.getSimpleName();
    }


    public static int handleCommands(CommandContext<CommandSourceStack> context, String command, ModContainer modContainer) {
        armadilloTweak armadilloTweakInstance = new armadilloTweak(modContainer);
        return ageableMobHandleCommands.handleCommands(context, command, armadilloTweakInstance);
    }
}
