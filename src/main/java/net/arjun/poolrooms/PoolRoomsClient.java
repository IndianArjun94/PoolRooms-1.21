package net.arjun.poolrooms;

import net.arjun.poolrooms.block.ModBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class PoolRoomsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LIGHT_SKYBOX, RenderLayer.getCutout()); // Or getTranslucent()
    }
}
