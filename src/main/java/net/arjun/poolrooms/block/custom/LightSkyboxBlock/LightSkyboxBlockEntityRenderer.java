package net.arjun.poolrooms.block.custom.LightSkyboxBlock;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager; // For accessing block model
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos; // You might need this for context

import org.lwjgl.opengl.GL11; // Ensure this is imported

public class LightSkyboxBlockEntityRenderer implements BlockEntityRenderer<LightSkyboxBlockEntity> {

    // You might need a BlockRenderManager instance
    private final BlockRenderManager blockRenderManager;

    public LightSkyboxBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) { // or similar constructor
        this.blockRenderManager = ctx.getRenderManager();
    }

    @Override
    public void render(LightSkyboxBlockEntity entity, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light, int overlay) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        double distance = client.player.squaredDistanceTo(entity.getPos().toCenterPos());
        if (distance > 100) return;

        matrices.push();

        // Optional: Perform any transformations here if the skybox isn't perfectly aligned
        // For example, if you want it to appear slightly rotated or scaled
        // matrices.translate(0.5, 0.5, 0.5); // Center the origin
        // matrices.multiply(Quaternion.fromEulerXyz(0, entity.getRotation(), 0)); // Example rotation
        // matrices.translate(-0.5, -0.5, -0.5); // Move back

        // 1. Get the block's model
        // You'll need the BlockState to get the correct model
        BlockPos pos = entity.getPos();
        BlockState blockState = entity.getWorld().getBlockState(pos);
        BakedModel model = blockRenderManager.getModel(blockState);


        // 2. Get the vertex consumer for rendering
        // RenderLayer.solid() is for opaque blocks
        // RenderLayer.cutoutMipped() or RenderLayer.translucent() for transparency
        VertexConsumer consumer = vertexConsumers.getBuffer(RenderLayer.getSolid());

        // 3. Render the block model
        // The last parameter determines if ambient occlusion is applied
        blockRenderManager.getModelRenderer().render(matrices.peek(), consumer, blockState, model,
                1.0F, 1.0F, 1.0F, light, overlay);

        matrices.pop();

        // The GL calls for clearing the screen should be removed or moved to a different context
        // if they are not intended to be part of the actual skybox rendering.
        // RenderSystem.disableDepthTest();
        // RenderSystem.depthFunc(GL11.GL_ALWAYS);
        // RenderSystem.clearColor(0.1f, 0.1f, 0.3f, 1.0f); // This would clear the *entire* screen
        // RenderSystem.clear(GL11.GL_COLOR_BUFFER_BIT, false);
        // RenderSystem.depthFunc(GL11.GL_LEQUAL);
        // RenderSystem.enableDepthTest();
    }
}