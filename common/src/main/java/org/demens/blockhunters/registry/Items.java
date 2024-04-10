package org.demens.blockhunters.registry;

import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import org.demens.blockhunters.BlockHunters;
import org.demens.blockhunters.item.DisguiseWandItem;

import java.util.function.Supplier;

public class Items {
    // Registry
    private static final Registrar<Item> ITEMS = BlockHunters.REGISTRIES.get().get(Registry.ITEM_REGISTRY);
    // Items
    public static final RegistrySupplier<Item> DISGUISE_WAND = register("disguise_wand", () -> new DisguiseWandItem(new Item.Properties().stacksTo(1).tab(CreativeModeTabs.BLOCK_HUNTERS_TAB)));

    private static RegistrySupplier<Item> register(String name, Supplier<Item> item) {
        return ITEMS.register(BlockHunters.id(name), item);
    }

    public static void init() {
        // initialize
    }
}
