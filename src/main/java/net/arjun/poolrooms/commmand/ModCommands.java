package net.arjun.poolrooms.commmand;

import net.arjun.poolrooms.block.entity.SkyboxBlockFamily.SkyboxBlockEntityRenderer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;

public class ModCommands {

    public static void registerCommands() {
        ClientCommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("setskybox")
                    .then(ClientCommandManager.literal("day")
                    .executes(SkyboxBlockEntityRenderer::switchToDayTexture)));
        }));

        ClientCommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("setskybox")
                    .then(ClientCommandManager.literal("night")
                    .executes(SkyboxBlockEntityRenderer::switchToNightTexture)));
        }));

        ClientCommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("switchskybox")
                    .executes(SkyboxBlockEntityRenderer::switchSkyboxTextures));
        }));
    }
}
