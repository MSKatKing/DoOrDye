package me.mskatking.doordye;

import net.fabricmc.api.ModInitializer;

public class DoOrDye implements ModInitializer {

    @Override
    public void onInitialize() {
        CommonClass.init();
        CommonClass.register();
    }
}
