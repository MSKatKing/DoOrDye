package me.mskatking.doordye.block;

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
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// TODO: needs block texture
public class VanillaVineBlock extends VineBlock implements BonemealableBlock {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;

    public static final int MAX_HEIGHT = 8;
    public static final IntegerProperty HEIGHT = IntegerProperty.create("height", 1, MAX_HEIGHT);

    private static final VoxelShape WEST_HALF_AABB = Block.box(0.0, 0.0, 0.0, 1.0, 8.0, 16.0);
    private static final VoxelShape EAST_HALF_AABB = Block.box(15.0, 0.0, 0.0, 16.0, 8.0, 16.0);
    private static final VoxelShape NORTH_HALF_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 1.0);
    private static final VoxelShape SOUTH_HALF_AABB = Block.box(0.0, 0.0, 15.0, 16.0, 8.0, 16.0);

    public VanillaVineBlock(Properties p_57847_) {
        super(p_57847_.replaceable().noCollission().randomTicks().strength(0.2F).sound(SoundType.VINE).ignitedByLava().pushReaction(PushReaction.DESTROY));
        this.registerDefaultState(this.defaultBlockState().setValue(AGE, 0).setValue(HEIGHT, 1));
    }

    @Override
    protected @NotNull VoxelShape getShape(BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        VoxelShape shape = Shapes.empty();
        if (pState.getValue(HEIGHT) == MAX_HEIGHT) {
            if (pState.getValue(NORTH)) shape = NORTH_HALF_AABB;
            else if (pState.getValue(SOUTH)) shape = SOUTH_HALF_AABB;
            else if (pState.getValue(EAST)) shape = EAST_HALF_AABB;
            else if (pState.getValue(WEST)) shape = WEST_HALF_AABB;
        } else {
            shape = super.getShape(pState, pLevel, pPos, pContext);
        }

        return shape.isEmpty() ? Shapes.block() : shape;
    }

    @Override
    protected boolean canSurvive(@NotNull BlockState pState, LevelReader pLevel, BlockPos pPos) {
        if (pLevel.isEmptyBlock(pPos.below())) return false;

        if (pState.getValue(NORTH) && (pLevel.isEmptyBlock(pPos.north()) || !pLevel.getBlockState(pPos.north()).is(Blocks.JUNGLE_LOG))) return false;
        if (pState.getValue(SOUTH) && (pLevel.isEmptyBlock(pPos.south()) || !pLevel.getBlockState(pPos.south()).is(Blocks.JUNGLE_LOG))) return false;
        if (pState.getValue(EAST) && (pLevel.isEmptyBlock(pPos.east()) || !pLevel.getBlockState(pPos.east()).is(Blocks.JUNGLE_LOG))) return false;
        if (pState.getValue(WEST) && (pLevel.isEmptyBlock(pPos.west()) || !pLevel.getBlockState(pPos.west()).is(Blocks.JUNGLE_LOG))) return false;

        if (pState.getValue(UP)) return false;

        return this.countFaces(pState) == 1;
    }

    private int countFaces(BlockState pState) {
        int faces = 0;
        for (BooleanProperty dir : PROPERTY_BY_DIRECTION.values()) {
            if (pState.getValue(dir))
                faces++;
        }

        return faces;
    }

    @Override
    protected void randomTick(@NotNull BlockState pState, ServerLevel pLevel, @NotNull BlockPos pPos, @NotNull RandomSource pRandom) {
        if (pLevel.getRawBrightness(pPos, 0) >= 9) {
            int age = pState.getValue(AGE);
            // TODO: possibly change growth speed mechanics
            if (age < 3 && pRandom.nextInt(20 - pLevel.getRawBrightness(pPos, 0)) == 0) {
                pLevel.setBlockAndUpdate(pPos, pState.setValue(AGE, age + 1));
            }
        }
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Level level = pContext.getLevel();
        BlockPos pos = pContext.getClickedPos();
        int height = 1;
        while (height <= MAX_HEIGHT) {
            pos = pos.below();
            if (!level.isEmptyBlock(pos) && level.getBlockState(pos).is(DoOrDyeBlocks.VANILLA_VINES))
                height++;
            else
                break;
        }

        if (height > MAX_HEIGHT) return null;
        BlockState state = super.getStateForPlacement(pContext);
        if (state == null) return null;

        return state.setValue(HEIGHT, height);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(AGE);
        pBuilder.add(HEIGHT);
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack pStack, @NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHitResult) {
        return this.useWithoutItem(pState, pLevel, pPos, pPlayer, pHitResult) == InteractionResult.SUCCESS ? ItemInteractionResult.SUCCESS : ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull BlockHitResult pHitResult) {
        if (pState.getValue(AGE) == 3) {
            Block.popResourceFromFace(pLevel, pPos, pHitResult.getDirection(), new ItemStack(DoOrDyeItems.VANILLA_BEANS, pLevel.random.nextInt(2, 6)));
            pLevel.setBlockAndUpdate(pPos, pState.setValue(AGE, 0));
            // TODO: need custom sound
            pLevel.playSound(null, pPos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + pLevel.random.nextFloat() * 0.4F);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    public boolean isValidBonemealTarget(@NotNull LevelReader levelReader, @NotNull BlockPos blockPos, BlockState blockState) {
        return blockState.getValue(AGE) != 3;
    }

    @Override
    public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource randomSource, @NotNull BlockPos blockPos, BlockState blockState) {
        return blockState.getValue(AGE) != 3;
    }

    @Override
    public void performBonemeal(@NotNull ServerLevel serverLevel, @NotNull RandomSource randomSource, @NotNull BlockPos blockPos, BlockState blockState) {
        int age = blockState.getValue(AGE);

        if (age != 3) {
            serverLevel.setBlockAndUpdate(blockPos, blockState.setValue(AGE, age + 1));
        }
    }
}
