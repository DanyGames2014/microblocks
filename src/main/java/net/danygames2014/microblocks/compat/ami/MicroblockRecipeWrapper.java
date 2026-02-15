package net.danygames2014.microblocks.compat.ami;

import net.danygames2014.microblocks.Microblocks;
import net.danygames2014.microblocks.microblock.MicroblockRegistry;
import net.danygames2014.microblocks.recipe.MicroblockRecipe;
import net.danygames2014.microblocks.recipe.MicroblockRecipeIngredient;
import net.glasslauncher.mods.alwaysmoreitems.api.recipe.RecipeWrapper;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MicroblockRecipeWrapper implements RecipeWrapper {

    public final MicroblockRecipe recipe;

    public MicroblockRecipeWrapper(MicroblockRecipe recipe){
        this.recipe = recipe;
    }

    @Override
    public List<?> getInputs() {
        ArrayList<Object> items = new ArrayList<>();
        for(MicroblockRecipeIngredient ingredient : recipe.getInput()){
            if(ingredient == null) {
                items.add(null);
                continue;
            }
            if(ingredient == MicroblockRecipeIngredient.SAW){
                items.add(List.of(new ItemStack(Microblocks.ironHandsaw), new ItemStack(Microblocks.diamondHandsaw)));
                continue;
            }
            if(ingredient == MicroblockRecipeIngredient.BLOCK) {
                ArrayList<ItemStack> stacks = new ArrayList<>();
                for(Block block : MicroblockRegistry.getInstance().registry.keySet()){
                    int[] metas = MicroblockRegistry.getMeta(block);
                    for(int meta : metas) {
                        stacks.add(new ItemStack(block, 1,  meta));
                    }
                }
                items.add(stacks);
                continue;
            }
            ArrayList<ItemStack> stacks = new ArrayList<>();
            for(var block : Microblocks.microblockItems.get(MicroblockRecipeIngredient.toItemType(ingredient)).entrySet()){

                var metas = block.getValue().int2ObjectEntrySet();
                for(var entry : metas) {
                    stacks.add(new ItemStack(entry.getValue(), 1,  entry.getIntKey()));
                }
            }
            items.add(stacks);
        }
        return items;
    }

    @Override
    public List<?> getOutputs() {
        ArrayList<Object> items = new ArrayList<>();
        MicroblockRecipeIngredient outputType = recipe.getOutputType();

        if(outputType == MicroblockRecipeIngredient.SAW){
            items.add(List.of(new ItemStack(Microblocks.ironHandsaw), new ItemStack(Microblocks.diamondHandsaw)));
        }

        if(outputType == MicroblockRecipeIngredient.BLOCK) {
            ArrayList<ItemStack> stacks = new ArrayList<>();
            for(Block block : MicroblockRegistry.getInstance().registry.keySet()){
                int[] metas = MicroblockRegistry.getMeta(block);
                for(int meta : metas) {
                    stacks.add(new ItemStack(block, recipe.getOutputAmount(),  meta));
                }
            }
            items.add(stacks);
        }
        else {
            ArrayList<ItemStack> stacks = new ArrayList<>();
            for(var block : Microblocks.microblockItems.get(MicroblockRecipeIngredient.toItemType(outputType)).entrySet()){

                var metas = block.getValue().int2ObjectEntrySet();
                for(var entry : metas) {
                    stacks.add(new ItemStack(entry.getValue(), recipe.getOutputAmount(),  entry.getIntKey()));
                }
            }
            items.add(stacks);
        }

        return items;
    }

    @Override
    public void drawInfo(@NotNull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {

    }

    @Override
    public void drawAnimations(@NotNull Minecraft minecraft, int recipeWidth, int recipeHeight) {

    }

    @Override
    public @Nullable ArrayList<Object> getTooltip(int mouseX, int mouseY) {
        return null;
    }

    @Override
    public boolean handleClick(@NotNull Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
        return false;
    }
}
