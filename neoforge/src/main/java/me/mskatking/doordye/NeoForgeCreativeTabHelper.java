package me.mskatking.doordye;

import me.mskatking.doordye.item.ICreativeTabHelper;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class NeoForgeCreativeTabHelper implements ICreativeTabHelper {
    private final BuildCreativeModeTabContentsEvent inner;

    public NeoForgeCreativeTabHelper(BuildCreativeModeTabContentsEvent inner) {
        this.inner = inner;
    }

    private Optional<ItemStack> findItemStack(ItemLike item) {
        AtomicReference<ItemStack> out = new AtomicReference<>();
        inner.getEntries().forEach(entry -> {
            if (entry.getKey().is(item.asItem())) {
                out.set(entry.getKey());
            }
        });

        return Optional.ofNullable(out.get());
    }

    @Override
    public void append(ItemLike stack) {
        inner.accept(stack);
    }

    @Override
    public void putBefore(ItemLike before, ItemLike stack) {
        Optional<ItemStack> beforeStack = findItemStack(before);
        if (beforeStack.isPresent()) {
            inner.getEntries().putBefore(beforeStack.get(), new ItemStack(stack), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        } else {
            append(stack);
        }
    }

    @Override
    public void putAfter(ItemLike after, ItemLike stack) {
        Optional<ItemStack> afterStack = findItemStack(after);
        if (afterStack.isPresent()) {
            inner.getEntries().putAfter(afterStack.get(), new ItemStack(stack), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        } else {
            append(stack);
        }
    }
}
