package org.demens.blockhunters;

import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.demens.blockhunters.command.ModCommands;
import org.demens.blockhunters.registry.CreativeModeTabs;
import org.demens.blockhunters.registry.Items;

public class BlockHuntersFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        BlockHunters.init();
    }
}
