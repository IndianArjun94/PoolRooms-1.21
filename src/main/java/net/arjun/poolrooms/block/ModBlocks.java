package net.arjun.poolrooms.block;

import net.arjun.poolrooms.PoolRooms;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
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

public class ModBlocks {

    public static final Block POOL_TILES = registerBlock("pool_tiles",
            new Block(AbstractBlock.Settings.create().strength(2f).sounds(BlockSoundGroup.STONE)));


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
