package net.arjun.poolrooms.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {

//    public static final TagKey<Item> ITEMS = TagKey.of(RegistryKeys.ITEM, Identifier.of(PoolRooms.MOD_ID, "sigma_items"));

    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
//        Example of Item Tag (a group of items that can be accessed/used interchangeably by calling the item tag they are referenced to)

//        getOrCreateTagBuilder(ITEMS)
//                .add(ModItems.POOL_SHARD)
//                .add(ModItems.POOL_SPEAR)
//                .add(ModItems.POOL_RATIONS)
//                .forceAddTag(ItemTags.ACACIA_LOGS)
//                .setReplace(true);
    }
}
