package me.mskatking.doordye.registry;

import me.mskatking.doordye.Constants;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;

public class CommonBlocks {
    public static Block register(String id, Function<BlockBehaviour.Properties, Block> blockFactory, BlockBehaviour.Properties settings, boolean shouldRegistryItem) {
        ResourceLocation key = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, id);
        Block block = blockFactory.apply(settings);

        if (shouldRegistryItem) {
            BlockItem item = new BlockItem(block, new Item.Properties());
            Registry.register(BuiltInRegistries.ITEM, key, item);
        }

        return Registry.register(BuiltInRegistries.BLOCK, key, block);
    }
}
