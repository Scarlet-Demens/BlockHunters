package org.demens.blockhunters.forge;

import dev.architectury.platform.forge.EventBuses;
import eu.midnightdust.lib.config.MidnightConfig;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(BlockHunters.MODID)
public class BlockHuntersForge {
    public BlockHuntersForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(BlockHunters.MODID, FMLJavaModLoadingContext.get().getModEventBus());
        ModLoadingContext.get().registerExtensionPoint(
                ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory((minecraft, screen) -> MidnightConfig.getScreen(screen, BlockHunters.MODID)));
        BlockHunters.init();
    }
}
