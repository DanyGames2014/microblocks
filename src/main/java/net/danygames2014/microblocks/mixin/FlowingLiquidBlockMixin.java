package net.danygames2014.microblocks.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import net.danygames2014.nyalib.multipart.MultipartState;
import net.minecraft.block.FlowingLiquidBlock;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@SuppressWarnings("RedundantIfStatement")
@Mixin(FlowingLiquidBlock.class)
public class FlowingLiquidBlockMixin {
    @WrapWithCondition(method = "onTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/FlowingLiquidBlock;spreadTo(Lnet/minecraft/world/World;IIII)V", ordinal = 0))
    public boolean checkSpreadNegativeX(FlowingLiquidBlock block, World world, int targetX, int targetY, int targetZ, int depth, @Local(ordinal = 0, argsOnly = true) int x, @Local(ordinal = 1, argsOnly = true) int y, @Local(ordinal = 2, argsOnly = true) int z) {
        MultipartState state = world.getMultipartState(x,y,z);
        MultipartState targetState = world.getMultipartState(targetX,targetY,targetZ);
        
        if (state != null && state.isBoxFullyCoevered(Box.create(x, y, z, x + 0.0625D, y + 1, z + 1))) {
            return false;
        }
        
        if (targetState != null && targetState.isBoxFullyCoevered(Box.create(targetX + 0.9375D, targetY, targetZ, targetX + 1, targetY + 1, targetZ + 1))) {
            return false;
        }
        
        return true;
    }

    @WrapWithCondition(method = "onTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/FlowingLiquidBlock;spreadTo(Lnet/minecraft/world/World;IIII)V", ordinal = 1))
    public boolean checkSpreadPositiveX(FlowingLiquidBlock block, World world, int targetX, int targetY, int targetZ, int depth, @Local(ordinal = 0, argsOnly = true) int x, @Local(ordinal = 1, argsOnly = true) int y, @Local(ordinal = 2, argsOnly = true) int z) {
        MultipartState state = world.getMultipartState(x,y,z);
        MultipartState targetState = world.getMultipartState(targetX,targetY,targetZ);

        if (state != null && state.isBoxFullyCoevered(Box.create(x + 0.9375D, y, z, x + 1, y + 1, z + 1))) {
            return false;
        }

        if (targetState != null && targetState.isBoxFullyCoevered(Box.create(targetX, targetY, targetZ, targetX + 0.0625D, targetY + 1, targetZ + 1))) {
            return false;
        }

        return true;
    }

    @WrapWithCondition(method = "onTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/FlowingLiquidBlock;spreadTo(Lnet/minecraft/world/World;IIII)V", ordinal = 2))
    public boolean checkSpreadNegativeZ(FlowingLiquidBlock block, World world, int targetX, int targetY, int targetZ, int depth, @Local(ordinal = 0, argsOnly = true) int x, @Local(ordinal = 1, argsOnly = true) int y, @Local(ordinal = 2, argsOnly = true) int z) {
        MultipartState state = world.getMultipartState(x,y,z);
        MultipartState targetState = world.getMultipartState(targetX,targetY,targetZ);

        if (state != null && state.isBoxFullyCoevered(Box.create(x, y, z, x + 1, y + 1, z + 0.0625D))) {
            return false;
        }

        if (targetState != null && targetState.isBoxFullyCoevered(Box.create(targetX, targetY, targetZ + 0.9375D, targetX + 1, targetY + 1, targetZ + 1))) {
            return false;
        }

        return true;
    }

    @WrapWithCondition(method = "onTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/FlowingLiquidBlock;spreadTo(Lnet/minecraft/world/World;IIII)V", ordinal = 3))
    public boolean checkSpreadPositiveZ(FlowingLiquidBlock block, World world, int targetX, int targetY, int targetZ, int depth, @Local(ordinal = 0, argsOnly = true) int x, @Local(ordinal = 1, argsOnly = true) int y, @Local(ordinal = 2, argsOnly = true) int z) {
        MultipartState state = world.getMultipartState(x,y,z);
        MultipartState targetState = world.getMultipartState(targetX,targetY,targetZ);

        if (state != null && state.isBoxFullyCoevered(Box.create(x, y, z  + 0.9375D, x + 1, y + 1, z + 1))) {
            return false;
        }

        if (targetState != null && targetState.isBoxFullyCoevered(Box.create(targetX, targetY, targetZ, targetX + 1, targetY + 1, targetZ + 0.0625D))) {
            return false;
        }

        return true;
    }

//    @WrapOperation(method = "getSpread", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/FlowingLiquidBlock;isLiquidBreaking(Lnet/minecraft/world/World;III)Z", ordinal = 0))
//    public boolean modifySpreadForMicroblocks(FlowingLiquidBlock block, World world, int x, int y, int z, Operation<Boolean> original, @Local(ordinal = 3) int var5) {
//        MultipartState state = world.getMultipartState(x, y, z);
//
//        switch (var5) {
//            // X-
//            case 0 -> {
//                if (state != null && state.isBoxOccupied(Box.create(x + 1, y, z, x + 1 + 0.0625D, y + 1, z + 1))) {
//                    return true;
//                }
//
//                MultipartState neighborState = world.getMultipartState(x, y, z);
//                if (neighborState != null && neighborState.isBoxOccupied(Box.create(x + 0.9375D, y, z, x + 1, y + 1, z + 1))) {
//                    return true;
//                }
//            }
//
//            // X+
//            case 1 -> {
//                if (state != null && state.isBoxOccupied(Box.create(x, y, z, x + 0.9375D, y + 1, z + 1))) {
//                    return true;
//                }
//
//                MultipartState neighborState = world.getMultipartState(x - 1, y, z);
//                if (neighborState != null && neighborState.isBoxOccupied(Box.create(x + 0.0625D, y, z, x + 1, y + 1, z + 1))) {
//                    return true;
//                }
//            }
//
//            // Z-
//            case 2 -> {
//
//            }
//
//            // Z+
//            case 3 -> {
//
//            }
//        }
//
//        return original.call(block, world, x, y, z);
//    }
//
//    @WrapOperation(method = "getSpread", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/FlowingLiquidBlock;isLiquidBreaking(Lnet/minecraft/world/World;III)Z", ordinal = 1))
//    public boolean modifySpreadForMicroblocks(FlowingLiquidBlock block, World world, int x, int y, int z, Operation<Boolean> original) {
//        // Y-
//
//        return original.call(block, world, x, y, z);
//    }
}
