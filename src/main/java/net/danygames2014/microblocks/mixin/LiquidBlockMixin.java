package net.danygames2014.microblocks.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.danygames2014.nyalib.multipart.MultipartState;
import net.minecraft.block.LiquidBlock;
import net.minecraft.util.math.Box;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LiquidBlock.class)
public class LiquidBlockMixin {
    @WrapOperation(method = "getFlow", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/LiquidBlock;getLiquidDepth(Lnet/minecraft/world/BlockView;III)I", ordinal = 1))
    public int aVoid(LiquidBlock instance, BlockView blockView, int targetX, int targetY, int targetZ, Operation<Integer> original, @Local(ordinal = 4) int var7, @Local(ordinal = 0, argsOnly = true) int x, @Local(ordinal = 1, argsOnly = true) int y, @Local(ordinal = 2, argsOnly = true) int z) {
        if (blockView instanceof World world) {
            switch (var7) {
                // X-
                case 1 -> {
                    MultipartState state = world.getMultipartState(x,y,z);
                    MultipartState targetState = world.getMultipartState(targetX,targetY,targetZ);

                    if (state != null && state.isBoxFullyCovered(Box.create(x, y, z, x + 0.0625D, y + 1, z + 1))) {
                        return 0;
                    }

                    if (targetState != null && targetState.isBoxFullyCovered(Box.create(targetX + 0.9375D, targetY, targetZ, targetX + 1, targetY + 1, targetZ + 1))) {
                        return 0;
                    }
                }   
                
                // Z-
                case 2 -> {
                    MultipartState state = world.getMultipartState(x,y,z);
                    MultipartState targetState = world.getMultipartState(targetX,targetY,targetZ);

                    if (state != null && state.isBoxFullyCovered(Box.create(x, y, z, x + 1, y + 1, z + 0.0625D))) {
                        return 0;
                    }

                    if (targetState != null && targetState.isBoxFullyCovered(Box.create(targetX, targetY, targetZ + 0.9375D, targetX + 1, targetY + 1, targetZ + 1))) {
                        return 0;
                    }

                }
                
                // X+
                case 3 -> {
                    MultipartState state = world.getMultipartState(x,y,z);
                    MultipartState targetState = world.getMultipartState(targetX,targetY,targetZ);

                    if (state != null && state.isBoxFullyCovered(Box.create(x + 0.9375D, y, z, x + 1, y + 1, z + 1))) {
                        return 0;
                    }

                    if (targetState != null && targetState.isBoxFullyCovered(Box.create(targetX, targetY, targetZ, targetX + 0.0625D, targetY + 1, targetZ + 1))) {
                        return 0;
                    }

                }
                
                // Z+
                case 4 -> {
                    MultipartState state = world.getMultipartState(x,y,z);
                    MultipartState targetState = world.getMultipartState(targetX,targetY,targetZ);

                    if (state != null && state.isBoxFullyCovered(Box.create(x, y, z  + 0.9375D, x + 1, y + 1, z + 1))) {
                        return 0;
                    }

                    if (targetState != null && targetState.isBoxFullyCovered(Box.create(targetX, targetY, targetZ, targetX + 1, targetY + 1, targetZ + 0.0625D))) {
                        return 0;
                    }
                }
            }
        }
        
        return original.call(instance, blockView, targetX, targetY, targetZ);
    }
    
//    // Z-
//    @WrapOperation(method = "getFlow", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/LiquidBlock;isSolidFace(Lnet/minecraft/world/BlockView;IIII)Z", ordinal = 0))
//    public boolean getNegativeZFlow(LiquidBlock block, BlockView blockView, int targetX, int targetY, int targetZ, int face, Operation<Boolean> original, @Local(ordinal = 0, argsOnly = true) int x, @Local(ordinal = 1, argsOnly = true) int y, @Local(ordinal = 2, argsOnly = true) int z) {
//        if (blockView instanceof World world) {
//            MultipartState state = world.getMultipartState(x,y,z);
//            MultipartState targetState = world.getMultipartState(targetX,targetY,targetZ);
//
//            if (state != null && state.isBoxFullyCoevered(Box.create(x, y, z, x + 1, y + 1, z + 0.0625D))) {
//                return false;
//            }
//
//            if (targetState != null && targetState.isBoxFullyCoevered(Box.create(targetX, targetY, targetZ + 0.9375D, targetX + 1, targetY + 1, targetZ + 1))) {
//                return false;
//            }
//        }
//        
//        return original.call(block, blockView, targetX, targetY, targetZ, face);
//    }
//
//    // Z+
//    @WrapOperation(method = "getFlow", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/LiquidBlock;isSolidFace(Lnet/minecraft/world/BlockView;IIII)Z", ordinal = 1))
//    public boolean getPositiveZFlow(LiquidBlock block, BlockView blockView, int targetX, int targetY, int targetZ, int face, Operation<Boolean> original, @Local(ordinal = 0, argsOnly = true) int x, @Local(ordinal = 1, argsOnly = true) int y, @Local(ordinal = 2, argsOnly = true) int z) {
//        if (blockView instanceof World world) {
//            MultipartState state = world.getMultipartState(x,y,z);
//            MultipartState targetState = world.getMultipartState(targetX,targetY,targetZ);
//
//            if (state != null && state.isBoxFullyCoevered(Box.create(x, y, z  + 0.9375D, x + 1, y + 1, z + 1))) {
//                return false;
//            }
//
//            if (targetState != null && targetState.isBoxFullyCoevered(Box.create(targetX, targetY, targetZ, targetX + 1, targetY + 1, targetZ + 0.0625D))) {
//                return false;
//            }
//        }
//        
//        return original.call(block, blockView, targetX, targetY, targetZ, face);
//    }
//
//    // X-
//    @WrapOperation(method = "getFlow", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/LiquidBlock;isSolidFace(Lnet/minecraft/world/BlockView;IIII)Z", ordinal = 2))
//    public boolean getNegativeXFlow(LiquidBlock block, BlockView blockView, int targetX, int targetY, int targetZ, int face, Operation<Boolean> original, @Local(ordinal = 0, argsOnly = true) int x, @Local(ordinal = 1, argsOnly = true) int y, @Local(ordinal = 2, argsOnly = true) int z) {
//        if (blockView instanceof World world) {
//            MultipartState state = world.getMultipartState(x,y,z);
//            MultipartState targetState = world.getMultipartState(targetX,targetY,targetZ);
//
//            if (state != null && state.isBoxFullyCoevered(Box.create(x, y, z, x + 0.0625D, y + 1, z + 1))) {
//                return false;
//            }
//
//            if (targetState != null && targetState.isBoxFullyCoevered(Box.create(targetX + 0.9375D, targetY, targetZ, targetX + 1, targetY + 1, targetZ + 1))) {
//                return false;
//            }
//        }
//        
//        return original.call(block, blockView, targetX, targetY, targetZ, face);
//    }
//
//    // X+
//    @WrapOperation(method = "getFlow", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/LiquidBlock;isSolidFace(Lnet/minecraft/world/BlockView;IIII)Z", ordinal = 3))
//    public boolean getPositiveXFlow(LiquidBlock block, BlockView blockView, int targetX, int targetY, int targetZ, int face, Operation<Boolean> original, @Local(ordinal = 0, argsOnly = true) int x, @Local(ordinal = 1, argsOnly = true) int y, @Local(ordinal = 2, argsOnly = true) int z) {
//        if (blockView instanceof World world) {
//            MultipartState state = world.getMultipartState(x,y,z);
//            MultipartState targetState = world.getMultipartState(targetX,targetY,targetZ);
//
//            if (state != null && state.isBoxFullyCoevered(Box.create(x + 0.9375D, y, z, x + 1, y + 1, z + 1))) {
//                return false;
//            }
//
//            if (targetState != null && targetState.isBoxFullyCoevered(Box.create(targetX, targetY, targetZ, targetX + 0.0625D, targetY + 1, targetZ + 1))) {
//                return false;
//            }
//        }
//        
//        return original.call(block, blockView, targetX, targetY, targetZ, face);
//    }
}
