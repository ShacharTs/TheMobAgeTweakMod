package net.MobAgeTweak.Mobs.ageableMobs;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.animal.horse.Mule;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import org.jetbrains.annotations.NotNull;

public class muleTweak implements ageableMobInterface {
    private static int muleAgeCooldown = DEFAULT_COOLDOWN;

    public muleTweak(ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(this);
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
