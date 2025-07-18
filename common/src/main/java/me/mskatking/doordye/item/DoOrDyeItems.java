package me.mskatking.doordye.item;

import me.mskatking.doordye.block.DoOrDyeBlocks;
import me.mskatking.doordye.inventory.CreativeTab;
import me.mskatking.doordye.inventory.ICreativeTabHelper;
import me.mskatking.doordye.registry.CommonItems;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.Items;

public class DoOrDyeItems {
    // TODO: all need textures
    public static final Item CHERRIES = CommonItems.register("cherries", Item::new, new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(1).build()));
    public static final Item BLUEBERRIES = CommonItems.register("blueberries", prop -> new ItemNameBlockItem(DoOrDyeBlocks.BLUEBERRY_BUSH, prop), new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(1).build()));
    public static final Item VANILLA_BEANS = CommonItems.register("vanilla_beans", Item::new, new Item.Properties());

    public static void registerItems() { }

    public static void addItemsToCreativeTabs(CreativeTab tab, ICreativeTabHelper inserter) {
        if (tab == CreativeTab.FoodAndDrinks) {
            inserter.putAfter(Items.GLOW_BERRIES, CHERRIES);
            inserter.putBefore(Items.SWEET_BERRIES, BLUEBERRIES);
        } else if (tab == CreativeTab.NaturalBlocks) {
            inserter.putAfter(Items.TWISTING_VINES, DoOrDyeBlocks.VANILLA_VINES.asItem());
            inserter.putAfter(Items.PEONY, DoOrDyeBlocks.LAVENDER_FLOWER.asItem());
            inserter.putAfter(Items.LILY_OF_THE_VALLEY, DoOrDyeBlocks.CALLA_LILY.asItem());
        } else if (tab == CreativeTab.Ingredients) {
            inserter.putAfter(Items.HONEYCOMB, VANILLA_BEANS);
        }
    }
}
