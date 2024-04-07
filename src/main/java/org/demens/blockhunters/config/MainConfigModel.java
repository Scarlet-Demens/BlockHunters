package org.demens.blockhunters.config;

import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;
import org.demens.blockhunters.BlockHunters;

import java.util.ArrayList;
import java.util.List;

@Modmenu(modId = BlockHunters.MODID)
@Config(name = "blockhunters-config", wrapperName = "MainConfig")
public class MainConfigModel {
    public ListMode blockListMode = ListMode.BLACKLIST;
    public List<String> blockList = new ArrayList<>(List.of("minecraft:bedrock", "minecraft:grass", "minecraft:tall_grass", "minecraft:dirt_path"));

    public enum ListMode {
        BLACKLIST,
        WHITELIST
    }
}
