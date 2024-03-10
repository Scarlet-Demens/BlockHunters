package org.demens.blockhunters.command;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.Commands;

public class ModCommands {
    public static void init() {
        CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> {
            dispatcher.register(
                    Commands.literal("disguise")
                            .then(CommandDisguiseSet.get(dispatcher, registryAccess))
                            .then(CommandDisguiseRemove.get(dispatcher, registryAccess))
            );
        }));
    }
}
