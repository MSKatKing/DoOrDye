package me.mskatking.doordye.item;

import me.mskatking.doordye.block.DoOrDyeBlocks;
import me.mskatking.doordye.inventory.CreativeTab;
import me.mskatking.doordye.inventory.ICreativeTabHelper;
import me.mskatking.doordye.registry.CommonItems;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;

public class DoOrDyeItems {
    public static final Item CHERRIES = CommonItems.register("cherries", prop -> new FruitItem(prop, 2, 1), new Item.Properties());
    public static final Item BLUEBERRIES = CommonItems.register("blueberries", prop -> new ItemNameBlockItem(DoOrDyeBlocks.BLUEBERRY_BUSH, prop), new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(1).build()));

    public static void registerItems() { }

    public static void addItemsToCreativeTabs(CreativeTab tab, ICreativeTabHelper inserter) {
        if (tab == CreativeTab.FoodAndDrinks) {
            inserter.append(CHERRIES);
            inserter.append(BLUEBERRIES);
        }
    }
}
