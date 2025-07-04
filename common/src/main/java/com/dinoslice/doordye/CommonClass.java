package com.dinoslice.doordye;

import com.dinoslice.doordye.item.Dye;

public class CommonClass {

    public static void init() {
        Constants.LOG.info(Dye.FUSCHIA.getColor().toString());
    }
}
