package me.mskatking.doordye;

import me.mskatking.doordye.inventory.CreativeTab;
import me.mskatking.doordye.item.Dye;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(Constants.MOD_ID)
public class DoOrDye {

    public DoOrDye(IEventBus eventBus) {
        CommonClass.init();

        eventBus.addListener(DoOrDye::onRegisterBlockColors);
        eventBus.addListener(DoOrDye::onRegisterItemColors);
        eventBus.addListener(DoOrDye::onBuildContents);
        eventBus.addListener(DoOrDye::onRegister);
    }

    public static void onRegisterBlockColors(RegisterColorHandlersEvent.Block event) {
        Dye.registerBlockColor(event::register);
    }

    public static void onRegisterItemColors(RegisterColorHandlersEvent.Item event) {
        Dye.registerItemColor(event::register);
    }

    public static void onBuildContents(BuildCreativeModeTabContentsEvent event) {
        NeoForgeCreativeTabHelper helper = new NeoForgeCreativeTabHelper(event);
        if (event.getTabKey() == CreativeModeTabs.COLORED_BLOCKS) {
            for (Dye dye : Dye.dyes()) {
                dye.addItemsToInventory(CreativeTab.ColoredBlocks, helper);
            }
        } else if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            for (Dye dye : Dye.dyes()) {
                dye.addItemsToInventory(CreativeTab.Ingredients, helper);
            }
        }
    }

    public static void onRegister(RegisterEvent event) {
        CommonClass.register();
    }
}
