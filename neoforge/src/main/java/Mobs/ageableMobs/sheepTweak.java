    package net.MobAgeTweak.Mobs.ageableMobs;

    import com.mojang.brigadier.context.CommandContext;
    import net.MobAgeTweak.config.ConfigManager;
    import net.minecraft.commands.CommandSourceStack;
    import net.minecraft.world.entity.animal.Sheep;
    import net.neoforged.bus.api.SubscribeEvent;
    import net.neoforged.fml.ModContainer;
    import net.neoforged.neoforge.common.NeoForge;
    import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
    import org.jetbrains.annotations.NotNull;


    public class sheepTweak implements ageableMobInterface {
        public static int sheepAgeCooldown = DEFAULT_COOLDOWN;

        public sheepTweak(ModContainer modContainer) {
            NeoForge.EVENT_BUS.register(this);
            loadConfig();
        }

        @SubscribeEvent
        public void onEntityJoinLevelEvent(@NotNull EntityJoinLevelEvent event) {
            if (event.getEntity() instanceof Sheep sheep && sheep.isBaby()) {
                sheep.setAge(-getCooldown() * 20);
            }
        }

        @Override
        public int getCooldown() {
            return sheepAgeCooldown;
        }

        @Override
        public void setCooldown(int newCooldown) {
            sheepAgeCooldown = newCooldown;
            saveConfig();
        }
        @Override
        public void loadConfig() {
            sheepAgeCooldown = ConfigManager.loadCooldown(getName());

        }

        @Override
        public void saveConfig() {
            ConfigManager.saveCooldown(getName(), sheepAgeCooldown);
        }

        @Override
        public String getName() {
            return Sheep.class.getSimpleName();
        }



        public static int handleCommands(CommandContext<CommandSourceStack> context, String command, ModContainer modContainer) {
            sheepTweak sheepTweakInstance = new sheepTweak(modContainer);
            return ageableMobHandleCommands.handleCommands(context, command, sheepTweakInstance);
        }
    }
