package me.mskatking.doordye.item;

import me.mskatking.doordye.registry.CommonBlocks;
import me.mskatking.doordye.registry.CommonItems;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.ConcretePowderBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class Dye {
    public static final Dye DRAGONS_BLOOD = new Dye(0x780606, "dragons_blood", new ItemLike[]{Items.BROWN_DYE, Items.BROWN_WOOL, Items.BROWN_CARPET, Items.BROWN_CONCRETE, Items.BROWN_CONCRETE_POWDER, Items.BROWN_TERRACOTTA});
    public static final Dye CHERRY = new Dye(0xD20A2E, "cherry", new ItemLike[]{Items.RED_DYE, Items.RED_WOOL, Items.RED_CARPET, Items.RED_CONCRETE, Items.RED_CONCRETE_POWDER, Items.RED_TERRACOTTA});
    public static final Dye BLAZING_RED = new Dye(0xFF0054, "blazing_red", new ItemLike[]{CHERRY.dyeItem, CHERRY.woolBlock, CHERRY.carpetBlock, CHERRY.concreteBlock, CHERRY.concretePowderBlock, CHERRY.terracottaBlock});
    public static final Dye CORAL = new Dye(0xFF7F50, "coral", new ItemLike[]{BLAZING_RED.dyeItem, BLAZING_RED.woolBlock, BLAZING_RED.carpetBlock, BLAZING_RED.concreteBlock, BLAZING_RED.concretePowderBlock, BLAZING_RED.terracottaBlock});
    public static final Dye LEMON = new Dye(0xFFF700, "lemon", new ItemLike[]{Items.YELLOW_DYE, Items.YELLOW_WOOL, Items.YELLOW_CARPET, Items.YELLOW_CONCRETE, Items.YELLOW_CONCRETE_POWDER, Items.YELLOW_TERRACOTTA});
    public static final Dye VANILLA = new Dye(0xF3E5AB, "vanilla", new ItemLike[]{LEMON.dyeItem, LEMON.woolBlock, LEMON.carpetBlock, LEMON.concreteBlock, LEMON.concretePowderBlock, LEMON.terracottaBlock});
    public static final Dye MINT = new Dye(0xADEBB3, "mint", new ItemLike[]{VANILLA.dyeItem, VANILLA.woolBlock, VANILLA.carpetBlock, VANILLA.concreteBlock, VANILLA.concretePowderBlock, VANILLA.terracottaBlock});
    public static final Dye SEAFOAM = new Dye(0x8DDCDC, "seafoam", new ItemLike[]{Items.CYAN_DYE, Items.CYAN_WOOL, Items.CYAN_CARPET, Items.CYAN_CONCRETE, Items.CYAN_CONCRETE_POWDER, Items.CYAN_TERRACOTTA});
    public static final Dye CELESTE = new Dye(0xB2FFFF, "celeste", new ItemLike[]{SEAFOAM.dyeItem, SEAFOAM.woolBlock, SEAFOAM.carpetBlock, SEAFOAM.concreteBlock, SEAFOAM.concretePowderBlock, SEAFOAM.terracottaBlock});
    public static final Dye INDIGO = new Dye(0x560591, "indigo", new ItemLike[]{Items.BLUE_DYE, Items.BLUE_WOOL, Items.BLUE_CARPET, Items.BLUE_CONCRETE, Items.BLUE_CONCRETE_POWDER, Items.BLUE_TERRACOTTA});
    public static final Dye ROYAL_PURPLE = new Dye(0x6C3BAA, "royal_purple", new ItemLike[]{Items.PURPLE_DYE, Items.PURPLE_WOOL, Items.PURPLE_CARPET, Items.PURPLE_CONCRETE, Items.PURPLE_CONCRETE_POWDER, Items.PURPLE_TERRACOTTA});
    public static final Dye FUCHSIA = new Dye(0x9D0759, "fuchsia", new ItemLike[]{ROYAL_PURPLE.dyeItem, ROYAL_PURPLE.woolBlock, ROYAL_PURPLE.carpetBlock, ROYAL_PURPLE.concreteBlock, ROYAL_PURPLE.concretePowderBlock, ROYAL_PURPLE.terracottaBlock});
    public static final Dye BLUSH = new Dye(0xFF7782, "blush", new ItemLike[]{Items.MAGENTA_DYE, Items.MAGENTA_WOOL, Items.MAGENTA_CARPET, Items.MAGENTA_CONCRETE, Items.MAGENTA_CONCRETE_POWDER, Items.MAGENTA_TERRACOTTA});
    public static final Dye LAVENDER = new Dye(0xD3D3FF, "lavender",new ItemLike[]{Items.PINK_DYE, Items.PINK_WOOL, Items.PINK_CARPET, Items.PINK_CONCRETE, Items.PINK_CONCRETE_POWDER, Items.PINK_TERRACOTTA});

    public static Dye[] dyes() { return new Dye[]{DRAGONS_BLOOD, CHERRY, BLAZING_RED, CORAL, LEMON, VANILLA, MINT, SEAFOAM, CELESTE, INDIGO, ROYAL_PURPLE, FUCHSIA, BLUSH, LAVENDER}; }

    private final int color;

    private final Block woolBlock;
    private final Block carpetBlock;
    private final Block terracottaBlock;

    private final Block concreteBlock;
    private final Block concretePowderBlock;

    private final ItemLike[] inventoryItemsBefore;

    private final Item dyeItem;

    public Dye(int hexColor, String id, ItemLike[] inventoryItemsBefore) {
        this.color = hexColor;

        this.woolBlock = CommonBlocks.register(id + "_wool", Block::new, BlockBehaviour.Properties.of(), true);
        this.carpetBlock = CommonBlocks.register(id + "_carpet", CarpetBlock::new, BlockBehaviour.Properties.of(), true);
        this.terracottaBlock = CommonBlocks.register(id + "_terracotta", Block::new, BlockBehaviour.Properties.of(), true);
        this.concreteBlock = CommonBlocks.register(id + "_concrete", Block::new, BlockBehaviour.Properties.of(), true);
        this.concretePowderBlock = CommonBlocks.register(id + "_concrete_powder", prop -> new ConcretePowderBlock(this.concreteBlock, prop), BlockBehaviour.Properties.of(), true);

        this.dyeItem = CommonItems.register(id + "_dye", Item::new, new Item.Properties());

        this.inventoryItemsBefore = inventoryItemsBefore;
    }

    public void registerColors(BlockColorRegistry registry) {
        registry.register((blockState, blockAndTintGetter, blockPos, i) -> this.color, this.woolBlock, this.concreteBlock, this.terracottaBlock, this.concreteBlock);
    }

    public void registerColors(ItemColorRegistry registry) {
        registry.register((stack, i) -> this.color, this.woolBlock.asItem(), this.carpetBlock.asItem(), this.terracottaBlock.asItem(), this.concreteBlock.asItem(), this.dyeItem);
    }

    public void addItemsToInventory(CreativeTab tab, ICreativeTabHelper inserter) {
        switch (tab) {
            case ColoredBlocks -> {
                inserter.putAfter(inventoryItemsBefore[1], this.woolBlock);
                inserter.putAfter(inventoryItemsBefore[2], this.carpetBlock);
                inserter.putAfter(inventoryItemsBefore[3], this.concreteBlock);
                inserter.putAfter(inventoryItemsBefore[4], this.concretePowderBlock);
                inserter.putAfter(inventoryItemsBefore[5], this.terracottaBlock);
            }
            case Ingredients -> inserter.putAfter(inventoryItemsBefore[0], this.dyeItem);
        }
    }

    public static void registerBlockColor(BlockColorRegistry registry) {
        for (Dye dye : dyes())
            dye.registerColors(registry);
    }

    public static void registerItemColor(ItemColorRegistry registry) {
        for (Dye dye : dyes())
            dye.registerColors(registry);
    }

    public static void registerDyes() { }

    public interface BlockColorRegistry {
        void register(BlockColor color, Block... blocks);
    }

    public interface ItemColorRegistry {
        void register(ItemColor color, Item... items);
    }
}
