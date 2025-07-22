package net.arjun.poolrooms.block.entity.LightSkyboxBlockFamily;

import net.arjun.poolrooms.PoolRooms;
import net.arjun.poolrooms.PoolRoomsClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.EndPortalBlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import org.joml.Matrix4f;

import java.io.IOException;

import static net.minecraft.client.render.RenderPhase.END_GATEWAY_PROGRAM;

@Environment(EnvType.CLIENT)
public class LightSkyboxBlockEntityRenderer implements BlockEntityRenderer<LightSkyboxBlockEntity> {
//    public static final Identifier SKY_TEXTURE = Identifier.ofVanilla("textures/environment/end_sky.png");
//    public static final Identifier PORTAL_TEXTURE = Identifier.ofVanilla("textures/entity/end_portal.png");

    MinecraftClient mc = MinecraftClient.getInstance();



    public static RenderLayer LIGHT_SKYBOX_BLOCK_RENDER_LAYER;

    public LightSkyboxBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
//        PoolRooms.LOGGER.info("sigma constructed");

    }

    @Override
    public void render(LightSkyboxBlockEntity lightSkyboxBlockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j) {
        Matrix4f matrix4f = matrixStack.peek().getPositionMatrix();
//        PoolRooms.LOGGER.info("render called");
        this.renderSides(lightSkyboxBlockEntity, matrix4f, vertexConsumerProvider.getBuffer(this.getLayer()));
    }

    private void renderSides(LightSkyboxBlockEntity entity, Matrix4f matrix, VertexConsumer vertexConsumer) {
//        PoolRooms.LOGGER.info("rendering sides of skybox");
        float f = this.getBottomYOffset();
        float g = this.getTopYOffset();
        this.renderSide(entity, matrix, vertexConsumer, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, Direction.SOUTH);
        this.renderSide(entity, matrix, vertexConsumer, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, Direction.NORTH);
        this.renderSide(entity, matrix, vertexConsumer, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.EAST);
        this.renderSide(entity, matrix, vertexConsumer, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.WEST);
        this.renderSide(entity, matrix, vertexConsumer, 0.0F, 1.0F, f, f, 0.0F, 0.0F, 1.0F, 1.0F, Direction.DOWN);
        this.renderSide(entity, matrix, vertexConsumer, 0.0F, 1.0F, g, g, 1.0F, 1.0F, 0.0F, 0.0F, Direction.UP);
    }

    private void renderSide(
            LightSkyboxBlockEntity entity, Matrix4f model, VertexConsumer vertices, float x1, float x2, float y1, float y2, float z1, float z2, float z3, float z4, Direction side
    ) {
//        PoolRooms.LOGGER.info("rendering the skybox!");
        if (entity.shouldDrawSide(side)) {
            vertices.vertex(model, x1, y1, z1);
            vertices.vertex(model, x2, y1, z2);
            vertices.vertex(model, x2, y2, z3);
            vertices.vertex(model, x1, y2, z4);
        }
    }

    protected float getTopYOffset() {
        return 0.75F;
    }

    protected float getBottomYOffset() {
        return 0.375F;
    }

    protected RenderLayer getLayer() {
        return LIGHT_SKYBOX_BLOCK_RENDER_LAYER;
    }


}
