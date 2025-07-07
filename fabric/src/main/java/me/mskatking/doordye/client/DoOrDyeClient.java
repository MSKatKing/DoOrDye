package me.mskatking.doordye.client;

import me.mskatking.doordye.DoOrDyeCommon;
import me.mskatking.doordye.color.DoOrDyeColor;
import me.mskatking.doordye.inventory.CreativeTab;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.item.CreativeModeTabs;

public class DoOrDyeClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        DoOrDyeColor.registerAllBlockColors(ColorProviderRegistry.BLOCK::register);
        DoOrDyeColor.registerAllItemColors(ColorProviderRegistry.ITEM::register);

        // TODO: add remaining inventory tabs for convenience
        // TODO: also, can these move to the shared entrypoint?
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COLORED_BLOCKS).register(entries -> DoOrDyeCommon.addItemsToCreativeTab(CreativeTab.ColoredBlocks, new FabricCreativeTabHelper(entries)));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries -> DoOrDyeCommon.addItemsToCreativeTab(CreativeTab.NaturalBlocks, new FabricCreativeTabHelper(entries)));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS).register(entries -> DoOrDyeCommon.addItemsToCreativeTab(CreativeTab.Ingredients, new FabricCreativeTabHelper(entries)));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS).register(entries -> DoOrDyeCommon.addItemsToCreativeTab(CreativeTab.FoodAndDrinks, new FabricCreativeTabHelper(entries)));
    }
}
