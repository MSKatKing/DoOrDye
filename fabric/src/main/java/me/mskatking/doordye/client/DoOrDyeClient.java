package me.mskatking.doordye.client;

import me.mskatking.doordye.item.CreativeTab;
import me.mskatking.doordye.inventory.CreativeTab;
import me.mskatking.doordye.item.Dye;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.item.CreativeModeTabs;

public class DoOrDyeClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Dye.registerBlockColor(ColorProviderRegistry.BLOCK::register);
        Dye.registerItemColor(ColorProviderRegistry.ITEM::register);

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COLORED_BLOCKS).register(entries -> {
            FabricCreativeTabHelper helper = new FabricCreativeTabHelper(entries);
            for (Dye dye : Dye.dyes()) {
                dye.addItemsToInventory(CreativeTab.ColoredBlocks, helper);
            }
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS).register(entries -> {
            FabricCreativeTabHelper helper = new FabricCreativeTabHelper(entries);
            for (Dye dye : Dye.dyes()) {
                dye.addItemsToInventory(CreativeTab.Ingredients, helper);
            }
        });
    }
}
