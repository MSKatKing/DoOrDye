package me.mskatking.doordye;

import me.mskatking.doordye.inventory.CreativeTab;
import me.mskatking.doordye.item.DoOrDyeItems;
import me.mskatking.doordye.item.Dye;
import me.mskatking.doordye.inventory.ICreativeTabHelper;

public class CommonClass {

    public static void init() {
        Constants.LOG.info("Starting Do or Dye v{}!", Constants.VERSION);
    }

    public static void register() {
        Dye.registerDyes();
        DoOrDyeItems.registerItems();
    }
}
