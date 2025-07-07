package me.mskatking.doordye.block;

import me.mskatking.doordye.item.DoOrDyeItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

// TODO: needs block texture
public class BlueberryBushBlock extends SweetBerryBushBlock {
    public BlueberryBushBlock(Properties p_57249_) {
        super(p_57249_.mapColor(MapColor.PLANT).noCollission().sound(SoundType.SWEET_BERRY_BUSH).randomTicks().pushReaction(PushReaction.DESTROY));
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull BlockHitResult pHitResult) {
        if (pState.getValue(AGE) == 3) {
            popResource(pLevel, pPos, new ItemStack(DoOrDyeItems.BLUEBERRIES, 1 + pLevel.random.nextInt(2) + (pState.getValue(AGE) == 3 ? 1 : 0)));
            // TODO: add custom sound to this
            pLevel.playSound(null, pPos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + pLevel.random.nextFloat() * 0.4F);
            pLevel.setBlockAndUpdate(pPos, pState.setValue(AGE, 1));
            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }

        return super.useWithoutItem(pState, pLevel, pPos, pPlayer, pHitResult);
    }
}
