package net.MobAgeTweak.Mobs.Mobs.ageableMobs;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.animal.Cat;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModContainer;
import org.jetbrains.annotations.NotNull;

public class catTweak implements ageableMobInterface {
    public static int catAgeCooldown = DEFAULT_COOLDOWN;

    public catTweak(ModContainer modContainer) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onEntityJoinLevelEvent(@NotNull EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Cat cat && cat.isBaby()) {
            cat.setAge(-getCooldown() * 20);
        }
    }

    @Override
    public int getCooldown() {
        return catAgeCooldown;
    }

    @Override
    public void setCooldown(int newCooldown) {
        catAgeCooldown = newCooldown;
    }

    @Override
    public String getName() {
        return Cat.class.getSimpleName();
    }


    public static int handleCommands(CommandContext<CommandSourceStack> context, String command, ModContainer modContainer) {
        catTweak catTweakInstance = new catTweak(modContainer);
        return ageableMobHandleCommands.handleCommands(context, command, catTweakInstance);
    }
}
