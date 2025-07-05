package me.mskatking.doordye.mixin;

import me.mskatking.doordye.item.DoOrDyeItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(CherryLeavesBlock.class)
public final class CherryBlossomLeavesMixin extends LeavesBlockMixin implements BonemealableBlock {
    @Unique
    private static final IntegerProperty GROWTH_FACTOR = BlockStateProperties.AGE_5;

    @Unique
    private static final BooleanProperty CAN_GROW = BooleanProperty.create("growable");

    @Unique
    private static final Random GROW_RANDOM = new Random();

    private CherryBlossomLeavesMixin(Properties p) {
        super(p);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void constructor(BlockBehaviour.Properties p, CallbackInfo callbackInfo) {
        this.registerDefaultState(this.defaultBlockState().setValue(CAN_GROW, false).setValue(GROWTH_FACTOR, 0));
    }

    @Override
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder, CallbackInfo callbackInfo) {
        pBuilder.add(GROWTH_FACTOR, CAN_GROW);
    }

    @Override
    public void getStateForPlacement(BlockPlaceContext context, CallbackInfoReturnable<BlockState> cir) {
        cir.setReturnValue(cir.getReturnValue().setValue(CAN_GROW, GROW_RANDOM.nextDouble() > (7.0 / 8.0)).setValue(GROWTH_FACTOR, 0));
    }

    @Override
    public boolean isValidBonemealTarget(@NotNull LevelReader levelReader, @NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return blockState.getValue(GROWTH_FACTOR) != 5 || !blockState.getValue(CAN_GROW);
    }

    @Override
    public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource randomSource, @NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return blockState.getValue(GROWTH_FACTOR) != 5 || !blockState.getValue(CAN_GROW);
    }

    @Override
    public void performBonemeal(@NotNull ServerLevel serverLevel, @NotNull RandomSource randomSource, @NotNull BlockPos blockPos, BlockState blockState) {
        if (!blockState.getValue(CAN_GROW)) {
            serverLevel.setBlockAndUpdate(blockPos, blockState.setValue(CAN_GROW, true));
        } else {
            serverLevel.setBlockAndUpdate(blockPos, blockState.setValue(GROWTH_FACTOR, Math.min(blockState.getValue(GROWTH_FACTOR) + 1, 5)));
        }
    }

    @Override
    protected boolean isRandomlyTicking(BlockState pState) { return pState.getValue(CAN_GROW) && pState.getValue(GROWTH_FACTOR) != 5; }

    @Override
    protected void randomTick(@NotNull BlockState pState, @NotNull ServerLevel pLevel, @NotNull BlockPos pPos, @NotNull RandomSource pRandom) {
        if (pState.getValue(CAN_GROW) && pLevel.getRawBrightness(pPos, 0) >= 9) {
            int age = pState.getValue(GROWTH_FACTOR);
            if (age < 5 && pRandom.nextInt(25 - doOrDye$growthSpeed(pLevel, pPos, pState)) == 0) {
                pLevel.setBlockAndUpdate(pPos, pState.setValue(GROWTH_FACTOR, age + 1));
            }
        }
    }

    @Unique
    private int doOrDye$growthSpeed(@NotNull ServerLevel pLevel, @NotNull BlockPos pPos, @NotNull BlockState pState) {
        int brightness = pLevel.getRawBrightness(pPos, 0);
        int distance = 7 - pState.getValue(LeavesBlock.DISTANCE);
        boolean waterlogged = pState.getValue(BlockStateProperties.WATERLOGGED);

        return Math.max(0, brightness + distance - (waterlogged ? 10 : 0));
    }
}
