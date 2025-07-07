package me.mskatking.doordye.block;

import me.mskatking.doordye.item.DoOrDyeItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// TODO: needs block texture
// TODO: blockstate needs to add age models
public class VanillaVineBlock extends Block implements BonemealableBlock {
    public static final int MAX_HEIGHT = 8;
    public static final IntegerProperty HEIGHT = IntegerProperty.create("height", 1, MAX_HEIGHT);
    public static final EnumProperty<VineDirection> DIRECTION = EnumProperty.create("direction", VineDirection.class);
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;

    private static final VoxelShape WEST_FULL_AABB = Block.box(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
    private static final VoxelShape EAST_FULL_AABB = Block.box(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    private static final VoxelShape NORTH_FULL_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
    private static final VoxelShape SOUTH_FULL_AABB = Block.box(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);

    private static final VoxelShape WEST_HALF_AABB = Block.box(0.0, 0.0, 0.0, 1.0, 8.0, 16.0);
    private static final VoxelShape EAST_HALF_AABB = Block.box(15.0, 0.0, 0.0, 16.0, 8.0, 16.0);
    private static final VoxelShape NORTH_HALF_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 1.0);
    private static final VoxelShape SOUTH_HALF_AABB = Block.box(0.0, 0.0, 15.0, 16.0, 8.0, 16.0);

    public VanillaVineBlock(Properties p_57847_) {
        super(p_57847_.replaceable().noCollission().randomTicks().strength(0.2F).sound(SoundType.VINE).ignitedByLava().pushReaction(PushReaction.DESTROY));
        this.registerDefaultState(this.defaultBlockState().setValue(AGE, 0).setValue(HEIGHT, 1).setValue(DIRECTION, VineDirection.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(AGE);
        pBuilder.add(HEIGHT);
        pBuilder.add(DIRECTION);
    }

    @Override
    protected @NotNull VoxelShape getShape(BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        VoxelShape shape = Shapes.empty();

        boolean isTop = pState.getValue(HEIGHT) == MAX_HEIGHT;
        switch (pState.getValue(DIRECTION)) {
            case NORTH -> shape = isTop ? NORTH_HALF_AABB : NORTH_FULL_AABB;
            case SOUTH -> shape = isTop ? SOUTH_HALF_AABB : SOUTH_FULL_AABB;
            case EAST -> shape = isTop ? EAST_HALF_AABB : EAST_FULL_AABB;
            case WEST -> shape = isTop ? WEST_HALF_AABB : WEST_FULL_AABB;
        }

        return shape.isEmpty() ? Shapes.block() : shape;
    }

    @Override
    protected boolean canSurvive(@NotNull BlockState pState, LevelReader pLevel, BlockPos pPos) {
        if (pLevel.isEmptyBlock(pPos.below())) return false;
        if (!pLevel.getBlockState(pPos.below()).isSolidRender(pLevel, pPos.below()) && !pLevel.getBlockState(pPos.below()).is(this)) return false;

        Direction facing = pState.getValue(DIRECTION).getDirection();
        return pLevel.getBlockState(pPos.relative(facing)).is(BlockTags.JUNGLE_LOGS);
    }

    @Override
    protected void randomTick(@NotNull BlockState pState, ServerLevel pLevel, @NotNull BlockPos pPos, @NotNull RandomSource pRandom) {
        int height = pState.getValue(HEIGHT);
        if (pLevel.getRawBrightness(pPos, 0) >= 9 && height != MAX_HEIGHT) {
            int age = pState.getValue(AGE);
            // TODO: possibly change growth speed mechanics
            if (age < 3 && pRandom.nextInt(20 - pLevel.getRawBrightness(pPos, 0)) == 0) {
                pLevel.setBlockAndUpdate(pPos, pState.setValue(AGE, age + 1));
            }

            // TODO: play around with this as well
            Direction dir = pState.getValue(DIRECTION).getDirection();
            if (pLevel.getBlockState(pPos.relative(dir).above()).is(BlockTags.JUNGLE_LOGS) && pRandom.nextInt(5) == 0 && pLevel.isEmptyBlock(pPos.above())) {
                pLevel.setBlockAndUpdate(pPos.above(), pState.setValue(HEIGHT, height + 1));
            }
        }
    }

    @Override
    protected void tick(@NotNull BlockState pState, @NotNull ServerLevel pLevel, @NotNull BlockPos pPos, @NotNull RandomSource pRandom) {
        if (!this.canSurvive(pState, pLevel, pPos)) {
            pLevel.destroyBlock(pPos, true);
        }
    }

    @Override
    protected @NotNull BlockState updateShape(BlockState pState, @NotNull Direction pFacing, @NotNull BlockState pFacingState, @NotNull LevelAccessor pLevel, @NotNull BlockPos pCurrentPos, @NotNull BlockPos pFacingPos) {
        if (!pState.canSurvive(pLevel, pCurrentPos)) {
            pLevel.scheduleTick(pCurrentPos, this, 1);
        }

        return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Level level = pContext.getLevel();
        BlockPos pos = pContext.getClickedPos();
        VineDirection clickedDir = VineDirection.fromDirection(pContext.getClickedFace());
        if (clickedDir == null) return null;

        int height = 1;
        while (height <= MAX_HEIGHT) {
            pos = pos.below();
            if (!level.isEmptyBlock(pos) && level.getBlockState(pos).is(DoOrDyeBlocks.VANILLA_VINES))
                height++;
            else
                break;
        }

        if (height > MAX_HEIGHT) return null;

        return this.defaultBlockState().setValue(HEIGHT, height).setValue(DIRECTION, clickedDir.opposite());
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

    public enum VineDirection implements StringRepresentable {
        NORTH(Direction.NORTH),
        SOUTH(Direction.SOUTH),
        EAST(Direction.EAST),
        WEST(Direction.WEST);

        final Direction direction;

        VineDirection(Direction direction) {
            this.direction = direction;
        }

        public VineDirection opposite() {
            return fromDirection(this.direction.getOpposite());
        }

        public Direction getDirection() {
            return this.direction;
        }

        public static @Nullable VineDirection fromDirection(Direction direction) {
            for (VineDirection dir : values())
                if (dir.direction == direction)
                    return dir;

            return null;
        }

        @Override
        public @NotNull String getSerializedName() {
            return this.toString().toLowerCase();
        }
    }
}
