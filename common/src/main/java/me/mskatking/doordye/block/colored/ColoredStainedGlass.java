package me.mskatking.doordye.block.colored;

import me.mskatking.doordye.Constants;
import me.mskatking.doordye.block.FallbackBlockState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.StainedGlassBlock;
import org.jetbrains.annotations.NotNull;

public class ColoredStainedGlass extends StainedGlassBlock implements FallbackBlockState {
    public ColoredStainedGlass(Properties p_56834_) {
        super(DyeColor.BLACK, p_56834_.noOcclusion().isViewBlocking((state, level, pos) -> false));
    }

    @Override
    public @NotNull ResourceLocation fallbackBlockState() {
        return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "colored_stained_glass");
    }
}
