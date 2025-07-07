package me.mskatking.doordye;

import me.mskatking.doordye.color.DoOrDyeColor;
import me.mskatking.doordye.inventory.CreativeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(Constants.MOD_ID)
public class DoOrDye {

    public DoOrDye(IEventBus eventBus) {
        DoOrDyeCommon.init();

        eventBus.addListener(DoOrDye::onRegisterBlockColors);
        eventBus.addListener(DoOrDye::onRegisterItemColors);
        eventBus.addListener(DoOrDye::onBuildContents);
        eventBus.addListener(DoOrDye::onRegister);
    }

    public static void onRegisterBlockColors(RegisterColorHandlersEvent.Block event) {
        DoOrDyeColor.registerAllBlockColors(event::register);
    }

    public static void onRegisterItemColors(RegisterColorHandlersEvent.Item event) {
        DoOrDyeColor.registerAllItemColors(event::register);
    }

    // TODO: add remaining inventory tabs for convenience
    public static void onBuildContents(BuildCreativeModeTabContentsEvent event) {
        NeoForgeCreativeTabHelper helper = new NeoForgeCreativeTabHelper(event);
        if (event.getTabKey() == CreativeModeTabs.COLORED_BLOCKS) {
            DoOrDyeCommon.addItemsToCreativeTab(CreativeTab.ColoredBlocks, helper);
        } else if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            DoOrDyeCommon.addItemsToCreativeTab(CreativeTab.Ingredients, helper);
        } else if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            DoOrDyeCommon.addItemsToCreativeTab(CreativeTab.FoodAndDrinks, helper);
        } else if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            DoOrDyeCommon.addItemsToCreativeTab(CreativeTab.NaturalBlocks, helper);
        }
    }

    public static void onRegister(RegisterEvent event) {
        DoOrDyeCommon.register();
    }
}
