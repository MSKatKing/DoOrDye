package me.mskatking.doordye.color;

import me.mskatking.doordye.block.colored.ColoredStainedGlass;
import me.mskatking.doordye.inventory.CreativeTab;
import me.mskatking.doordye.inventory.ICreativeTabHelper;
import me.mskatking.doordye.registry.CommonBlocks;
import me.mskatking.doordye.registry.CommonItems;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.ConcretePowderBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiConsumer;

public final class DoOrDyeColor implements ItemLike {
    private static final List<DoOrDyeColor> COLORS = new ArrayList<>();

    private final ResourceLocation id;
    private final int color;

    private final Block wool;
    private final Block carpet;
    private final Block terracotta;
    private final Block concrete;
    private final Block concretePowder;
    private final Block stainedGlass;

    private final Item dye;

    private final String inventoryColorBefore;

    private DoOrDyeColor(int hexColor, ResourceLocation id, ResourceLocation inventoryColorBefore) {
        this.id = id;
        this.color = hexColor;

        String colorId = id.getPath();

        this.wool = CommonBlocks.register(colorId + "_wool", Block::new, BlockBehaviour.Properties.of(), true);
        this.carpet = CommonBlocks.register(colorId + "_carpet", CarpetBlock::new, BlockBehaviour.Properties.of(), true);
        this.terracotta = CommonBlocks.register(colorId + "_terracotta", Block::new, BlockBehaviour.Properties.of(), true);
        this.concrete = CommonBlocks.register(colorId + "_concrete", Block::new, BlockBehaviour.Properties.of(), true);
        this.concretePowder = CommonBlocks.register(colorId + "_concrete_powder", prop -> new ConcretePowderBlock(this.concrete, prop), BlockBehaviour.Properties.of(), true);
        this.stainedGlass = CommonBlocks.register(colorId + "_stained_glass", ColoredStainedGlass::new, BlockBehaviour.Properties.of(), true);

        this.dye = CommonItems.register(colorId + "_dye", Item::new, new Item.Properties());

        this.inventoryColorBefore = inventoryColorBefore.toString();

        COLORS.add(this);
    }

    public DoOrDyeColor(int hexColor, ResourceLocation colorId, DyeColor minecraftColorBefore) {
        this(hexColor, colorId, ResourceLocation.withDefaultNamespace(minecraftColorBefore.getName()));
    }

    public DoOrDyeColor(int hexColor, ResourceLocation colorId, DoOrDyeColor customColorBefore) {
        this(hexColor, colorId, customColorBefore.id);
    }

    public static List<DoOrDyeColor> colors() {
        return COLORS;
    }

    public void registerBlockColors(BiConsumer<BlockColor, Block[]> registry) {
        registry.accept((blockState, blockAndTintGetter, blockPos, i) -> this.color, new Block[]{this.wool, this.carpet, this.terracotta, this.concrete, this.concretePowder, this.stainedGlass});
    }

    public void registerItemColors(BiConsumer<ItemColor, ItemLike[]> registry) {
        registry.accept((itemStack, i) -> this.color | (0xFF << 24), new ItemLike[]{this.wool, this.carpet, this.terracotta, this.concrete, this.concretePowder, this.stainedGlass, this.dye});
    }

    public void addItemsToInventory(CreativeTab tab, ICreativeTabHelper inserter) {
        switch (tab) {
            case ColoredBlocks -> {
                inserter.putAfter(this.inventoryColorBefore + "_wool", this.wool);
                inserter.putAfter(this.inventoryColorBefore + "_carpet", this.carpet);
                inserter.putAfter(this.inventoryColorBefore + "_terracotta", this.terracotta);
                inserter.putAfter(this.inventoryColorBefore + "_concrete", this.concrete);
                inserter.putAfter(this.inventoryColorBefore + "_concrete_powder", this.concretePowder);
            }
            case Ingredients -> inserter.putAfter(this.inventoryColorBefore + "_dye", this.dye);
        }
    }

    public Block getWoolBlock() { return this.wool; }
    public Block getCarpetBlock() { return this.carpet; }
    public Block getTerracottaBlock() { return this.terracotta; }
    public Block getConcreteBlock() { return this.concrete; }
    public Block getConcretePowderBlock() { return this.concretePowder; }
    public Block getStainedGlassBlock() { return this.stainedGlass; }

    public Item getDyeItem() { return this.dye; }

    public static void registerAllBlockColors(BiConsumer<BlockColor, Block[]> registry) {
        COLORS.forEach(color -> color.registerBlockColors(registry));
    }

    public static void registerAllItemColors(BiConsumer<ItemColor, ItemLike[]> registry) {
        COLORS.forEach(color -> color.registerItemColors(registry));
    }

    @Override
    public @NotNull Item asItem() {
        return this.getDyeItem();
    }
}
