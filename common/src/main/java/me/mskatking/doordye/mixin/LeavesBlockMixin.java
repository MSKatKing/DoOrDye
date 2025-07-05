package me.mskatking.doordye.mixin;

import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LeavesBlock.class)
public abstract class LeavesBlockMixin extends Block {
    protected LeavesBlockMixin(BlockBehaviour.Properties p) {
        super(p);
    }

    @Inject(method = "createBlockStateDefinition", at = @At("TAIL"))
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder, CallbackInfo callbackInfo) {

    }

    @Inject(method = "getStateForPlacement", at = @At("RETURN"), cancellable = true)
    public void getStateForPlacement(BlockPlaceContext pContext, CallbackInfoReturnable<BlockState> cir) {

    }
}
