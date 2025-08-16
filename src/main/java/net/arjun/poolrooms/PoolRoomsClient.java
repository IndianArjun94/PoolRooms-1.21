package net.arjun.poolrooms;

import net.arjun.poolrooms.block.entity.SkyboxBlockFamily.SkyboxBlockEntityRenderer;
import net.arjun.poolrooms.block.entity.ModBlockEntities;
import net.arjun.poolrooms.commmand.ModCommands;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

import static net.arjun.poolrooms.block.entity.SkyboxBlockFamily.SkyboxBlockEntityRenderer.*;

public class PoolRoomsClient implements ClientModInitializer {

    @Nullable
    public static RenderPhase.ShaderProgram SKYBOX_PROGRAM; // The usable version
    @Nullable
    public static ShaderProgram SKYBOX_SHADER; // The raw version

    public static ShaderProgram getSkyboxShader() {
        return SKYBOX_SHADER;
    }

    @Override
    public void onInitializeClient() {
        BlockEntityRendererFactories.register(ModBlockEntities.SKYBOX_BLOCK_ENTITY, SkyboxBlockEntityRenderer::new);

        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
            private static final Identifier ID = Identifier.of(PoolRooms.MOD_ID, "skybox_block_shader_reloader");

            @Override
            public Identifier getFabricId() {
                return ID;
            }

            @Override
            public void reload(ResourceManager manager) {
                try {
                    // Load the shader program
                    SKYBOX_SHADER = new ShaderProgram(
                            manager,
                            "rendertype_skybox_block", // This matches your JSON file name
                            VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL // Define the vertex format your shader expects
                    );

                    SKYBOX_PROGRAM = new RenderPhase.ShaderProgram(PoolRoomsClient::getSkyboxShader);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to load skybox block shader", e);
                }
                SkyboxBlockEntityRenderer.SKYBOX_BLOCK_RENDER_LAYER = RenderLayer.of(
                        "skybox_block_render_layer",
                        VertexFormats.POSITION_TEXTURE,
                        VertexFormat.DrawMode.QUADS,
                        1536,
                        false,
                        false,
                        RenderLayer.MultiPhaseParameters.builder()
                                .program(PoolRoomsClient.SKYBOX_PROGRAM)
                                .texture(
                                        RenderPhase.Textures.create()
                                                .add(SKYBOX_TOP_TEXTURE, false, false)
                                                .add(SKYBOX_NORTH_TEXTURE, false, false)
                                                .add(SKYBOX_EAST_TEXTURE, false, false)
                                                .add(SKYBOX_SOUTH_TEXTURE, false, false)
                                                .add(SKYBOX_WEST_TEXTURE, false, false)
                                                .add(SKYBOX_BOTTOM_TEXTURE, false, false)
                                                .build())
                                .build(false)
                );

            }
        });

        ModCommands.registerCommands();

    }
}
