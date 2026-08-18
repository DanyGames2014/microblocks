package net.danygames2014.microblocks.multipart.model;

import net.danygames2014.nyalib.block.voxelshape.VoxelShape;
import net.danygames2014.nyalib.multipart.MultipartSlot;
import net.minecraft.util.math.Box;
import org.jetbrains.annotations.Nullable;


public abstract class MicroblockModel {
    public static double PIXEL_SIZE = 1D / 16D;
    public abstract VoxelShape getShapeForSlot(@Nullable MultipartSlot slot, int size, int offsetX, int offsetY, int offsetZ);
    public abstract Box getRenderBounds(MultipartSlot slot, int size, double offsetX, double offsetY, double offsetZ);
}
