package net.arjun.poolrooms.block;

import net.arjun.poolrooms.PoolRooms;
import net.arjun.poolrooms.block.entity.LightSkyboxBlockFamily.LightSkyboxBlock;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.*;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final Block POOL_TILES = registerBlock("pool_tiles",
            new Block(AbstractBlock.Settings.create().strength(2f).sounds(BlockSoundGroup.STONE)));

    public static final Block POOL_TILE_STAIRS = registerBlock("pool_tile_stairs",
            new StairsBlock(ModBlocks.POOL_TILES.getDefaultState(), AbstractBlock.Settings.create().strength(2f).sounds(BlockSoundGroup.STONE)));

    public static final Block POOL_TILE_SLAB = registerBlock("pool_tile_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(2f).sounds(BlockSoundGroup.STONE)));

    public static final BlockFamily POOL_TILES_FAMILY =
            new BlockFamily.Builder(ModBlocks.POOL_TILES)
                    .slab(ModBlocks.POOL_TILE_SLAB)
                    .stairs(ModBlocks.POOL_TILE_STAIRS)
                    .build();

//    Block Entity -----

    public static final Block LIGHT_SKYBOX_BLOCK = registerBlock("light_skybox_block",
            new LightSkyboxBlock(AbstractBlock.Settings.copy(Blocks.GLOWSTONE).strength(2f).sounds(BlockSoundGroup.GLASS)));

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

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
            entries.add(POOL_TILES);
        });
    }
}
