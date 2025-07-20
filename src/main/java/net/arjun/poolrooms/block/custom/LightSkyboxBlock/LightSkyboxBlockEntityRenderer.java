package net.arjun.poolrooms.block.custom.LightSkyboxBlock;

import net.arjun.poolrooms.PoolRooms;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.Camera; // Import the Camera class
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis; // Import RotationAxis for more flexible rotations
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;

@Environment(EnvType.CLIENT)
public class LightSkyboxBlockEntityRenderer implements BlockEntityRenderer<LightSkyboxBlockEntity> {

    // Define Identifiers for your six textures
    private static final Identifier TEXTURE_DOWN = Identifier.of(PoolRooms.MOD_ID, "textures/block/light_skybox_bottom.png");
    private static final Identifier TEXTURE_UP = Identifier.of(PoolRooms.MOD_ID, "textures/block/light_skybox_top.png");
    private static final Identifier TEXTURE_NORTH = Identifier.of(PoolRooms.MOD_ID, "textures/block/light_skybox_front.png");
    private static final Identifier TEXTURE_EAST = Identifier.of(PoolRooms.MOD_ID, "textures/block/light_skybox_right.png");
    private static final Identifier TEXTURE_SOUTH = Identifier.of(PoolRooms.MOD_ID, "textures/block/light_skybox_back.png");
    private static final Identifier TEXTURE_WEST = Identifier.of(PoolRooms.MOD_ID, "textures/block/light_skybox_left.png");

