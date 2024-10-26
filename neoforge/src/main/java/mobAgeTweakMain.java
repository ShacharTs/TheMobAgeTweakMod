package net.MobAgeTweak;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
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

import java.util.List;

@Mod(mobAgeTweakMain.MOD_ID)
public class mobAgeTweakMain {
    public static final String MOD_ID = "mobagetweak";
    private final ModContainer modContainer;
    public static ConfigManager.MobSettings mobSettings;

    public mobAgeTweakMain(ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(this);
        this.modContainer = modContainer;
        ConfigManager.loadConfig();
        mobSettings = new ConfigManager.MobSettings();
    }


    @SubscribeEvent
    public void onRegisterCommandsEvent(@NotNull RegisterCommandsEvent event) {
        ConfigManager.loadConfig();
        event.getDispatcher().register(
                Commands.literal("mobtweaks").then(
                        Commands.argument("mob", StringArgumentType.string()).suggests((context, builder) -> builder
                                .suggest("villager").suggest("cow").suggest("chicken")
                                .suggest("pig").suggest("zombie").suggest("armadillo")
                                .suggest("bee").suggest("cat").suggest("camel")
                                .suggest("drowned").suggest("zombievillager").suggest("husk").suggest("piglin")
                                .suggest("zombifiedpiglin").suggest("zoglin").suggest("hoglin")
                                .suggest("sheep").suggest("rabbit").suggest("axolotl")
                                .suggest("panda").suggest("fox").suggest("donkey")
                                .suggest("goat").suggest("horse").suggest("llama")
                                .suggest("mule").suggest("mushroomcow").suggest("ocelot")
                                .suggest("polarbear").suggest("skeletonhorse").suggest("sniffer")
                                .suggest("strider").suggest("turtle").suggest("wolf")
                                .buildFuture()).executes(context -> {
                            String mobName = StringArgumentType.getString(context, "mob").toLowerCase();
                            return handleMobCommand(context, mobName, null);
                        }).then(
                                Commands.argument("command", StringArgumentType.string()).suggests((context, builder) -> {
                                    String mobName = StringArgumentType.getString(context, "mob").toLowerCase();
                                    boolean isHostile = List.of("zombie", "zombievillager", "husk", "piglin", "zombifiedpiglin", "zoglin", "hoglin", "drowned").contains(mobName);
                                    if (isHostile) {
                                        return builder
                                                .suggest("disablebaby")
                                                .suggest("enablebaby")
                                                .suggest("enableonlybabies")
                                                .suggest("disableonlybabies")
                                                .buildFuture();
                                    } else {
                                        return builder
                                                .suggest("changecooldown")
                                                .suggest("checkcooldown")
                                                .suggest("resetcooldown")
                                                .buildFuture();
                                    }
                                }).executes(context -> {
                                    String mobName = StringArgumentType.getString(context, "mob").toLowerCase();
                                    String command = StringArgumentType.getString(context, "command").toLowerCase();
                                    return handleMobCommand(context, mobName, command);
                                }).then(
                                        Commands.argument("seconds", IntegerArgumentType.integer(1, 1200)).executes(context -> {
                                            String mobName = StringArgumentType.getString(context, "mob").toLowerCase();
                                            String command = StringArgumentType.getString(context, "command").toLowerCase();
                                            if ("changecooldown".equals(command)) {
                                                return handleMobCommand(context, mobName, command);
                                            } else {
                                                context.getSource().sendSystemMessage(Component.literal("The seconds argument can only be used with the changecooldown command.").withStyle(ChatFormatting.RED));
                                                return Command.SINGLE_SUCCESS;
                                            }
                                        })
                                )
                        )
                )
        );
    }

