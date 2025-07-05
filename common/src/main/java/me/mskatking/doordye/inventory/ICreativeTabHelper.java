package me.mskatking.doordye.inventory;

import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

public interface ICreativeTabHelper {
    void append(ItemLike item);
    void putBefore(@Nullable ItemLike before, ItemLike stack);
    void putAfter(@Nullable ItemLike after, ItemLike stack);
}
