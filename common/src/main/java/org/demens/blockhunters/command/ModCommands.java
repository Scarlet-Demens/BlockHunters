package org.demens.blockhunters.command;

import dev.architectury.event.events.common.CommandRegistrationEvent;
import net.minecraft.commands.Commands;

public class ModCommands {
    public static void init() {
        CommandRegistrationEvent.EVENT.register(((dispatcher, registryAccess, environment) -> {
            dispatcher.register(
                    Commands.literal("disguise")
                            .then(CommandDisguiseSet.get(dispatcher, registryAccess))
                            .then(CommandDisguiseRemove.get(dispatcher, registryAccess))
            );
        }));
    }
}
