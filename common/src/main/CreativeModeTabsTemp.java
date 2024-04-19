package org.demens.blockhunters.registry;

import dev.architectury.registry.CreativeTabRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import @id_package@;

public class CreativeModeTabs {
    public static final CreativeModeTab BLOCK_HUNTERS_TAB = CreativeTabRegistry.create(@id@, () -> new ItemStack(Items.DISGUISE_WAND.get()));

    public static void init() {
        // Initialize class
    }
}
