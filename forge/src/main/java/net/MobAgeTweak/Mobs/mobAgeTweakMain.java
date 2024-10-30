package net.MobAgeTweak.Mobs;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.MobAgeTweak.Mobs.Mobs.ageableMobs.*;
import net.MobAgeTweak.Mobs.Mobs.hostilemobs.*;
import net.MobAgeTweak.Mobs.config.ConfigManager;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;



@Mod(mobAgeTweakMain.MOD_ID)
public class mobAgeTweakMain {
    public static final String MOD_ID = "mobagetweak";
    private  ModContainer modContainer;

    public mobAgeTweakMain(ModContainer modContainer) {
        MinecraftForge.EVENT_BUS.register(this);
        this.modContainer = modContainer;
    }
    // DONT DELETE OR CHANGE THIS FUNCION !!!
    public mobAgeTweakMain() {
        MinecraftForge.EVENT_BUS.register(this);
    }

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
            case "armadillo" -> armadilloTweak.handleCommands(context, command, modContainer);
            case "axolotl" -> axolotlTweak.handleCommands(context, command, modContainer);
            case "bee" -> beeTweak.handleCommands(context, command, modContainer);
            case "camel" -> camelTweak.handleCommands(context, command, modContainer);
            case "cat" -> catTweak.handleCommands(context, command, modContainer);
            case "chicken" -> chickenTweak.handleCommands(context, command, modContainer);
            case "cow" ->   cowTweak.handleCommands(context, command, modContainer);
            case "donkey" -> donkeyTweak.handleCommands(context,command,modContainer);
            case "drowned" -> drownedTweak.handleCommands(context, command, modContainer);
            case "fox" -> foxTweak.handleCommands(context, command, modContainer);
            case "goat" -> goatTweak.handleCommands(context, command, modContainer);
            case "horse" -> horseTweak.handleCommands(context,command,modContainer);
            case "llama" -> horseTweak.handleCommands(context,command,modContainer);
            case "husk" -> huskTweak.handleCommands(context, command, modContainer);
            case "mule" -> muleTweak.handleCommands(context, command, modContainer);
            case "mushroomcow" -> mushroomCowTweak.handleCommands(context, command, modContainer);
            case "ocelot" -> ocelotTweak.handleCommands(context, command, modContainer);
            case "panda" -> pandaTweak.handleCommands(context, command, modContainer);
            case "pig" -> pigTweak.handleCommands(context, command, modContainer);
            case "piglin" -> piglinTweak.handleCommands(context, command, modContainer);
            case "polarBear" -> polarBearTweak.handleCommands(context, command, modContainer);
            case "rabbit" -> rabbitTweak.handleCommands(context, command, modContainer);
            case "sheep" -> sheepTweak.handleCommands(context, command, modContainer);
            case "skeletonhorse" -> skeletonHorseTweak.handleCommands(context, command, modContainer);
            case "sniffer" -> snifferTweak.handleCommands(context, command, modContainer);
            case "strider" -> striderTweak.handleCommands(context, command, modContainer);
            case "turtle" -> turtleTweak.handleCommands(context, command, modContainer);
            case "villager" -> villagerTweak.handleCommands(context, command, modContainer);
            case "wolf" -> wolfTweak.handleCommands(context, command, modContainer);
            case "zombie" -> zombieTweak.handleCommands(context, command, modContainer);
            case "zombievillager" -> zombieVillagerTweak.handleCommands(context, command, modContainer);
            case "hoglin" -> hoglinTweak.handleCommands(context, command, modContainer);
            case "zombifiedpiglin" -> zombifiedPiglinTweak.handleCommands(context, command, modContainer);
            case "zoglin" -> zoglinTweak.handleCommands(context, command, modContainer);
            default -> Command.SINGLE_SUCCESS;
        };
    }

}
