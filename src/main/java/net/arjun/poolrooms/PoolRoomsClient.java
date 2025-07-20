package net.arjun.poolrooms;

import net.arjun.poolrooms.block.ModBlocks;
import net.arjun.poolrooms.block.custom.LightSkyboxBlock.LightSkyboxBlockEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class PoolRoomsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LIGHT_SKYBOX, RenderLayer.getCutout()); // Or getTranslucent()
        BlockEntityRendererFactories.register(ModBlocks.LIGHT_SKYBOX_BLOCK_ENTITY_TYPE, LightSkyboxBlockEntityRenderer::new);
    }
}
