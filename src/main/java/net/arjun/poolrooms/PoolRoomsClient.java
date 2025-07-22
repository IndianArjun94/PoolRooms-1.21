package net.arjun.poolrooms;

import net.arjun.poolrooms.block.entity.LightSkyboxBlockFamily.LightSkyboxBlockEntityRenderer;
import net.arjun.poolrooms.block.entity.ModBlockEntities;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class PoolRoomsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererFactories.register(ModBlockEntities.LIGHT_SKYBOX_BLOCK_ENTITY, LightSkyboxBlockEntityRenderer::new);
    }
}
