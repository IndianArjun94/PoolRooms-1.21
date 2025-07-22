package net.arjun.poolrooms.block.entity.LightSkyboxBlockFamily;

import net.arjun.poolrooms.block.entity.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class LightSkyboxBlockEntity extends BlockEntity {
    public LightSkyboxBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.LIGHT_SKYBOX_BLOCK_ENTITY, pos, state);
    }

    public boolean shouldDrawSide(Direction direction) {
        return direction.getAxis() == Direction.Axis.Y;
    }

}
