package com.dinoslice.doordye.client;

import com.dinoslice.doordye.item.Dye;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;

public class DoOrDyeClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Dye.registerBlockColor(ColorProviderRegistry.BLOCK::register);
        Dye.registerItemColor(ColorProviderRegistry.ITEM::register);
    }
}
