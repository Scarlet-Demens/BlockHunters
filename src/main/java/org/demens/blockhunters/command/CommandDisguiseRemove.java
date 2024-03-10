package org.demens.blockhunters.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.player.Player;

public class CommandDisguiseRemove {
    public static ArgumentBuilder<CommandSourceStack, ?> get(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext buildContext) {
        return Commands
                .literal("remove")
                .requires(cs -> cs.hasPermission(2))
                .then(Commands.argument("players", EntityArgument.players())
                        .executes(ctx -> {
                            for (Player player : EntityArgument.getPlayers(ctx, "players")) {
                                player.blockHunters$removeDisguise();
                            }
                            return 0;
                        }));
    }
}
