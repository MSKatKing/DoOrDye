package me.mskatking.doordye.item;

import me.mskatking.doordye.registry.CommonBlocks;
import me.mskatking.doordye.registry.CommonItems;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Dye {
    public static final Dye FUCHSIA = new Dye(0x9D0759, "fuchsia");
    public static final Dye DRAGONS_BLOOD = new Dye(0x780606, "dragons_blood");
    public static final Dye CHERRY = new Dye(0xD20A2E, "cherry");
    public static final Dye CORAL = new Dye(0xFF7F50, "coral");
    public static final Dye LEMON = new Dye(0xFFF700, "lemon");
    public static final Dye MINT = new Dye(0xADEBB3, "mint");
    public static final Dye SEAFOAM = new Dye(0x8DDCDC, "seafoam");
    public static final Dye LAVENDER = new Dye(0xD3D3FF, "lavender");
    public static final Dye ROYAL_PURPLE = new Dye(0x6C3BAA, "royal_purple");
    public static final Dye INDIGO = new Dye(0x560591, "indigo");
    public static final Dye BLUSH = new Dye(0xFF7782, "blush");
    public static final Dye VANILLA = new Dye(0xF3E5AB, "vanilla");
    public static final Dye CELESTE = new Dye(0xB2FFFF, "celeste");
    public static final Dye BLAZING_RED = new Dye(0xFF0054, "blazing_red");

    public static Dye[] dyes() { return new Dye[]{FUCHSIA, DRAGONS_BLOOD, CHERRY, CORAL, LEMON, MINT, SEAFOAM, LAVENDER, ROYAL_PURPLE, INDIGO, BLUSH, VANILLA, CELESTE, BLAZING_RED}; }

    private final Color color;

    private final Block woolBlock;
    private final Block carpetBlock;
    private final Block terracottaBlock;
    private final Block concreteBlock;

    private final Item dyeItem;

    public Dye(int hexColor, String id) {
        this.color = new Color(hexColor);

        this.woolBlock = CommonBlocks.register(id + "_wool", Block::new, BlockBehaviour.Properties.of(), true);
        this.carpetBlock = CommonBlocks.register(id + "_carpet", CarpetBlock::new, BlockBehaviour.Properties.of(), true);
        this.terracottaBlock = CommonBlocks.register(id + "_terracotta", Block::new, BlockBehaviour.Properties.of(), true);
        this.concreteBlock = CommonBlocks.register(id + "_concrete", Block::new, BlockBehaviour.Properties.of(), true);
        this.dyeItem = CommonItems.register(id + "_dye", Item::new, new Item.Properties());
    }

    public void registerColors(BlockColorRegistry registry) {
        registry.register((blockState, blockAndTintGetter, blockPos, i) -> this.color.getRGB(), this.woolBlock, this.concreteBlock, this.terracottaBlock, this.concreteBlock);
    }

    public void registerColors(ItemColorRegistry registry) {
        registry.register((stack, i) -> this.color.getRGB(), this.woolBlock.asItem(), this.carpetBlock.asItem(), this.terracottaBlock.asItem(), this.concreteBlock.asItem(), this.dyeItem);
    }

    public List<Item> getItems() {
        return List.of(this.woolBlock.asItem(), this.carpetBlock.asItem(), this.concreteBlock.asItem(), this.terracottaBlock.asItem(), this.dyeItem);
    }

    public static void registerBlockColor(BlockColorRegistry registry) {
        for (Dye dye : dyes())
            dye.registerColors(registry);
    }

    public static void registerItemColor(ItemColorRegistry registry) {
        for (Dye dye : dyes())
            dye.registerColors(registry);
    }

    public static List<Item> getColoredBlocksItems() {
        List<Item> list = new ArrayList<>();

        for (Dye dye : dyes())
            list.addAll(dye.getItems());

        return list;
    }

    public static void registerDyes() { }

    public interface BlockColorRegistry {
        void register(BlockColor color, Block... blocks);
    }

    public interface ItemColorRegistry {
        void register(ItemColor color, Item... items);
    }
}
