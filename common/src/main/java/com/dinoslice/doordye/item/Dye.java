package com.dinoslice.doordye.item;

import net.minecraft.util.ColorRGBA;

public class Dye {
    public static final Dye FUSCHIA = new Dye(0xFF00FF);

    private final ColorRGBA color;

    public Dye(int hexColor) {
        color = new ColorRGBA(hexColor << 8 | 0xFF);
    }

    public ColorRGBA getColor() {
        return color;
    }
}
