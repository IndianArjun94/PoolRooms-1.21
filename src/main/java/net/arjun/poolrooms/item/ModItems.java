package net.arjun.poolrooms.item;

import net.arjun.poolrooms.PoolRooms;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item POOL_SHARD = registerItem("pool_shard", new Item(new Item.Settings()));

    public static final Item POOL_SPEAR = registerItem("pool_spear", new Item(new Item.Settings()));

    public static final Item POOL_RATIONS = registerItem("pool_rations", new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(3).saturationModifier(4).build())));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(PoolRooms.MOD_ID, name), item);
    }

    public static void registerModItems() {
        PoolRooms.LOGGER.info("Registering Pool Room Items");

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(POOL_SHARD);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
            entries.add(POOL_SPEAR);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> {
            entries.add(POOL_RATIONS);
        });
    }
}
