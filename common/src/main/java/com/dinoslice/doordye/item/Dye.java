package com.dinoslice.doordye.item;

import com.dinoslice.doordye.registry.CommonBlocks;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.awt.*;

public class Dye {
    public static final Dye FUCHSIA = new Dye(0x9D0759, "fuchsia");
    public static final Dye CELESTE = new Dye(0xB2FFFF, "celeste");

    private final Color color;

    private final Block woolBlock;
    private final Block carpetBlock;
    private final Block terracottaBlock;
    private final Block concreteBlock;

    public Dye(int hexColor, String id) {
        this.color = new Color(hexColor);

        this.woolBlock = CommonBlocks.register(id + "_wool", Block::new, BlockBehaviour.Properties.of(), true);
        this.carpetBlock = CommonBlocks.register(id + "_carpet", CarpetBlock::new, BlockBehaviour.Properties.of(), true);
        this.terracottaBlock = CommonBlocks.register(id + "_terracotta", Block::new, BlockBehaviour.Properties.of(), true);
        this.concreteBlock = CommonBlocks.register(id + "_concrete", Block::new, BlockBehaviour.Properties.of(), true);
    }

    public void registerColors(BlockColorRegistry registry) {
        registry.register((blockState, blockAndTintGetter, blockPos, i) -> this.color.getRGB(), this.woolBlock, this.concreteBlock, this.terracottaBlock, this.concreteBlock);
    }

    public void registerColors(ItemColorRegistry registry) {
        registry.register((stack, i) -> this.color.getRGB(), this.woolBlock.asItem(), this.carpetBlock.asItem(), this.terracottaBlock.asItem(), this.concreteBlock.asItem());
    }

    public static void registerBlockColor(BlockColorRegistry registry) {
        FUCHSIA.registerColors(registry);
        CELESTE.registerColors(registry);
    }

    public static void registerItemColor(ItemColorRegistry registry) {
        FUCHSIA.registerColors(registry);
        CELESTE.registerColors(registry);
    }

    public static void registerDyes() { }

    public interface BlockColorRegistry {
        void register(BlockColor color, Block... blocks);
    }

    public interface ItemColorRegistry {
        void register(ItemColor color, Item... items);
    }
}