    public LightSkyboxBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        // Constructor, can be used to get ItemRenderer or TextRenderer if needed
    }

    @Override
    public void render(LightSkyboxBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push(); // Save the current matrix state

        MinecraftClient client = MinecraftClient.getInstance();
        Camera camera = client.gameRenderer.getCamera();

        // Get camera pitch and yaw
        float cameraYaw = camera.getYaw();
        float cameraPitch = camera.getPitch();

        // **Dynamic Texture Movement Logic:**
        // This is the core part. The simplest "moves with camera" effect is
        // often achieved by rotating the UVs or the face itself based on camera angles.
        // For a more advanced effect like Dr.4t's, you might need to use more complex 3D math
        // to project the camera's view onto the block's faces or manipulate UVs in a non-linear way.

        // Example: Rotate the entire block slightly to react to camera yaw (just for demonstration)
        // This makes the block itself rotate, making the texture appear to shift perspective
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(cameraYaw)); // Example rotation
        // You could also rotate based on cameraPitch here

        // Get the VertexConsumer for the desired rendering layer
        // Use RenderLayer.getTranslucent() if your textures use blended transparency (e.g. fades)
        // Use RenderLayer.getCutout() if your textures are either fully opaque or fully transparent (e.g. leaves or fences)
        VertexConsumer consumer = vertexConsumers.getBuffer(RenderLayer.getTranslucent());

        // --- Render each face with its respective texture ---
        // This is a simplified cube rendering. For accurate rendering, especially with transparency,
        // consider the order you draw faces (back-to-front from camera perspective) and culling.

        // Example for NORTH face (Z-)
        renderFace(matrices, consumer, light, overlay, Direction.NORTH, TEXTURE_NORTH,
                cameraYaw, cameraPitch);

        // Example for SOUTH face (Z+)
        renderFace(matrices, consumer, light, overlay, Direction.SOUTH, TEXTURE_SOUTH,
                cameraYaw, cameraPitch);

        renderFace(matrices, consumer, light, overlay, Direction.EAST, TEXTURE_EAST,
                cameraYaw, cameraPitch);

        renderFace(matrices, consumer, light, overlay, Direction.WEST, TEXTURE_WEST,
                cameraYaw, cameraPitch);

        renderFace(matrices, consumer, light, overlay, Direction.UP, TEXTURE_UP,
                cameraYaw, cameraPitch);

        renderFace(matrices, consumer, light, overlay, Direction.DOWN, TEXTURE_DOWN,
                cameraYaw, cameraPitch);

        // ... repeat for EAST, WEST, UP, DOWN faces

        matrices.pop(); // Restore the previous matrix state
    }

    private void renderFace(MatrixStack matrices, VertexConsumer consumer, int light, int overlay,
                            Direction faceDirection, Identifier textureIdentifier,
                            float cameraYaw, float cameraPitch) {

        MinecraftClient.getInstance().getTextureManager().bindTexture(textureIdentifier);

        // Define vertex coordinates for a standard cube face
        float x1 = 0, y1 = 0, z1 = 0; // Bottom-left-front corner
        float x2 = 1, y2 = 1, z2 = 1; // Top-right-back corner

        // Define base UV coordinates (0,0 to 1,1 for the full texture)
        float u1 = 0, v1 = 0;
        float u2 = 1, v2 = 1;

        // --- UV manipulation based on camera ---
        // This is where the magic happens. You'll need to calculate how much to shift
        // or warp u1, v1, u2, v2 based on cameraYaw and cameraPitch
        // For a simple shift:
        // float uvShiftX = (cameraYaw % 360) / 360f; // Scale yaw to a 0-1 range
        // float uvShiftY = (cameraPitch % 360) / 360f; // Scale pitch to a 0-1 range
        // u1 += uvShiftX; u2 += uvShiftX;
        // v1 += uvShiftY; v2 += uvShiftY;
        // You'll need more complex math for realistic "moving with camera" effect.

        // Build the face vertices
        switch (faceDirection) {
            case NORTH: // Z-
                addVertex(matrices, consumer, x1, y2, z1, u1, v1, light, overlay, faceDirection); // Top-left
                addVertex(matrices, consumer, x1, y1, z1, u1, v2, light, overlay, faceDirection); // Bottom-left
                addVertex(matrices, consumer, x2, y1, z1, u2, v2, light, overlay, faceDirection); // Bottom-right
                addVertex(matrices, consumer, x2, y2, z1, u2, v1, light, overlay, faceDirection); // Top-right
                break;
            case SOUTH: // Z+
                addVertex(matrices, consumer, x2, y2, z2, u1, v1, light, overlay, faceDirection);
                addVertex(matrices, consumer, x2, y1, z2, u1, v2, light, overlay, faceDirection);
                addVertex(matrices, consumer, x1, y1, z2, u2, v2, light, overlay, faceDirection);
                addVertex(matrices, consumer, x1, y2, z2, u2, v1, light, overlay, faceDirection);
                break;
            // ... Implement rendering for EAST, WEST, UP, DOWN faces similarly
            case EAST: // X+
                addVertex(matrices, consumer, x2, y2, z1, u1, v1, light, overlay, faceDirection);
                addVertex(matrices, consumer, x2, y1, z1, u1, v2, light, overlay, faceDirection);
                addVertex(matrices, consumer, x2, y1, z2, u2, v2, light, overlay, faceDirection);
                addVertex(matrices, consumer, x2, y2, z2, u2, v1, light, overlay, faceDirection);
                break;
            case WEST: // X-
                addVertex(matrices, consumer, x1, y2, z2, u1, v1, light, overlay, faceDirection);
                addVertex(matrices, consumer, x1, y1, z2, u1, v2, light, overlay, faceDirection);
                addVertex(matrices, consumer, x1, y1, z1, u2, v2, light, overlay, faceDirection);
                addVertex(matrices, consumer, x1, y2, z1, u2, v1, light, overlay, faceDirection);
                break;
            case UP: // Y+
                addVertex(matrices, consumer, x1, y2, z2, u1, v1, light, overlay, faceDirection);
                addVertex(matrices, consumer, x1, y2, z1, u1, v2, light, overlay, faceDirection);
                addVertex(matrices, consumer, x2, y2, z1, u2, v2, light, overlay, faceDirection);
                addVertex(matrices, consumer, x2, y2, z2, u2, v1, light, overlay, faceDirection);
                break;
            case DOWN: // Y-
                addVertex(matrices, consumer, x1, y1, z1, u1, v1, light, overlay, faceDirection);
                addVertex(matrices, consumer, x1, y1, z2, u1, v2, light, overlay, faceDirection);
                addVertex(matrices, consumer, x2, y1, z2, u2, v2, light, overlay, faceDirection);
                addVertex(matrices, consumer, x2, y1, z1, u2, v1, light, overlay, faceDirection);
                break;
        }
    }

    private void addVertex(MatrixStack matrices, VertexConsumer consumer,
                           float x, float y, float z, float u, float v,
                           int light, int overlay, Direction normalDirection) {

        // Get the current MatrixStack.Entry
        MatrixStack.Entry entry = matrices.peek();

        // Get the Matrix4f for transforming positions
        Matrix4f positionMatrix = entry.getPositionMatrix();

        // Get the Matrix3f for transforming normals
        Matrix3f normalMatrix = entry.getNormalMatrix();

        // Define the original normal vector components for the face
        float originalNormalX = normalDirection.getOffsetX();
        float originalNormalY = normalDirection.getOffsetY();
        float originalNormalZ = normalDirection.getOffsetZ();

        // Create a temporary vector for the original normal
        Vector3f normal = new Vector3f(originalNormalX, originalNormalY, originalNormalZ);

        // Transform the normal vector using the normal matrix
        // This ensures the normal is correctly oriented after any rotations/scaling applied to the block
        normal.mul(normalMatrix);

        consumer.vertex(positionMatrix, x, y, z)
                .color(1.0f, 1.0f, 1.0f, 1.0f) // White color, full opacity
                .texture(u, v)
                .overlay(overlay)
                .light(light)
                // Use the transformed normal vector components
                .normal(normal.x(), normal.y(), normal.z());
    }
}