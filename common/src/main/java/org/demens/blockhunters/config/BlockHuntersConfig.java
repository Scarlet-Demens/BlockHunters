package org.demens.blockhunters.config;

import com.google.common.collect.Lists;
import eu.midnightdust.lib.config.MidnightConfig;
import org.demens.blockhunters.BlockHunters;

import java.util.List;

public class BlockHuntersConfig extends MidnightConfig {
    @Entry public static ListMode blockListMode = ListMode.BLACKLIST;
    @Entry public static List<String> blockList = Lists.newArrayList("minecraft:bedrock", "minecraft:grass", "minecraft:tall_grass", "minecraft:dirt_path");

    public enum ListMode {
        BLACKLIST,
        WHITELIST
    }

    public static void init() {
        MidnightConfig.init(BlockHunters.MODID, BlockHuntersConfig.class);
    }
}
