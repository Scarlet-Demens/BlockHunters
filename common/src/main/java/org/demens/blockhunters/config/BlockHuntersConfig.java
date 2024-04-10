package org.demens.blockhunters.config;

import dev.architectury.injectables.annotations.ExpectPlatform;

import java.util.List;

public class BlockHuntersConfig {
    @ExpectPlatform
    public static List<String> getBlockList() {
        return null;
    }

    @ExpectPlatform
    public static ListMode getBlockListMode() {
        return null;
    }

    public enum ListMode {
        BLACKLIST,
        WHITELIST
    }
}
