package net.MobAgeTweak.Mobs.ageableMobs;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.animal.horse.Llama;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import org.jetbrains.annotations.NotNull;

public class llamaTweak implements ageableMobInterface {
    private static int llamaAgeCooldown = DEFAULT_COOLDOWN;

    public llamaTweak(ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onEntityJoinLevelEvent(@NotNull EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Llama llama && llama.isBaby()) {
            llama.setAge(-getCooldown() * 20);
        }
    }

    @Override
    public int getCooldown() {
        return llamaAgeCooldown;
    }

    @Override
    public void setCooldown(int newCooldown) {
        llamaAgeCooldown = newCooldown;
    }

    @Override
    public String getName() {
        return Llama.class.getSimpleName();
    }

    public static int handleCommands(CommandContext<CommandSourceStack> context, String command, ModContainer modContainer) {
        llamaTweak llamaTweakInstance = new llamaTweak(modContainer);
        return ageableMobHandleCommands.handleCommands(context, command, llamaTweakInstance);
    }
}
