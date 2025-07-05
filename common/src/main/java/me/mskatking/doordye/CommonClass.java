package me.mskatking.doordye;

import me.mskatking.doordye.item.Dye;

public class CommonClass {

    public static void init() {
        Constants.LOG.info("Starting Do or Dye v{}!", Constants.VERSION);
    }

    public static void register() {
        Dye.registerDyes();
    }
}
