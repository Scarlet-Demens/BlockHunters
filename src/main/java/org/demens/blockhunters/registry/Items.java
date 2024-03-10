package org.demens.blockhunters.registry;

import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import org.demens.blockhunters.BlockHunters;
import org.demens.blockhunters.item.DisguiseWandItem;

public class Items {
    public static final Item DISGUISE_WAND = register("disguise_wand", new DisguiseWandItem(new Item.Properties().stacksTo(1)));

    private static Item register(String name, Item item) {  
        return Registry.register(Registry.ITEM, BlockHunters.id(name), item);
    }

    public static void init() {
        // initialize
    }
}
