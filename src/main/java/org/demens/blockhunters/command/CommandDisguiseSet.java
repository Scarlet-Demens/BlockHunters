package org.demens.blockhunters.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.blocks.BlockStateArgument;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;

public class CommandDisguiseSet {
    public static ArgumentBuilder<CommandSourceStack, ?> get(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext buildContext) {
        return Commands
                .literal("set")
                .requires(cs -> cs.hasPermission(2))
                .then(Commands.argument("players", EntityArgument.players())
                        .then(Commands.argument("state", BlockStateArgument.block(buildContext))
                                .executes(ctx -> {
                                    BlockState state = BlockStateArgument.getBlock(ctx, "state").getState();
                                    for (Player player : EntityArgument.getPlayers(ctx, "players")) {
                                        player.blockHunters$setDisguise(state);
                                    }
                                    return 0;
                                })));
    }
}
