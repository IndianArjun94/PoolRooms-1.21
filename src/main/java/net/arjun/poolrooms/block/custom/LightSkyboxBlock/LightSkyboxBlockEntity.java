package net.arjun.poolrooms.block.custom.LightSkyboxBlock;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class LightSkyboxBlockEntity extends BlockEntity {
    public LightSkyboxBlockEntity(BlockPos pos, BlockState state) {
        super(YourMod.SKYBOX_BLOCK_ENTITY_TYPE, pos, state);
    }
}