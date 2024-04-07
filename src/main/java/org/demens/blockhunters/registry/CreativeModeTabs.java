package org.demens.blockhunters.registry;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.demens.blockhunters.BlockHunters;

public class CreativeModeTabs {
    public static final CreativeModeTab BLOCK_HUNTERS_TAB = FabricItemGroupBuilder.create(BlockHunters.id("block_hunters_tab"))
            .icon(() -> new ItemStack(Items.DISGUISE_WAND))
            .appendItems((stacks) -> {
                stacks.add(new ItemStack(Items.DISGUISE_WAND));
            })
            .build();

    public static void init() {
        // Initialize class
    }
}
