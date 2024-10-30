package net.MobAgeTweak;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.MobAgeTweak.Mobs.ageableMobs.donkeyTweak;
import net.MobAgeTweak.Mobs.ageableMobs.horseTweak;
import net.MobAgeTweak.Mobs.hostilemobs.drownedTweak;
import net.MobAgeTweak.config.ConfigManager;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

@Mod(mobAgeTweakMain.MOD_ID)
public class mobAgeTweakMain {
    public static final String MOD_ID = "mobagetweak";
    private final ModContainer modContainer;

    // List of commands
    public static final String change = "change";
    public static final String check = "check";
    public static final String reset = "reset";
    public static final String disableBaby = "disableBaby";
    public static final String enableBaby = "enableBaby";
    public static final String disableOnlyBaby = "disableonlybabies";
    public static final String enableOnlyBaby = "onlybabies";
    public static final String modName = "mobtweak";
    public static final String disable = "disable";
    public static final String enable = "enable";
    public static final String spawnBaby = "spawnbaby";
    public static final String onlyBabyMode = "onlybabymode";

    public mobAgeTweakMain(ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(this);
        this.modContainer = modContainer;
        ConfigManager.loadConfig();
    }

    @SubscribeEvent
    public void onRegisterCommandsEvent(@NotNull RegisterCommandsEvent event) {
        ConfigManager.loadConfig();
        List<String> passiveMobs = Arrays.asList(
                "villager", "cow", "chicken", "pig", "bee", "cat", "camel",
                "sheep", "rabbit", "axolotl", "panda", "fox", "donkey",
                "goat", "horse", "llama", "mule", "mushroomcow", "ocelot",
                "polarbear", "sniffer", "strider", "turtle", "wolf", "armadillo", "skeletonhorse");

        List<String> hostileMobs = Arrays.asList(
                "zombie", "zombievillager", "husk", "drowned", "hoglin", "zoglin", "piglin", "zombifiedpiglin");

        // commands for passive mobs
        passiveMobs.forEach(mob -> {
            event.getDispatcher().register(
                    Commands.literal(modName).then(
                            Commands.literal(mob).then(
                                    Commands.literal(change).then(
                                            Commands.argument("seconds", IntegerArgumentType.integer(1, 1200)).
                                                    executes(context -> handleMobCommand(context, mob, change))
                                    )
                            )
                    )
            );
            event.getDispatcher().register(
                    Commands.literal(modName).then(
                            Commands.literal(mob).then(
                                    Commands.literal(check).executes(context -> handleMobCommand(context, mob, check))
                            )
                    )
            );
            event.getDispatcher().register(
                    Commands.literal(modName).then(
                            Commands.literal(mob).then(
                                    Commands.literal(reset).executes(context -> handleMobCommand(context, mob, reset))
                            )
                    )
            );
        });

        // commands for hostile mobs
        hostileMobs.forEach(mob -> {
            event.getDispatcher().register(
                    Commands.literal(modName).then(
                            Commands.literal(mob).then(
                                    Commands.literal(disable).then(
                                            Commands.literal(spawnBaby).executes(context -> handleMobCommand(context,mob,disableBaby))
                            )
                    )
            ));

            event.getDispatcher().register(
                    Commands.literal(modName).then(
                            Commands.literal(mob).then(
                                    Commands.literal(enable).then(
                                            Commands.literal(spawnBaby).executes(context -> handleMobCommand(context,mob,enableBaby))
                                    )
                            )
                    ));

            event.getDispatcher().register(
                    Commands.literal(modName).then(
                            Commands.literal(mob).then(
                                    Commands.literal(enable).then(
                                            Commands.literal(onlyBabyMode).executes(context -> handleMobCommand(context,mob, enableOnlyBaby)))
                                    )
                            )
                    );

            event.getDispatcher().register(
                    Commands.literal(modName).then(
                            Commands.literal(mob).then(
                                    Commands.literal(disable).then(
                                            Commands.literal(onlyBabyMode).executes(context -> handleMobCommand(context,mob, disableOnlyBaby)))
                            )
                    )
            );
        });
    }


