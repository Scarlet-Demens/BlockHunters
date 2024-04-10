package org.demens.blockhunters.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.demens.blockhunters.BlockHunters;
import org.demens.blockhunters.config.forge.BlockHuntersConfigImpl;

@Mod(BlockHunters.MODID)
public class BlockHuntersForge {
    public BlockHuntersForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(BlockHunters.MODID, FMLJavaModLoadingContext.get().getModEventBus());
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, BlockHuntersConfigImpl.CONFIG.getValue());
        BlockHunters.init();
    }
}
