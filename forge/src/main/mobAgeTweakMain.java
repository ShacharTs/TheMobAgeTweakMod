package net.MobAgeTweak;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.MobAgeTweak.Mobs.ageableMobs.*;
import net.MobAgeTweak.Mobs.hostilemobs.*;
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

import java.util.List;

@Mod(mobAgeTweakMain.MOD_ID)
public class mobAgeTweakMain {
    public static final String MOD_ID = "mobagetweak";
    private  ModContainer modContainer;

    public mobAgeTweakMain(ModContainer modContainer) {
        MinecraftForge.EVENT_BUS.register(this);
        this.modContainer = modContainer;
    }
    public mobAgeTweakMain() {
        // Register event bus
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onRegisterCommandsEvent(@NotNull RegisterCommandsEvent event) {
        event.getDispatcher().register(
                Commands.literal("mobtweaks").then(
                        Commands.argument("mob", StringArgumentType.string()).suggests((context, builder) -> builder
                                .suggest("villager").suggest("cow").suggest("chicken")
                                .suggest("pig").suggest("zombie").suggest("armadillo")
                                .suggest("bee").suggest("cat").suggest("camel")
                                .suggest("zombievillager").suggest("husk").suggest("piglin")
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
                                    boolean isHostile = List.of("zombie", "zombievillager", "husk", "piglin", "zombifiedpiglin", "zoglin", "hoglin").contains(mobName);
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
            case "villager" -> villagerTweak.handleCommands(context, command, modContainer);
            case "cow" -> cowTweak.handleCommands(context, command, modContainer);
            case "chicken" -> chickenTweak.handleCommands(context, command, modContainer);
            case "pig" -> pigTweak.handleCommands(context, command, modContainer);
            case "armadillo" -> armadilloTweak.handleCommands(context, command, modContainer);
            case "axolotl" -> axolotlTweak.handleCommands(context, command, modContainer);
            case "bee" -> beeTweak.handleCommands(context, command, modContainer);
            case "cat" -> catTweak.handleCommands(context, command, modContainer);
            case "camel" -> camelTweak.handleCommands(context, command, modContainer);
            case "sheep" -> sheepTweak.handleCommands(context, command, modContainer);
            case "rabbit" -> rabbitTweak.handleCommands(context, command, modContainer);
            case "panda" -> pandaTweak.handleCommands(context, command, modContainer);
            case "fox" -> foxTweak.handleCommands(context, command, modContainer);
            case "mule" -> muleTweak.handleCommands(context, command, modContainer);
            case "mushroomcow" -> mushroomCowTweak.handleCommands(context, command, modContainer);
            case "ocelot" -> ocelotTweak.handleCommands(context, command, modContainer);
            case "polarbear" -> polarBearTweak.handleCommands(context, command, modContainer);
            case "skeletonhorse" -> skeletonHorseTweak.handleCommands(context, command, modContainer);
            case "sniffer" -> snifferTweak.handleCommands(context, command, modContainer);
            case "strider" -> striderTweak.handleCommands(context, command, modContainer);
            case "turtle" -> turtleTweak.handleCommands(context, command, modContainer);
            case "wolf" -> wolfTweak.handleCommands(context, command, modContainer);
            case "zombie" -> zombieTweak.handleCommands(context, command, modContainer);
            case "zombievillager" -> zombieVillagerTweak.handleCommands(context, command, modContainer);
            case "husk" -> huskTweak.handleCommands(context, command, modContainer);
            case "zombifiedpiglin" -> zombifiedPiglinTweak.handleCommands(context, command, modContainer);
            case "zoglin" -> zoglinTweak.handleCommands(context, command, modContainer);
            case "piglin" -> piglinTweak.handleCommands(context, command, modContainer);
            case "hoglin" -> hoglinTweak.handleCommands(context, command, modContainer);
            default -> {
                context.getSource().sendSystemMessage(Component.literal("Unknown mob: " + mobName).withStyle(ChatFormatting.RED));
                yield Command.SINGLE_SUCCESS;
            }
        };
    }

}