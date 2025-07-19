package net.arjun.poolrooms.block.custom.LightSkyboxBlock;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;

public class LightSkyboxBlockEntityRenderer implements BlockEntityRenderer<LightSkyboxBlockEntity> {
    private static final Identifier SKYBOX_FRONT = Identifier.of("pool-rooms-mod", "textures/skybox/light_skybox/front.png");
    private static final Identifier SKYBOX_BACK = Identifier.of("pool-rooms-mod", "textures/skybox/light_skybox/back.png");
    private static final Identifier SKYBOX_LEFT = Identifier.of("pool-rooms-mod", "textures/skybox/light_skybox/left.png");
    private static final Identifier SKYBOX_RIGHT = Identifier.of("pool-rooms-mod", "textures/skybox/light_skybox/right.png");
    private static final Identifier SKYBOX_TOP = Identifier.of("pool-rooms-mod", "textures/skybox/light_skybox/top.png");
    private static final Identifier SKYBOX_BOTTOM = Identifier.of("pool-rooms-mod", "textures/skybox/light_skybox/bottom.png");

    public LightSkyboxBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {}

    @Override
    public void render(LightSkyboxBlockEntity entity, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light, int overlay) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        double distance = client.player.squaredDistanceTo(entity.getPos().toCenterPos());
        if (distance > 100) return;

        matrices.push();

        RenderSystem.disableDepthTest();
        RenderSystem.depthFunc(GL11.GL_ALWAYS);
        RenderSystem.clearColor(0.1f, 0.1f, 0.3f, 1.0f); // Replace with cube later
        RenderSystem.clear(GL11.GL_COLOR_BUFFER_BIT, false);
        RenderSystem.depthFunc(GL11.GL_LEQUAL);
        RenderSystem.enableDepthTest();

        matrices.pop();
    }
}