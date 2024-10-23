package net.MobAgeTweak.Mobs.Mobs.ageableMobs;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModContainer;
import org.jetbrains.annotations.NotNull;


public class chickenTweak implements ageableMobInterface {
    public static int chickenAgeCooldown = DEFAULT_COOLDOWN;

    public chickenTweak(ModContainer modContainer) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onEntityJoinLevelEvent(@NotNull EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Chicken chicken && chicken.isBaby()) {
            chicken.setAge(-getCooldown() * 20);
        }
    }

    @Override
    public int getCooldown() {
        return chickenAgeCooldown;
    }

    @Override
    public void setCooldown(int newCooldown) {
        chickenAgeCooldown = newCooldown;
    }

    @Override
    public String getName() {
        return Chicken.class.getSimpleName();
    }



    public static int handleCommands(CommandContext<CommandSourceStack> context, String command, ModContainer modContainer) {
        chickenTweak chickenTweakInstance = new chickenTweak(modContainer);
        return ageableMobHandleCommands.handleCommands(context, command, chickenTweakInstance);
    }
}
