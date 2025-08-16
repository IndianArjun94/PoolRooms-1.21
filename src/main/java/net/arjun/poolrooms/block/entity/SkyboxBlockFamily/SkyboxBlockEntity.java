package net.arjun.poolrooms.block.entity.SkyboxBlockFamily;

import net.arjun.poolrooms.block.entity.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class SkyboxBlockEntity extends BlockEntity {
    public SkyboxBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SKYBOX_BLOCK_ENTITY, pos, state);
    }

    public boolean shouldDrawSide(Direction direction) {
        return true;
    }

}
