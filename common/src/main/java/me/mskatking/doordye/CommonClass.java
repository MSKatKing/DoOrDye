package me.mskatking.doordye;

import me.mskatking.doordye.block.DoOrDyeBlocks;
import me.mskatking.doordye.color.DoOrDyeColor;
import me.mskatking.doordye.color.DoOrDyeColors;
import me.mskatking.doordye.inventory.CreativeTab;
import me.mskatking.doordye.item.DoOrDyeItems;
import me.mskatking.doordye.inventory.ICreativeTabHelper;

public class CommonClass {

    public static void init() {
        Constants.LOG.info("Starting Do or Dye v{}!", Constants.VERSION);
    }

    public static void register() {
        DoOrDyeColors.registerDyes();

        DoOrDyeBlocks.registerBlocks();
        DoOrDyeItems.registerItems();
    }

    public static void addItemsToCreativeTab(CreativeTab tab, ICreativeTabHelper inserter) {
        DoOrDyeColor.colors().forEach(color -> color.addItemsToInventory(tab, inserter));

        DoOrDyeItems.addItemsToCreativeTabs(tab, inserter);
    }
}
