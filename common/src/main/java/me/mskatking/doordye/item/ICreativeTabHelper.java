package me.mskatking.doordye.item;

import net.minecraft.world.level.ItemLike;

public interface ICreativeTabHelper {
    void append(ItemLike item);
    void putBefore(ItemLike before, ItemLike stack);
    void putAfter(ItemLike after, ItemLike stack);
}
