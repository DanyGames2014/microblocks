package net.danygames2014.microblocks.compat.ami;

import net.danygames2014.microblocks.Microblocks;
import net.glasslauncher.mods.alwaysmoreitems.api.gui.AMIDrawable;
import net.glasslauncher.mods.alwaysmoreitems.api.gui.CraftingGridHelper;
import net.glasslauncher.mods.alwaysmoreitems.api.gui.GuiItemStackGroup;
import net.glasslauncher.mods.alwaysmoreitems.api.gui.RecipeLayout;
import net.glasslauncher.mods.alwaysmoreitems.api.recipe.RecipeCategory;
import net.glasslauncher.mods.alwaysmoreitems.api.recipe.RecipeWrapper;
import net.glasslauncher.mods.alwaysmoreitems.api.recipe.VanillaRecipeCategoryUid;
import net.glasslauncher.mods.alwaysmoreitems.api.recipe.wrapper.CraftingRecipeWrapper;
import net.glasslauncher.mods.alwaysmoreitems.api.recipe.wrapper.ShapedCraftingRecipeWrapper;
import net.glasslauncher.mods.alwaysmoreitems.gui.DrawableHelper;
import net.glasslauncher.mods.alwaysmoreitems.util.AlwaysMoreItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resource.language.TranslationStorage;

import javax.annotation.Nonnull;

public class MicroblockRecipeCategory implements RecipeCategory {

    private static final int craftOutputSlot = 0;

    @Nonnull
    private final AMIDrawable background;
    @Nonnull
    private final String localizedName;

    public MicroblockRecipeCategory() {
        background = DrawableHelper.createDrawable("/gui/crafting.png", 29, 16, 116, 54);
        localizedName = TranslationStorage.getInstance().get("gui.microblocks.category.microblocks");
    }

    @Override
    @Nonnull
    public String getUid() {
        return Microblocks.NAMESPACE.id("microblocks").toString();
    }

    @Nonnull
    @Override
    public String getTitle() {
        return localizedName;
    }

    @Override
    @Nonnull
    public AMIDrawable getBackground() {
        return background;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {

    }

    @Override
    public void drawAnimations(Minecraft minecraft) {

    }

    @Override
    public void setRecipe(@Nonnull RecipeLayout recipeLayout, @Nonnull RecipeWrapper recipeWrapper) {
        MicroblockRecipeWrapper wrapper = (MicroblockRecipeWrapper) recipeWrapper;
        GuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        guiItemStacks.init(craftOutputSlot, false, 94, 18);
        guiItemStacks.setFromRecipe(craftOutputSlot, recipeWrapper.getOutputs().get(0));




        for (int y = 0; y < wrapper.recipe.getHeight(); ++y) {
            for (int x = 0; x < wrapper.recipe.getWidth(); ++x) {
                int index = x + (y * wrapper.recipe.getWidth());
                guiItemStacks.init(index + 1, true, x * 18, y * 18);
                guiItemStacks.setFromRecipe(index + 1, recipeWrapper.getInputs().get(index));
            }
        }
    }

}
