package me.mskatking.doordye;

import me.mskatking.doordye.inventory.ICreativeTabHelper;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class NeoForgeCreativeTabHelper implements ICreativeTabHelper {
    private final BuildCreativeModeTabContentsEvent inner;

    public NeoForgeCreativeTabHelper(BuildCreativeModeTabContentsEvent inner) {
        this.inner = inner;
    }

    @Override
    public void append(ItemLike stack) {
        if (this.findItem(stack).isPresent()) return; // Don't add items more than once.

        inner.accept(stack);
    }

    @Override
    public void putBefore(@Nullable ItemLike before, ItemLike stack) {
        if (before == null) {
            append(stack);
            return;
        }

        Optional<ItemStack> beforeStack = this.findItem(before);
        if (beforeStack.isPresent()) {
            this.putBefore(beforeStack.get(), stack);
        } else {
            append(stack);
        }
    }

    @Override
    public void putAfter(@Nullable ItemLike after, ItemLike stack) {
        if (after == null) {
            append(stack);
            return;
        }

        Optional<ItemStack> afterStack = this.findItem(after);
        if (afterStack.isPresent()) {
            this.putAfter(afterStack.get(), stack);
        } else {
            append(stack);
        }
    }

    @Override
    public void putBefore(@NotNull ItemStack before, ItemLike stack) {
        if (this.findItem(stack).isPresent()) return; // Don't add items more than once.

        inner.getEntries().putBefore(before, new ItemStack(stack), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
    }

    @Override
    public void putAfter(@NotNull ItemStack after, ItemLike stack) {
        if (this.findItem(stack).isPresent()) return; // Don't add items more than once.

        inner.getEntries().putAfter(after, new ItemStack(stack), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
    }

    @Override
    public void putBefore(@NotNull String before, ItemLike stack) {
        Optional<ItemStack> beforeStack = this.findItem(ResourceLocation.parse(before));
        if (beforeStack.isPresent()) {
            this.putBefore(beforeStack.get(), stack);
        } else {
            this.append(stack);
        }
    }

    @Override
    public void putAfter(@NotNull String after, ItemLike stack) {
        Optional<ItemStack> afterStack = this.findItem(ResourceLocation.parse(after));
        if (afterStack.isPresent()) {
            this.putAfter(afterStack.get(), stack);
        } else {
            this.append(stack);
        }
    }

    @Override
    public Optional<ItemStack> findItem(@NotNull ResourceLocation id) {
        Item item = BuiltInRegistries.ITEM.get(id);

        return this.findItem(item);
    }

    @Override
    public Optional<ItemStack> findItem(@NotNull ItemLike item) {
        AtomicReference<ItemStack> out = new AtomicReference<>();
        inner.getEntries().forEach(entry -> {
            if (entry.getKey().is(item.asItem())) {
                out.set(entry.getKey());
            }
        });

        return Optional.ofNullable(out.get());
    }
}
