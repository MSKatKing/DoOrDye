package me.mskatking.doordye;

import me.mskatking.doordye.item.Dye;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@Mod(Constants.MOD_ID)
public class DoOrDye {

    public DoOrDye(IEventBus eventBus) {
        CommonClass.init();

        eventBus.addListener(DoOrDye::onRegisterBlockColors);
        eventBus.addListener(DoOrDye::onRegisterItemColors);
        eventBus.addListener(DoOrDye::onBuildContents);
    }

    public static void onRegisterBlockColors(RegisterColorHandlersEvent.Block event) {
        Dye.registerBlockColor(event::register);
    }

    public static void onRegisterItemColors(RegisterColorHandlersEvent.Item event) {
        Dye.registerItemColor(event::register);
    }

    public static void onBuildContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.COLORED_BLOCKS) {
            event.acceptAll(Dye.getColoredBlocksItems().stream().map(Item::getDefaultInstance).toList());
        }
    }
}
