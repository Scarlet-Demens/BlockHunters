package org.demens.blockhunters.config.fabric;

import org.demens.blockhunters.client.BlockHuntersFabricClient;
import org.demens.blockhunters.config.BlockHuntersConfig;

import java.util.List;

public class BlockHuntersConfigImpl {
    public static List<String> getBlockList() {
        return BlockHuntersFabricClient.CONFIG.blockList();
    }

    public static BlockHuntersConfig.ListMode getBlockListMode() {
        return BlockHuntersFabricClient.CONFIG.blockListMode();
    }
}
