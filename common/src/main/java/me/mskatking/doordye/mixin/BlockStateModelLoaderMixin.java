package me.mskatking.doordye.mixin;

import me.mskatking.doordye.Constants;
import me.mskatking.doordye.block.FallbackBlockState;
import net.minecraft.client.resources.model.BlockStateModelLoader;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.List;
import java.util.Map;

@Mixin(BlockStateModelLoader.class)
public class BlockStateModelLoaderMixin {
    @Final
    @Shadow
    public static FileToIdConverter BLOCKSTATE_LISTER;

    @Final
    @Shadow
    private Map<ResourceLocation, List<BlockStateModelLoader.LoadedJson>> blockStateResources;

    @ModifyArg(method = "loadBlockStateDefinitions", at = @At(value = "INVOKE", target = "Lnet/minecraft/resources/FileToIdConverter;idToFile(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/resources/ResourceLocation;"))
    private ResourceLocation loadDefaultBlockState(ResourceLocation blockStateId) {
        boolean uniqueExists = blockStateResources.containsKey(BLOCKSTATE_LISTER.idToFile(blockStateId));

        Block block = BuiltInRegistries.BLOCK.get(blockStateId);
        if (block != null && !uniqueExists && block instanceof FallbackBlockState fallbackBlockState) {
            Constants.LOG.info("Loading fallback blockstate for {}", blockStateId);
            return fallbackBlockState.fallbackBlockState();
        }

        return blockStateId;
    }
}
