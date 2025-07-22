package net.arjun.poolrooms.block.entity;

import net.arjun.poolrooms.PoolRooms;
import net.arjun.poolrooms.block.ModBlocks;
import net.arjun.poolrooms.block.entity.LightSkyboxBlockFamily.LightSkyboxBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {

    public static final BlockEntityType<LightSkyboxBlockEntity> LIGHT_SKYBOX_BLOCK_ENTITY = register("light_skybox_block_entity",
            LightSkyboxBlockEntity::new, ModBlocks.LIGHT_SKYBOX_BLOCK);

    private static <T extends BlockEntity> BlockEntityType<T> register(
            String name,
            BlockEntityType.BlockEntityFactory<? extends T> entityFactory,
            Block... blocks) {
//        Very long params above...

        Identifier id = Identifier.of(PoolRooms.MOD_ID, name);
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, id, BlockEntityType.Builder.<T>create(entityFactory, blocks).build());
    }

    public static void registerBlockEntities() {
        PoolRooms.LOGGER.info("Registering Pool Rooms Block Entities");
    }
}
