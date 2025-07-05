package me.mskatking.doordye.client;

import me.mskatking.doordye.item.ICreativeTabHelper;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.minecraft.world.level.ItemLike;

public class FabricCreativeTabHelper implements ICreativeTabHelper {
    private final FabricItemGroupEntries inner;

    public FabricCreativeTabHelper(FabricItemGroupEntries inner) {
        this.inner = inner;
    }

    @Override
    public void append(ItemLike stack) {
        inner.accept(stack);
    }

    @Override
    public void putBefore(ItemLike before, ItemLike stack) {
        if (before == null) {
            append(stack);
        } else {
            inner.addBefore(before, stack);
        }
    }

    @Override
    public void putAfter(ItemLike after, ItemLike stack) {
        if (after == null) {
            append(stack);
        } else {
            inner.addAfter(after, stack);
        }
    }
}
