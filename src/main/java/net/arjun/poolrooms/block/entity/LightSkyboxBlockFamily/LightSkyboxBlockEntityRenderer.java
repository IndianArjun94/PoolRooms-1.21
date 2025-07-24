package net.arjun.poolrooms.block.entity.LightSkyboxBlockFamily;

import net.arjun.poolrooms.PoolRooms;
import net.arjun.poolrooms.PoolRoomsClient; // Import your client class to access the shader

import net.minecraft.client.MinecraftClient; // To get the player instance
import net.minecraft.client.gl.ShaderProgram; // For the shader program type
import net.minecraft.client.gl.Uniform; // For the Uniform type
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity; // To get player yaw
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

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


    MinecraftClient mc = MinecraftClient.getInstance();

    public static RenderLayer LIGHT_SKYBOX_BLOCK_RENDER_LAYER; // This is set in PoolRoomsClient

    public LightSkyboxBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        // Constructor logic if needed
    }

    @Override
    public void render(LightSkyboxBlockEntity lightSkyboxBlockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j) {
        Matrix4f matrix4f = matrixStack.peek().getPositionMatrix();

        VertexConsumer consumer = vertexConsumerProvider.getBuffer(this.getLayer());

        ShaderProgram shaderProgram = PoolRoomsClient.getLightSkyboxShader();

        if (shaderProgram != null) {
            PlayerEntity player = MinecraftClient.getInstance().player;

            if (player != null) {
                Vec3d playerPos = player.getPos();
                shaderProgram.getUniform("playerPos").set((float)playerPos.getX(), (float)playerPos.getY(), (float)playerPos.getZ());
                Vec3d blockPos = lightSkyboxBlockEntity.getPos().toCenterPos();
                shaderProgram.getUniform("blockPos").set((float)blockPos.getX(), (float)blockPos.getY(), (float)blockPos.getZ());
                // If the uniform is found, set its value.
                // This must happen *after* the shader is active.
            }
        }

        // --- END: Add PlayerYaw Uniform Setting ---

        // Now, proceed with rendering, using the consumer that has the shader active
        // and its uniform correctly set.
        this.renderSides(lightSkyboxBlockEntity, matrix4f, consumer);
    }

    private void renderSides(LightSkyboxBlockEntity entity, Matrix4f matrix, VertexConsumer vertexConsumer) {
        float f = this.getBottomYOffset();
        float g = this.getTopYOffset();
        this.renderSide(entity, matrix, vertexConsumer, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, Direction.SOUTH);
        this.renderSide(entity, matrix, vertexConsumer, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, Direction.NORTH);
        this.renderSide(entity, matrix, vertexConsumer, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.EAST);
        this.renderSide(entity, matrix, vertexConsumer, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.WEST);
        this.renderSide(entity, matrix, vertexConsumer, 0.0F, 1.0F, f, f, 0.0F, 0.0F, 1.0F, 1.0F, Direction.DOWN);
        this.renderSide(entity, matrix, vertexConsumer, 0.0F, 1.0F, g, g, 1.0F, 1.0F, 0.0F, 0.0F, Direction.UP);
    }



    protected float getTopYOffset() {
        return 1F;
    }private void renderSide(
            LightSkyboxBlockEntity entity, Matrix4f model, VertexConsumer vertices, float x1, float x2, float y1, float y2, float z1, float z2, float z3, float z4, Direction side
    ) {
        vertices.vertex(model, x1, y1, z1).texture(0.0f, 1.0f); // Bottom-left
        vertices.vertex(model, x2, y1, z2).texture(1.0f, 1.0f); // Bottom-right
        vertices.vertex(model, x2, y2, z3).texture(1.0f, 0.0f); // Top-right
        vertices.vertex(model, x1, y2, z4).texture(0.0f, 0.0f); // Top-left
    }

    protected float getBottomYOffset() {
        return 0F;
    }

    protected RenderLayer getLayer() {
        return LIGHT_SKYBOX_BLOCK_RENDER_LAYER;
    }
}
