package org.demens.blockhunters;

import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.demens.blockhunters.command.ModCommands;
import org.demens.blockhunters.registry.Items;

public class BlockHunters implements ModInitializer {
    public static final String MODID = "blockhunters";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    @Override
    public void onInitialize() {
        // Registry
        Items.init();
        ModCommands.init();
    }

    public static ResourceLocation id(String id) {
        return new ResourceLocation(MODID, id);
    }
}
