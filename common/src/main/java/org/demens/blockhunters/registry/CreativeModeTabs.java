package org.demens.blockhunters.registry;

import dev.architectury.registry.CreativeTabRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;

public class CreativeModeTabs {
    public static final CreativeModeTab BLOCK_HUNTERS_TAB = CreativeTabRegistry.create(Component.translatable("itemGroup.blockhunters.block_hunters_tab"), () -> new ItemStack(Items.DISGUISE_WAND.get()));

    public static void init() {
        // Initialize class
    }
}
