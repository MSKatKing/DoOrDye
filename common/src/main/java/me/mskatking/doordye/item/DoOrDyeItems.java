package me.mskatking.doordye.item;

import me.mskatking.doordye.Constants;
import me.mskatking.doordye.inventory.CreativeTab;
import me.mskatking.doordye.inventory.ICreativeTabHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class DoOrDyeItems {
    public static final Item CHERRIES = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "cherries"), new FruitItem(new Item.Properties(), 2, 1));

    public static void registerItems() { }

    public static void addItemsToCreativeTabs(CreativeTab tab, ICreativeTabHelper inserter) {
        if (tab == CreativeTab.FoodAndDrinks) {
            inserter.append(CHERRIES);
        }
    }
}
