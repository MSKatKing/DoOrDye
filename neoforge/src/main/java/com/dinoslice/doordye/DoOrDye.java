package com.dinoslice.doordye;


import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class DoOrDye {

    public DoOrDye(IEventBus eventBus) {
        CommonClass.init();
    }
}
