package net.danygames2014.microblocks.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.danygames2014.nyalib.multipart.MultipartState;
import net.minecraft.block.FlowingLiquidBlock;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorByName;

@Mixin(FlowingLiquidBlock.class)
public class FlowingLiquidBlockMixin {
    @WrapOperation(method = "getSpread", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/FlowingLiquidBlock;isLiquidBreaking(Lnet/minecraft/world/World;III)Z", ordinal = 0))
    public boolean modifySpreadForMicroblocks(FlowingLiquidBlock block, World world, int x, int y, int z, Operation<Boolean> original, @Local(ordinal = 3) int var5) {
        MultipartState state = world.getMultipartState(x, y, z);
        
        switch (var5) {
            // X-
            case 0 -> {
                if (state != null && state.isBoxOccupied(Box.create(x + 1, y, z, x + 1 + 0.0625D, y + 1, z + 1))) {
                    return true;
                }
                
                MultipartState neighborState = world.getMultipartState(x, y, z);
                if (neighborState != null && neighborState.isBoxOccupied(Box.create(x + 0.9375D, y, z, x + 1, y + 1, z + 1))) {
                    return true;
                }
            }
            
            // X+
            case 1 -> {
                if (state != null && state.isBoxOccupied(Box.create(x, y, z, x + 0.9375D, y + 1, z + 1))) {
                    return true;
                }

                MultipartState neighborState = world.getMultipartState(x - 1, y, z);
                if (neighborState != null && neighborState.isBoxOccupied(Box.create(x + 0.0625D, y, z, x + 1, y + 1, z + 1))) {
                    return true;
                }
            }
            
            // Z-
            case 2 -> {
                
            }
            
            // Z+
            case 3 -> {
                
            }
        }
        
        return original.call(block, world, x, y, z);
    }

    @WrapOperation(method = "getSpread", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/FlowingLiquidBlock;isLiquidBreaking(Lnet/minecraft/world/World;III)Z", ordinal = 1))
    public boolean modifySpreadForMicroblocks(FlowingLiquidBlock block, World world, int x, int y, int z, Operation<Boolean> original) {
        // Y-

        return original.call(block, world, x, y, z);
    }
}
