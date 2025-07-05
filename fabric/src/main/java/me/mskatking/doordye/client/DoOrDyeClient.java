package me.mskatking.doordye.client;

import me.mskatking.doordye.item.Dye;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

public class DoOrDyeClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Dye.registerBlockColor(ColorProviderRegistry.BLOCK::register);
        Dye.registerItemColor(ColorProviderRegistry.ITEM::register);

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COLORED_BLOCKS).register((entries) -> {
            entries.acceptAll(Dye.getColoredBlocksItems().stream().map(Item::getDefaultInstance).toList());
        });
    }
}
