package org.demens.blockhunters;

import net.fabricmc.api.ModInitializer;

public class BlockHuntersFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        BlockHunters.init();
    }
}
