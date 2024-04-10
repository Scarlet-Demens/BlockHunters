package org.demens.blockhunters.config.forge;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;
import org.demens.blockhunters.config.BlockHuntersConfig;

import java.util.List;

public class BlockHuntersConfigImpl {
    public static Pair<BlockHuntersConfigImpl, ForgeConfigSpec> CONFIG = new ForgeConfigSpec.Builder()
            .configure(BlockHuntersConfigImpl::new);;

    public final ForgeConfigSpec.ConfigValue<Enum<BlockHuntersConfig.ListMode>> LIST_MODE;
    public final ForgeConfigSpec.ConfigValue<List<String>> BLOCK_LIST;

    public BlockHuntersConfigImpl(ForgeConfigSpec.Builder builder) {
        LIST_MODE = builder.comment("BLock list mode").define("list_mode", BlockHuntersConfig.ListMode.BLACKLIST);
        BLOCK_LIST = builder.comment("Block list").define("block_list", List.of("minecraft:bedrock"));
    }

    public static List<String> getBlockList() {
        return CONFIG.getLeft().BLOCK_LIST.get();
    }

    public static BlockHuntersConfig.ListMode getBlockListMode() {
        return (BlockHuntersConfig.ListMode) CONFIG.getLeft().LIST_MODE.get();
    }
}
