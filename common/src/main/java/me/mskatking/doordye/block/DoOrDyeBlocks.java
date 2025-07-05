package me.mskatking.doordye.block;

import me.mskatking.doordye.registry.CommonBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class DoOrDyeBlocks {
    public static final Block BLUEBERRY_BUSH = CommonBlocks.register("blueberry_bush", BlueberryBushBlock::new, BlockBehaviour.Properties.of(), false);

    public static void registerBlocks() { }
}