    private int handleMobCommand(CommandContext<CommandSourceStack> context, String mobName, String command) {
        CommandSourceStack source = context.getSource();
            // If this enables that's mean it requires Op to use this mod
            // level 2 = Op
        if (!source.hasPermission(2)) {
            source.sendSystemMessage(Component.literal("You do not have permission to execute this command.").withStyle(ChatFormatting.RED));
            return Command.SINGLE_SUCCESS;
        }

        return switch (mobName) {
            case "armadillo" -> net.MobAgeTweak.Mobs.ageableMobs.armadilloTweak.handleCommands(context, command, modContainer);
            case "axolotl" -> net.MobAgeTweak.Mobs.ageableMobs.axolotlTweak.handleCommands(context, command, modContainer);
            case "bee" -> net.MobAgeTweak.Mobs.ageableMobs.beeTweak.handleCommands(context, command, modContainer);
            case "camel" -> net.MobAgeTweak.Mobs.ageableMobs.camelTweak.handleCommands(context, command, modContainer);
            case "cat" -> net.MobAgeTweak.Mobs.ageableMobs.catTweak.handleCommands(context, command, modContainer);
            case "chicken" -> net.MobAgeTweak.Mobs.ageableMobs.chickenTweak.handleCommands(context, command, modContainer);
            case "cow" -> net.MobAgeTweak.Mobs.ageableMobs.cowTweak.handleCommands(context, command, modContainer);
            case "donkey" -> donkeyTweak.handleCommands(context,command,modContainer);
            case "drowned" -> drownedTweak.handleCommands(context, command, modContainer);
            case "fox" -> net.MobAgeTweak.Mobs.ageableMobs.foxTweak.handleCommands(context, command, modContainer);
            case "goat" -> net.MobAgeTweak.Mobs.ageableMobs.goatTweak.handleCommands(context, command, modContainer);
            case "horse" -> horseTweak.handleCommands(context,command,modContainer);
            case "llama" -> horseTweak.handleCommands(context,command,modContainer);
            case "husk" -> net.MobAgeTweak.Mobs.hostilemobs.huskTweak.handleCommands(context, command, modContainer);
            case "mule" -> net.MobAgeTweak.Mobs.ageableMobs.muleTweak.handleCommands(context, command, modContainer);
            case "mushroomcow" -> net.MobAgeTweak.Mobs.ageableMobs.mushroomCowTweak.handleCommands(context, command, modContainer);
            case "ocelot" -> net.MobAgeTweak.Mobs.ageableMobs.ocelotTweak.handleCommands(context, command, modContainer);
            case "panda" -> net.MobAgeTweak.Mobs.ageableMobs.pandaTweak.handleCommands(context, command, modContainer);
            case "pig" -> net.MobAgeTweak.Mobs.ageableMobs.pigTweak.handleCommands(context, command, modContainer);
            case "piglin" -> net.MobAgeTweak.Mobs.hostilemobs.piglinTweak.handleCommands(context, command, modContainer);
            case "polarBear" -> net.MobAgeTweak.Mobs.ageableMobs.polarBearTweak.handleCommands(context, command, modContainer);
            case "rabbit" -> net.MobAgeTweak.Mobs.ageableMobs.rabbitTweak.handleCommands(context, command, modContainer);
            case "sheep" -> net.MobAgeTweak.Mobs.ageableMobs.sheepTweak.handleCommands(context, command, modContainer);
            case "skeletonhorse" -> net.MobAgeTweak.Mobs.ageableMobs.skeletonHorseTweak.handleCommands(context, command, modContainer);
            case "sniffer" -> net.MobAgeTweak.Mobs.ageableMobs.snifferTweak.handleCommands(context, command, modContainer);
            case "strider" -> net.MobAgeTweak.Mobs.ageableMobs.striderTweak.handleCommands(context, command, modContainer);
            case "turtle" -> net.MobAgeTweak.Mobs.ageableMobs.turtleTweak.handleCommands(context, command, modContainer);
            case "villager" -> net.MobAgeTweak.Mobs.ageableMobs.villagerTweak.handleCommands(context, command, modContainer);
            case "wolf" -> net.MobAgeTweak.Mobs.ageableMobs.wolfTweak.handleCommands(context, command, modContainer);
            case "zombie" -> net.MobAgeTweak.Mobs.hostilemobs.zombieTweak.handleCommands(context, command, modContainer);
            case "zombievillager" -> net.MobAgeTweak.Mobs.hostilemobs.zombieVillagerTweak.handleCommands(context, command, modContainer);
            case "hoglin" -> net.MobAgeTweak.Mobs.hostilemobs.hoglinTweak.handleCommands(context, command, modContainer);
            case "zombifiedpiglin" -> net.MobAgeTweak.Mobs.hostilemobs.zombifiedPiglinTweak.handleCommands(context, command, modContainer);
            case "zoglin" -> net.MobAgeTweak.Mobs.hostilemobs.zoglinTweak.handleCommands(context, command, modContainer);
            default -> Command.SINGLE_SUCCESS;
        };
    }
}
