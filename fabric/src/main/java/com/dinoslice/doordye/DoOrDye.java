package com.dinoslice.doordye;

import net.fabricmc.api.ModInitializer;

public class DoOrDye implements ModInitializer {

    @Override
    public void onInitialize() {
        Constants.LOG.info("Hello Fabric world!");
        CommonClass.init();
    }
}
