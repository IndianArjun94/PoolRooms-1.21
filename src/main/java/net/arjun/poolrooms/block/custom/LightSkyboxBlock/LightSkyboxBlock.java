package net.arjun.poolrooms.block.custom.LightSkyboxBlock;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.scoreboard.ScoreboardCriterion;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class LightSkyboxBlock extends BlockWithEntity {
    private static final VoxelShape SHAPE = Block.createCuboidShape(0, 0, 0, 16, 16, 16);
    public static final MapCodec<LightSkyboxBlock> CODEC = LightSkyboxBlock.createCodec(LightSkyboxBlock::new);

    public LightSkyboxBlock(Settings settings) {
        super(AbstractBlock.Settings.copy(Blocks.GLASS).nonOpaque().strength(99999).dropsNothing());
    }

    public LightSkyboxBlock() {
        super(AbstractBlock.Settings.copy(Blocks.GLASS).nonOpaque().strength(99999).dropsNothing());
    }


    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new LightSkyboxBlockEntity(pos, state);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE; // No collision
    }

    @Override
    public boolean hasSidedTransparency(BlockState state) {
        return true; // Ensures all sides are see-through
    }
}