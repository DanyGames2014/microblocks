package net.danygames2014.microblocks.multipart.model;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.danygames2014.microblocks.util.MicroblockBoxUtil;
import net.danygames2014.nyalib.block.voxelshape.VoxelData;
import net.danygames2014.nyalib.block.voxelshape.VoxelShape;
import net.danygames2014.nyalib.multipart.MultipartSlot;
import net.minecraft.util.math.Box;
import net.modificationstation.stationapi.api.util.math.Direction;

public class PostMicroblockModel extends MicroblockModel{
    public Box bounds = Box.create(0.5D, 0.5D, 0D, 0.5D, 0.5D, 1D);

    public final Direction.Axis axis;

    public static PostMicroblockModel[] MODELS = new PostMicroblockModel[3];

    static {
        for(Direction.Axis axis1 : Direction.Axis.VALUES) {
            MODELS[axis1.ordinal()] = new PostMicroblockModel(axis1);
        }
    }

    public PostMicroblockModel(Direction.Axis axis) {
        this.axis = axis;
    }

    @Override
    public VoxelShape getShapeForSlot(MultipartSlot slot, int size, int offsetX, int offsetY, int offsetZ) {
        ObjectArrayList<Box> boxes = new ObjectArrayList<>();
        Box box = bounds.copy();
        box.minX -= ((float)size * 0.5f) * PIXEL_SIZE;
        box.minY -= ((float)size * 0.5f) * PIXEL_SIZE;
        box.maxX += ((float)size * 0.5f) * PIXEL_SIZE;
        box.maxY += ((float)size * 0.5f) * PIXEL_SIZE;
        boxes.add(MicroblockBoxUtil.transformPostMicroblock(box, axis));
        return new VoxelData(boxes.toArray(new Box[0])).withOffset(offsetX, offsetY, offsetZ);
    }

    @Override
    public Box getRenderBounds(MultipartSlot slot, int size, double offsetX, double offsetY, double offsetZ) {
        if(slot == null){
            slot = MultipartSlot.CUSTOM;
        }
        return getShapeForSlot(slot, size, (int) offsetX, (int) offsetY, (int) offsetZ).getOffsetBoxes().get(0);
    }
}
