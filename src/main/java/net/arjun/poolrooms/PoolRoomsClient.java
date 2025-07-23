package net.arjun.poolrooms;

import net.arjun.poolrooms.block.entity.LightSkyboxBlockFamily.LightSkyboxBlockEntityRenderer;
import net.arjun.poolrooms.block.entity.ModBlockEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.block.entity.EndPortalBlockEntityRenderer;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import net.fabricmc.fabric.api.client.rendering.v1.CoreShaderRegistrationCallback;
import net.minecraft.util.Identifier;
import net.minecraft.client.gl.ShaderProgram; // For the type

import java.io.IOException;

import static net.arjun.poolrooms.block.entity.LightSkyboxBlockFamily.LightSkyboxBlockEntityRenderer.*;
import static net.minecraft.client.render.block.entity.EndPortalBlockEntityRenderer.PORTAL_TEXTURE;
import static net.minecraft.client.render.block.entity.EndPortalBlockEntityRenderer.SKY_TEXTURE;

public class PoolRoomsClient implements ClientModInitializer {

    @Nullable
    public static RenderPhase.ShaderProgram LIGHT_SKYBOX_PROGRAM; // The usable version
    @Nullable
    public static ShaderProgram LIGHT_SKYBOX_SHADER; // The raw version

    public static ShaderProgram getLightSkyboxShader() {
        return LIGHT_SKYBOX_SHADER;
    }

    @Override
    public void onInitializeClient() {
        BlockEntityRendererFactories.register(ModBlockEntities.LIGHT_SKYBOX_BLOCK_ENTITY, LightSkyboxBlockEntityRenderer::new);

        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
            private static final Identifier ID = Identifier.of(PoolRooms.MOD_ID, "light_skybox_block_shader_reloader");

            @Override
            public Identifier getFabricId() {
                return ID;
            }

            @Override
            public void reload(ResourceManager manager) {
                try {
                    // Load the shader program
                    LIGHT_SKYBOX_SHADER = new ShaderProgram(
                            manager,
                            "rendertype_light_skybox_block", // This matches your JSON file name
                            VertexFormats.POSITION_TEXTURE // Define the vertex format your shader expects
                    );

                    LIGHT_SKYBOX_PROGRAM = new RenderPhase.ShaderProgram(PoolRoomsClient::getLightSkyboxShader);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to load light skybox block shader", e);
                }
                LightSkyboxBlockEntityRenderer.LIGHT_SKYBOX_BLOCK_RENDER_LAYER = RenderLayer.of(
                        "light_skybox_block_render_layer",
                        VertexFormats.POSITION_TEXTURE,
                        VertexFormat.DrawMode.QUADS,
                        1536,
                        false,
                        false,
                        RenderLayer.MultiPhaseParameters.builder()
                                .program(PoolRoomsClient.LIGHT_SKYBOX_PROGRAM)
                                .texture(
                                        RenderPhase.Textures.create()
                                                .add(LightSkyboxBlockEntityRenderer.LIGHT_SKYBOX_NORTH_TEXTURE, false, false)
//                                                .add(LightSkyboxBlockEntityRenderer.LIGHT_SKYBOX_EAST_TEXTURE, false, false)
//                                                .add(LightSkyboxBlockEntityRenderer.LIGHT_SKYBOX_WEST_TEXTURE, false, false)
//                                                .add(LightSkyboxBlockEntityRenderer.LIGHT_SKYBOX_TOP_TEXTURE, false, false)
//                                                .add(LightSkyboxBlockEntityRenderer.LIGHT_SKYBOX_BOTTOM_TEXTURE, false, false)
                                                .build())
                                .build(false)
                );

            }
        });
    }
}
