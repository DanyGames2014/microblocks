package net.danygames2014.microblocks.compat.ami;

import net.danygames2014.microblocks.Microblocks;
import net.danygames2014.microblocks.recipe.MicroblockRecipe;
import net.glasslauncher.mods.alwaysmoreitems.api.recipe.RecipeHandler;
import net.glasslauncher.mods.alwaysmoreitems.api.recipe.RecipeWrapper;
import org.jetbrains.annotations.NotNull;

public class MicroblockRecipeHandler implements RecipeHandler<MicroblockRecipe> {

    @Override
    public @NotNull Class<MicroblockRecipe> getRecipeClass() {
        return MicroblockRecipe.class;
    }

    @Override
    public @NotNull String getRecipeCategoryUid() {
        return Microblocks.NAMESPACE.id("microblocks").toString();
    }

    @Override
    public @NotNull RecipeWrapper getRecipeWrapper(@NotNull MicroblockRecipe recipe) {
        return new MicroblockRecipeWrapper(recipe);
    }

    @Override
    public boolean isRecipeValid(@NotNull MicroblockRecipe recipe) {
        return true;
    }
}