    private int handleMobCommand(CommandContext<CommandSourceStack> context, String mobName, String command) {
        CommandSourceStack source = context.getSource();

        if (!source.hasPermission(2)) {
            source.sendSystemMessage(Component.literal("You do not have permission to execute this command.").withStyle(ChatFormatting.RED));
            return Command.SINGLE_SUCCESS;
        }

        if (command == null) {
            source.sendSystemMessage(Component.literal("Available commands for " + mobName + ":").withStyle(ChatFormatting.YELLOW));
            source.sendSystemMessage(Component.literal(" - changecooldown").withStyle(ChatFormatting.GOLD));
            source.sendSystemMessage(Component.literal(" - checkcooldown").withStyle(ChatFormatting.GOLD));
            source.sendSystemMessage(Component.literal(" - resetcooldown").withStyle(ChatFormatting.GOLD));
            return Command.SINGLE_SUCCESS;
        }


        return switch (mobName) {
            case "villager" -> net.MobAgeTweak.Mobs.ageableMobs.villagerTweak.handleCommands(context, command, modContainer);
            case "cow" -> net.MobAgeTweak.Mobs.ageableMobs.cowTweak.handleCommands(context, command, modContainer);
            case "chicken" -> net.MobAgeTweak.Mobs.ageableMobs.chickenTweak.handleCommands(context, command, modContainer);
            case "pig" -> net.MobAgeTweak.Mobs.ageableMobs.pigTweak.handleCommands(context, command, modContainer);
            case "armadillo" -> net.MobAgeTweak.Mobs.ageableMobs.armadilloTweak.handleCommands(context, command, modContainer);
            case "axolotl" -> net.MobAgeTweak.Mobs.ageableMobs.axolotlTweak.handleCommands(context, command, modContainer);
            case "bee" -> net.MobAgeTweak.Mobs.ageableMobs.beeTweak.handleCommands(context, command, modContainer);
            case "cat" -> net.MobAgeTweak.Mobs.ageableMobs.catTweak.handleCommands(context, command, modContainer);
            case "camel" -> net.MobAgeTweak.Mobs.ageableMobs.camelTweak.handleCommands(context, command, modContainer);
            case "sheep" -> net.MobAgeTweak.Mobs.ageableMobs.sheepTweak.handleCommands(context, command, modContainer);
            case "rabbit" -> net.MobAgeTweak.Mobs.ageableMobs.rabbitTweak.handleCommands(context, command, modContainer);
            case "panda" -> net.MobAgeTweak.Mobs.ageableMobs.pandaTweak.handleCommands(context, command, modContainer);
            case "fox" -> net.MobAgeTweak.Mobs.ageableMobs.foxTweak.handleCommands(context, command, modContainer);
            case "mule" -> net.MobAgeTweak.Mobs.ageableMobs.muleTweak.handleCommands(context, command, modContainer);
            case "mushroomcow" -> net.MobAgeTweak.Mobs.ageableMobs.mushroomCowTweak.handleCommands(context, command, modContainer);
            case "ocelot" -> net.MobAgeTweak.Mobs.ageableMobs.ocelotTweak.handleCommands(context, command, modContainer);
            case "polarbear" -> net.MobAgeTweak.Mobs.ageableMobs.polarBearTweak.handleCommands(context, command, modContainer);
            case "skeletonhorse" -> net.MobAgeTweak.Mobs.ageableMobs.skeletonHorseTweak.handleCommands(context, command, modContainer);
            case "sniffer" -> net.MobAgeTweak.Mobs.ageableMobs.snifferTweak.handleCommands(context, command, modContainer);
            case "strider" -> net.MobAgeTweak.Mobs.ageableMobs.striderTweak.handleCommands(context, command, modContainer);
            case "turtle" -> net.MobAgeTweak.Mobs.ageableMobs.turtleTweak.handleCommands(context, command, modContainer);
            case "wolf" -> net.MobAgeTweak.Mobs.ageableMobs.wolfTweak.handleCommands(context, command, modContainer);
            case "zombie" -> net.MobAgeTweak.Mobs.hostilemobs.zombieTweak.handleCommands(context, command, modContainer);
            case "zombievillager" -> net.MobAgeTweak.Mobs.hostilemobs.zombieVillagerTweak.handleCommands(context, command, modContainer);
            case "husk" -> net.MobAgeTweak.Mobs.hostilemobs.huskTweak.handleCommands(context, command, modContainer);
            case "zombifiedpiglin" -> net.MobAgeTweak.Mobs.hostilemobs.zombifiedPiglinTweak.handleCommands(context, command, modContainer);
            case "zoglin" -> net.MobAgeTweak.Mobs.hostilemobs.zoglinTweak.handleCommands(context, command, modContainer);
            case "piglin" -> net.MobAgeTweak.Mobs.hostilemobs.piglinTweak.handleCommands(context, command, modContainer);
            case "hoglin" -> net.MobAgeTweak.Mobs.hostilemobs.hoglinTweak.handleCommands(context, command, modContainer);
            case "drowned" -> drownedTweak.handleCommands(context, command, modContainer);
            default -> {
                context.getSource().sendSystemMessage(Component.literal("Unknown mob: " + mobName).withStyle(ChatFormatting.RED));
                yield Command.SINGLE_SUCCESS;
            }
        };
    }
}