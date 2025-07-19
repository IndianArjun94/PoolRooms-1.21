package net.arjun.poolrooms.block;

import net.arjun.poolrooms.PoolRooms;
import net.arjun.poolrooms.block.custom.LightSkyboxBlock.LightSkyboxBlock;
import net.arjun.poolrooms.block.custom.LightSkyboxBlock.LightSkyboxBlockEntity;
import net.arjun.poolrooms.block.custom.LightSkyboxBlock.LightSkyboxBlockEntityRenderer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import static net.minecraft.network.packet.CustomPayload.id;

public class ModBlocks {
    public static BlockEntityType<LightSkyboxBlockEntity> SKYBOX_BLOCK_ENTITY_TYPE;

    public static final Block POOL_TILES = registerBlock("pool_tiles",
            new Block(AbstractBlock.Settings.create().strength(2f).sounds(BlockSoundGroup.STONE)));


    public static final Block LIGHT_SKYBOX = registerBlock("light_skybox", new LightSkyboxBlock());

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(PoolRooms.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(PoolRooms.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlocks() {
        PoolRooms.LOGGER.info("Registering Pool Rooms Blocks");

        SKYBOX_BLOCK_ENTITY_TYPE = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                id("skybox_block_entity").id(),
                FabricBlockEntityTypeBuilder.create(LightSkyboxBlockEntity::new, LIGHT_SKYBOX).build()
        );

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
            entries.add(POOL_TILES);
            entries.add(LIGHT_SKYBOX);
        });
    }
}
