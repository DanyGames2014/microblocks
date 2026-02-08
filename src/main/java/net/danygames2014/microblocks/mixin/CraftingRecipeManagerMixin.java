package net.danygames2014.microblocks.mixin;

import net.danygames2014.microblocks.Microblocks;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CraftingRecipeManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CraftingRecipeManager.class)
public class CraftingRecipeManagerMixin {
    @Inject(method = "craft", at = @At(value = "HEAD"), cancellable = true)
    public void doMicroblockCrafting(CraftingInventory inv, CallbackInfoReturnable<ItemStack> cir) {
        ItemStack stack = Microblocks.recipeManager.craft(inv);
        
        if(stack != null){
            cir.setReturnValue(stack);
        }
    }
    
}
