package me.mskatking.doordye.block;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public interface FallbackBlockState {
    @NotNull ResourceLocation fallbackBlockState();
}
