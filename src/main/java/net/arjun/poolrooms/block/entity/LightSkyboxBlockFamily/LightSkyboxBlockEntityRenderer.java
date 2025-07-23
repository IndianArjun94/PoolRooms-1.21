package net.arjun.poolrooms.block.entity.LightSkyboxBlockFamily;

import net.arjun.poolrooms.PoolRooms;
import net.arjun.poolrooms.PoolRoomsClient; // Import your client class to access the shader

import net.minecraft.client.MinecraftClient; // To get the player instance
import net.minecraft.client.gl.ShaderProgram; // For the shader program type
import net.minecraft.client.gl.Uniform; // For the Uniform type
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
    PlayerEntity player;

    public static RenderLayer LIGHT_SKYBOX_BLOCK_RENDER_LAYER; // This is set in PoolRoomsClient

    public LightSkyboxBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        // Constructor logic if needed
    }

    @Override
    public void render(LightSkyboxBlockEntity lightSkyboxBlockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j) {
        Matrix4f matrix4f = matrixStack.peek().getPositionMatrix();

        // IMPORTANT: Obtain the VertexConsumer *first*.
        // This action (vertexConsumerProvider.getBuffer(this.getLayer())) implicitly
        // activates the shader program associated with LIGHT_SKYBOX_BLOCK_RENDER_LAYER.
        VertexConsumer consumer = vertexConsumerProvider.getBuffer(this.getLayer());

        // --- START: Add PlayerYaw Uniform Setting ---

        // Get the custom shader program instance from your client initializer.
        // This should be the same shader program that was just activated by getBuffer().
        ShaderProgram shaderProgram = PoolRoomsClient.getLightSkyboxShader();

        if (shaderProgram != null) {
            player = mc.player; // Get the player entity

            if (player != null) {
                // Get the player's yaw (horizontal rotation) in degrees.
                float playerYawDegrees = player.getYaw();

                // Convert degrees to radians, as your GLSL shader expects radians for `PlayerYaw`.
                float playerYawRadians = (float) Math.toRadians(playerYawDegrees);

                // Find the "PlayerYaw" uniform within your shader program.
                Uniform playerYawUniform = shaderProgram.getUniform("PlayerYaw");

                // If the uniform is found, set its value.
                // This must happen *after* the shader is active.
                if (playerYawUniform != null) {
                    playerYawUniform.set(playerYawRadians);
                    System.out.println("Yaw: " + playerYawDegrees);
                    System.out.println("Yaw Uniform: " + shaderProgram.getUniform("PlayerYaw").getFloatData().get());
                } else {
                    // Log a warning if the uniform isn't found.
                    System.err.println("Warning: PlayerYaw uniform not found in rendertype_light_skybox_block shader.");
                }
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

    private void renderSide(
            LightSkyboxBlockEntity entity, Matrix4f model, VertexConsumer vertices, float x1, float x2, float y1, float y2, float z1, float z2, float z3, float z4, Direction side
    ) {
//        if (entity.shouldDrawSide(side)) {
            vertices.vertex(model, x1, y1, z1);
            vertices.vertex(model, x2, y1, z2);
            vertices.vertex(model, x2, y2, z3);
            vertices.vertex(model, x1, y2, z4);
//        }
    }

    protected float getTopYOffset() {
        return 1F;
    }

    protected float getBottomYOffset() {
        return 0F;
    }

    protected RenderLayer getLayer() {
        return LIGHT_SKYBOX_BLOCK_RENDER_LAYER;
    }
}
