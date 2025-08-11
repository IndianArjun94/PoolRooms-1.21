package net.arjun.poolrooms.block.entity.LightSkyboxBlockFamily;

import com.mojang.blaze3d.systems.RenderSystem;
import net.arjun.poolrooms.PoolRooms;
import net.arjun.poolrooms.PoolRoomsClient; // Import your client class to access the shader

import net.minecraft.client.MinecraftClient; // To get the player instance
import net.minecraft.client.gl.ShaderProgram; // For the shader program type
import net.minecraft.client.gl.Uniform; // For the Uniform type
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.BlockModelRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity; // To get player yaw
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import static net.arjun.poolrooms.PoolRoomsClient.LIGHT_SKYBOX_PROGRAM;
import static net.arjun.poolrooms.PoolRoomsClient.LIGHT_SKYBOX_SHADER;

// Assuming LightSkyboxBlockEntity is in this package or imported correctly
// import net.arjun.poolrooms.block.entity.LightSkyboxBlockFamily.LightSkyboxBlockEntity;

@Environment(EnvType.CLIENT)
public class LightSkyboxBlockEntityRenderer implements BlockEntityRenderer<LightSkyboxBlockEntity> {

    public static Identifier LIGHT_SKYBOX_NORTH_TEXTURE = Identifier.of(PoolRooms.MOD_ID, "textures/environment/light_skybox_front.png");
    public static Identifier LIGHT_SKYBOX_EAST_TEXTURE = Identifier.of(PoolRooms.MOD_ID, "textures/environment/light_skybox_right.png");
    public static Identifier LIGHT_SKYBOX_SOUTH_TEXTURE = Identifier.of(PoolRooms.MOD_ID, "textures/environment/light_skybox_back.png");
    public static Identifier LIGHT_SKYBOX_WEST_TEXTURE = Identifier.of(PoolRooms.MOD_ID, "textures/environment/light_skybox_left.png");
    public static Identifier LIGHT_SKYBOX_TOP_TEXTURE = Identifier.of(PoolRooms.MOD_ID, "textures/environment/light_skybox_top.png");
    public static Identifier LIGHT_SKYBOX_BOTTOM_TEXTURE = Identifier.of(PoolRooms.MOD_ID, "textures/environment/light_skybox_bottom.png");

    public static RenderLayer LIGHT_SKYBOX_BLOCK_RENDER_LAYER; // This is set in PoolRoomsClient

    public LightSkyboxBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {

    }

    @Override
    public void render(LightSkyboxBlockEntity lightSkyboxBlockEntity, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j) {
        MinecraftClient client = MinecraftClient.getInstance();
        float yaw = client.player.getYaw(tickDelta);
        float pitch = client.player.getPitch(tickDelta);

        // Build a rotation matrix
        Matrix4f rotMat = new Matrix4f()
                .rotate((float)Math.toRadians(pitch), 1, 0, 0)
                .rotate((float)Math.toRadians(yaw), 0, 1, 0);

        assert LIGHT_SKYBOX_SHADER != null;
        Uniform rotUniform = LIGHT_SKYBOX_SHADER.getUniform("RotMat");

        if (rotUniform != null) {
            rotUniform.set(
                    rotMat.m00(), rotMat.m01(), rotMat.m02(), rotMat.m03(),
                    rotMat.m10(), rotMat.m11(), rotMat.m12(), rotMat.m13(),
                    rotMat.m20(), rotMat.m21(), rotMat.m22(), rotMat.m23(),
                    rotMat.m30(), rotMat.m31(), rotMat.m32(), rotMat.m33()
            );
        }

        Matrix4f matrix4f = matrixStack.peek().getPositionMatrix();
        VertexConsumer consumer = vertexConsumerProvider.getBuffer(getLayer());
        renderSides(lightSkyboxBlockEntity, matrix4f, consumer, tickDelta);
    }

    private void renderSides(LightSkyboxBlockEntity entity, Matrix4f matrix, VertexConsumer vertexConsumer, float tickDelta) {
        this.renderSide(entity, matrix, vertexConsumer, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, Direction.SOUTH, tickDelta);
        this.renderSide(entity, matrix, vertexConsumer, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, Direction.NORTH, tickDelta);
        this.renderSide(entity, matrix, vertexConsumer, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.EAST, tickDelta);
        this.renderSide(entity, matrix, vertexConsumer, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.WEST, tickDelta);
        this.renderSide(entity, matrix, vertexConsumer, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, Direction.DOWN, tickDelta);
        this.renderSide(entity, matrix, vertexConsumer, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, Direction.UP, tickDelta);
    }

    private void renderSide(
            LightSkyboxBlockEntity entity, Matrix4f model, VertexConsumer vertices, float x1, float x2, float y1, float y2, float z1, float z2, float z3, float z4, Direction side
    , float tickDelta) {
//        MinecraftClient client = MinecraftClient.getInstance();
//        PlayerEntity player = client.player;

//        float uShift = -player.getYaw(tickDelta) / 360.0f;
//        float vShift = -player.getPitch(tickDelta) / 180.0f;

        vertices.vertex(model, x1, y1, z1).texture(0.0f, 1.0f);
        vertices.vertex(model, x2, y1, z2).texture(1.0f, 1.0f);
        vertices.vertex(model, x2, y2, z3).texture(1.0f, 0.0f);
        vertices.vertex(model, x1, y2, z4).texture(0.0f, 0.0f);
    }

    protected RenderLayer getLayer() {
        return LIGHT_SKYBOX_BLOCK_RENDER_LAYER;
    }
}
