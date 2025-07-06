package me.mskatking.doordye.color;

import me.mskatking.doordye.Constants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;

public final class DoOrDyeColors {
    public static final DoOrDyeColor DRAGONS_BLOOD = register(0x780606, "dragons_blood", DyeColor.BROWN);
    public static final DoOrDyeColor CHERRY = register(0xD20A2E, "cherry", DyeColor.RED);
    public static final DoOrDyeColor BLAZING_RED = register(0xFF0054, "blazing_red", CHERRY);
    public static final DoOrDyeColor CORAL = register(0xFF7F50, "coral", BLAZING_RED);
    public static final DoOrDyeColor LEMON = register(0xFFF700, "lemon", DyeColor.YELLOW);
    public static final DoOrDyeColor VANILLA = register(0xF3E5AB, "vanilla", LEMON);
    public static final DoOrDyeColor MINT = register(0xADEBB3, "mint", VANILLA);
    public static final DoOrDyeColor SEAFOAM = register(0x8DDCDC, "seafoam", DyeColor.CYAN);
    public static final DoOrDyeColor CELESTE = register(0xB2FFFF, "celeste", SEAFOAM);
    public static final DoOrDyeColor INDIGO = register(0x560591, "indigo", DyeColor.BLUE);
    public static final DoOrDyeColor ROYAL_PURPLE = register(0x6C3BAA, "royal_purple", DyeColor.PURPLE);
    public static final DoOrDyeColor FUCHSIA = register(0x9D0759, "fuchsia", ROYAL_PURPLE);
    public static final DoOrDyeColor BLUSH = register(0xFF7782, "blush", DyeColor.MAGENTA);
    public static final DoOrDyeColor LAVENDER = register(0xD3D3FF, "lavender", DyeColor.PINK);

    private static DoOrDyeColor register(int hexColor, String id, DyeColor inventoryPrevious) {
        return new DoOrDyeColor(hexColor, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, id), inventoryPrevious);
    }

    private static DoOrDyeColor register(int hexColor, String id, DoOrDyeColor inventoryPrevious) {
        return new DoOrDyeColor(hexColor, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, id), inventoryPrevious);
    }

    public static void registerDyes() { }
}
