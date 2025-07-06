package me.mskatking.doordye.client;

import me.mskatking.doordye.inventory.ICreativeTabHelper;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

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

    @Override
    public void putBefore(@NotNull ItemStack before, ItemLike stack) {
        this.putBefore(before.getItem(), stack);
    }

    @Override
    public void putAfter(@NotNull ItemStack after, ItemLike stack) {
        this.putAfter(after.getItem(), stack);
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
        return this.findItem(BuiltInRegistries.ITEM.get(id));
    }

    @Override
    public Optional<ItemStack> findItem(@NotNull ItemLike item) {
        AtomicReference<ItemStack> out = new AtomicReference<>();
        inner.getDisplayStacks().forEach(stack -> {
            if (stack.is(item.asItem())) {
                out.set(stack);
            }
        });

        return Optional.ofNullable(out.get());
    }
}
