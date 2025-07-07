package me.mskatking.doordye.block;

import me.mskatking.doordye.registry.CommonBlocks;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class DoOrDyeBlocks {
    public static final Block BLUEBERRY_BUSH = CommonBlocks.register("blueberry_bush", BlueberryBushBlock::new, BlockBehaviour.Properties.of(), false);
    public static final Block VANILLA_VINES = CommonBlocks.register("vanilla_vines", VanillaVineBlock::new, BlockBehaviour.Properties.of(), true);
    public static final Block LAVENDER_FLOWER = CommonBlocks.register("lavender_flower", TallFlowerBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).ignitedByLava().pushReaction(PushReaction.DESTROY), true);
    public static final Block CALLA_LILY = CommonBlocks.register("calla_lily", prop -> new FlowerBlock(SuspiciousStewEffects.EMPTY, prop), BlockBehaviour.Properties.of().noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).ignitedByLava().pushReaction(PushReaction.DESTROY).dynamicShape(), true);

    public static void registerBlocks() { }
}
