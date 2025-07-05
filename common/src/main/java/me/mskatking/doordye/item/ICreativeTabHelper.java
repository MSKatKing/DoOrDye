package me.mskatking.doordye.item;

import net.minecraft.world.level.ItemLike;

import javax.annotation.Nullable;

public interface ICreativeTabHelper {
    void append(ItemLike item);
    void putBefore(@Nullable ItemLike before, ItemLike stack);
    void putAfter(@Nullable ItemLike after, ItemLike stack);
}
