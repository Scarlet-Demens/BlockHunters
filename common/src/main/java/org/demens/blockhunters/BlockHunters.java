package org.demens.blockhunters;

import com.google.common.base.Suppliers;
import dev.architectury.registry.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.demens.blockhunters.command.ModCommands;
import org.demens.blockhunters.config.BlockHuntersConfig;
import org.demens.blockhunters.registry.CreativeModeTabs;
import org.demens.blockhunters.registry.Items;

import java.util.function.Supplier;

public class BlockHunters {
    public static final String MODID = "blockhunters";
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static final Supplier<Registries> REGISTRIES = Suppliers.memoize(() -> Registries.get(MODID));

    public static void init() {
        // Registry
        Items.init();
        CreativeModeTabs.init();
        ModCommands.init();
        BlockHuntersConfig.init();
    }

    public static ResourceLocation id(String id) {
        return new ResourceLocation(MODID, id);
    }
}
