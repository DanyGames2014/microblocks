package net.danygames2014.microblocks.recipe;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.danygames2014.microblocks.Microblocks;
import net.danygames2014.microblocks.item.MicroblockItemType;
import net.danygames2014.microblocks.item.base.MicroblockItem;
import net.danygames2014.microblocks.microblock.MicroblockRegistry;
import net.minecraft.block.Block;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

public class MicroblockRecipeManager {
    Object2ObjectOpenHashMap<Block, ObjectArrayList<MicroblockRecipe>> recipes = new Object2ObjectOpenHashMap<>();
    public ObjectArrayList<MicroblockRecipe> patterns = new ObjectArrayList<>();
    
    public MicroblockRecipeManager() {
        
    }

    public ItemStack craft(CraftingInventory inv) {
        for (var pattern : patterns) {
            // Check if a pattern matches
            if (pattern.matchesIgnoreType(inv)) {
                // Determine the block
                Block block = null;
                
                for (int i = 0; i < inv.size(); i++) {
                    ItemStack stack = inv.getStack(i);
                    
                    if (stack == null || stack.getItem() == null) {
                        continue;
                    }
                    
                    if (stack.getItem() instanceof BlockItem blockItem) {
                        block = blockItem.getBlock();
                    }
                    
                    if (stack.getItem() instanceof MicroblockItem microblockItem) {
                        block = microblockItem.block;
                    }
                }
                
                if (block == null) {
                    return null;
                }
                
                if (recipes.containsKey(block)) {
                    for (var recipe : recipes.get(block)) {
                        if (recipe.matches(inv)) {
                            return recipe.craft(inv);
                        }
                    }
                }
            }
        }

        return null;
    }
    
    public void addRecipe(MicroblockRecipeIngredient outputType, int outputAmount, Object... pattern) {
        String var3 = "";
        int i = 0;
        int width = 0;
        int height = 0;
        
        // Resolve Pattern
        if (pattern[i] instanceof String[]) {
            String[] lines = (String[])pattern[i++];

            for (String var9 : lines) {
                height++;
                width = var9.length();
                var3 = var3 + var9;
            }
        } else {
            while(i < pattern.length && pattern[i] instanceof String) {
                String var7 = (String)pattern[i++];
                height++;
                width = var7.length();
                var3 = var3 + var7;
            }
        }

        // Resolve Ingredients
        HashMap<Character, MicroblockRecipeIngredient> ingredientMap = new HashMap<>();
        ingredientMap.put('S', MicroblockRecipeIngredient.SAW);
        ingredientMap.put('B', MicroblockRecipeIngredient.BLOCK);
        
        for(; i < pattern.length; i += 2) {
            Character ingredientKey = (Character)pattern[i];
            
            MicroblockRecipeIngredient ingredient = null;
            if (pattern[i + 1] instanceof MicroblockRecipeIngredient recipeInput) {
                ingredient = recipeInput;
            } else if(pattern[i + 1] instanceof MicroblockItemType itemType) {
                ingredient = MicroblockRecipeIngredient.fromItemType(itemType);
            }

            ingredientMap.put(ingredientKey, ingredient);
        }

        // Resolve Input
        MicroblockRecipeIngredient[] input = new MicroblockRecipeIngredient[width * height];

        for(int index = 0; index < width * height; ++index) {
            char ingredientKey = var3.charAt(index);
            input[index] = ingredientMap.getOrDefault(ingredientKey, null);
        }

        for (var typeItems : Microblocks.microblockItems.entrySet()) {
            for (Block block : typeItems.getValue().keySet()) {
                if (!recipes.containsKey(block)) {
                    recipes.put(block, new ObjectArrayList<>());
                }
                
                for (var meta : MicroblockRegistry.getInstance().registry.get(block)) {
                    this.recipes.get(block).add(new MicroblockRecipe(width, height, input, outputType, outputAmount, block, meta));
                }
            }
        }
        
        patterns.add(new MicroblockRecipe(width, height, input, outputType, outputAmount, null, 0));
    }
}
