package net.danygames2014.microblocks.recipe;

import net.danygames2014.microblocks.Microblocks;
import net.danygames2014.microblocks.item.base.MicroblockItem;
import net.minecraft.block.Block;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CraftingRecipe;

public class MicroblockRecipe implements CraftingRecipe {
    private final int width;
    private final int height;
    private final MicroblockRecipeIngredient[] input;
    private ItemStack output;
    private MicroblockRecipeIngredient outputType;
    private final Block block;
    private final int meta;

    public MicroblockRecipe(int width, int height, MicroblockRecipeIngredient[] input, MicroblockRecipeIngredient outputType, int outputAmount, Block block, int meta) {
        this.width = width;
        this.height = height;
        this.input = input;
        this.outputType = outputType;
        this.block = block;
        this.meta = meta;
        
        if (block != null) {
            if (outputType == MicroblockRecipeIngredient.BLOCK) {
                this.output = new ItemStack(block, outputAmount, meta);
            } else {
                this.output = new ItemStack(Microblocks.microblockItems.get(MicroblockRecipeIngredient.toItemType(outputType)).get(block).get(meta), outputAmount);
            }
        }
    }

    @Override
    public ItemStack getOutput() {
        return this.output;
    }

    public MicroblockRecipeIngredient getOutputType() {
        return outputType;
    }

    @Override
    public boolean matches(CraftingInventory craftingInventory) {
        for(int xOffset = 0; xOffset <= 3 - this.width; ++xOffset) {
            for(int yOffset = 0; yOffset <= 3 - this.height; ++yOffset) {
                if (this.matchesPattern(craftingInventory, xOffset, yOffset, true, false)) {
                    return true;
                }

                if (this.matchesPattern(craftingInventory, xOffset, yOffset, false, false)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean matchesIgnoreType(CraftingInventory craftingInventory) {
        for(int xOffset = 0; xOffset <= 3 - this.width; ++xOffset) {
            for(int yOffset = 0; yOffset <= 3 - this.height; ++yOffset) {
                if (this.matchesPattern(craftingInventory, xOffset, yOffset, true, true)) {
                    return true;
                }

                if (this.matchesPattern(craftingInventory, xOffset, yOffset, false, true)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean matchesPattern(CraftingInventory inv, int xOffset, int yOffset, boolean flipped, boolean ignoreTypeCheck) {
        for(int x = 0; x < 3; ++x) {
            for(int y = 0; y < 3; ++y) {
                int var7 = x - xOffset;
                int var8 = y - yOffset;
                MicroblockRecipeIngredient inputKey = null;
                if (var7 >= 0 && var8 >= 0 && var7 < this.width && var8 < this.height) {
                    if (flipped) {
                        inputKey = this.input[this.width - var7 - 1 + var8 * this.width];
                    } else {
                        inputKey = this.input[var7 + var8 * this.width];
                    }
                }

                ItemStack stack = inv.getStack(x, y);
                if (stack != null || inputKey != null) {
                    if (stack == null || inputKey == null) {
                        return false;
                    }

                    if (inputKey != MicroblockRecipeIngredient.fromItem(stack.getItem())) {
                        return false;
                    }
                    
                    if (!ignoreTypeCheck) {
                        if ((inputKey != MicroblockRecipeIngredient.SAW) && (inputKey != MicroblockRecipeIngredient.BLOCK) && (stack.getItem() instanceof MicroblockItem microblockItem) && ((microblockItem.block != this.block) || (microblockItem.meta != this.meta))) {
                            return false;
                        }

                        if ((inputKey == MicroblockRecipeIngredient.BLOCK) && ((stack.getItem() instanceof BlockItem blockItem) && ((blockItem.getBlock() != this.block) || stack.getDamage() != this.meta))) {
                            return false;
                        }
                    }
                }
            }
        }
        
        return true;
    }

    @Override
    public ItemStack craft(CraftingInventory craftingInventory) {
        return output.copy();
    }

    @Override
    public int getSize() {
        return this.width * this.height;
    }
}
