package me.mskatking.doordye.item;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class FruitItem extends Item {
    public FruitItem(Properties pProperties, int hunger, int saturation) {
        super(pProperties.food(new FoodProperties.Builder().nutrition(hunger).saturationModifier(saturation).build()));
    }
}
