package org.demens.blockhunters.client;

import net.fabricmc.api.ClientModInitializer;
import org.demens.blockhunters.config.MainConfig;

public class BlockHuntersClient implements ClientModInitializer {
    public static final MainConfig CONFIG = MainConfig.createAndLoad();

    @Override
    public void onInitializeClient() {

    }
}
