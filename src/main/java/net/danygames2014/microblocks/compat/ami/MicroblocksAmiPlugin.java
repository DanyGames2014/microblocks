package net.danygames2014.microblocks.compat.ami;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.danygames2014.microblocks.Microblocks;
import net.danygames2014.microblocks.item.base.MicroblockItem;
import net.danygames2014.microblocks.recipe.MicroblockRecipeManager;
import net.glasslauncher.mods.alwaysmoreitems.api.*;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.modificationstation.stationapi.api.util.Identifier;

public class MicroblocksAmiPlugin implements ModPluginProvider {
    public MicroblocksAmiPlugin() {
        super();
    }

    @Override
    public String getName() {
        return "Microblocks";
    }

    @Override
    public Identifier getId() {
        return Microblocks.NAMESPACE.id("microblocks");
    }

    @Override
    public void onAMIHelpersAvailable(AMIHelpers amiHelpers) {
        
    }

    @Override
    public void onItemRegistryAvailable(ItemRegistry itemRegistry) {

    }

    @Override
    public void register(ModRegistry registry) {
        registry.addRecipeCategories(new MicroblockRecipeCategory());
        registry.addRecipeHandlers(new MicroblockRecipeHandler());
        registry.addRecipes(Microblocks.recipeManager.patterns);
    }

    @Override
    public void onRecipeRegistryAvailable(RecipeRegistry recipeRegistry) {

    }

    @Override
    public SyncableRecipe deserializeRecipe(NbtCompound recipe) {
        return null;
    }

    @Override
    public void updateBlacklist(AMIHelpers amiHelpers) {
        for (var microblockItemsOfType : Microblocks.microblockItems.values()) {
            for (var microblockItems : microblockItemsOfType.values()) {
                for (Int2ObjectMap.Entry<MicroblockItem> microblockItem : microblockItems.int2ObjectEntrySet()) {
                    if (microblockItem.getValue().block == Block.STONE) {
                        continue;
                    }
                    
                    amiHelpers.getItemBlacklist().addItemToBlacklist(new ItemStack(microblockItem.getValue(), 1, microblockItem.getIntKey()));
                }
            }
        }
    }
}
