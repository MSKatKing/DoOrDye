package com.dinoslice.doordye;


import com.dinoslice.doordye.item.Dye;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@Mod(Constants.MOD_ID)
public class DoOrDye {

    public DoOrDye(IEventBus eventBus) {
        CommonClass.init();

        eventBus.addListener(DoOrDye::onRegisterBlockColors);
        eventBus.addListener(DoOrDye::onRegisterItemColors);
    }

    public static void onRegisterBlockColors(RegisterColorHandlersEvent.Block event) {
        Dye.registerBlockColor(event::register);
    }

    public static void onRegisterItemColors(RegisterColorHandlersEvent.Item event) {
        Dye.registerItemColor(event::register);
    }
}
