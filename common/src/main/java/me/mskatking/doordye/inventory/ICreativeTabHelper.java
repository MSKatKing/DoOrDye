package me.mskatking.doordye.inventory;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

// TODO: more annotations
public interface ICreativeTabHelper {
    void append(ItemLike item);
    void putBefore(@Nullable ItemLike before, ItemLike stack);
    void putAfter(@Nullable ItemLike after, ItemLike stack);

    void putBefore(@NotNull ItemStack before, ItemLike stack);
    void putAfter(@NotNull ItemStack after, ItemLike stack);

    void putBefore(@NotNull String before, ItemLike stack);
    void putAfter(@NotNull String after, ItemLike stack);

    Optional<ItemStack> findItem(@NotNull ResourceLocation id);
    Optional<ItemStack> findItem(@NotNull ItemLike item);
}
