package net.danygames2014.microblocks.multipart.model;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.danygames2014.microblocks.util.MicroblockBoxUtil;
import net.danygames2014.nyalib.block.voxelshape.VoxelData;
import net.danygames2014.nyalib.block.voxelshape.VoxelShape;
import net.danygames2014.nyalib.multipart.MultipartSlot;
import net.minecraft.util.math.Box;

public class CornerMicroblockModel extends MicroblockModel{

    public Box bounds = Box.create(0D, 0D, 0D, 0D, 0D, 0D);

    @Override
    public VoxelShape getShapeForSlot(MultipartSlot slot, int size, int offsetX, int offsetY, int offsetZ) {
        if(slot == null){
            slot = MultipartSlot.CORNER_BOT_NEG_X_NEG_Z;
        }
        ObjectArrayList<Box> boxes = new ObjectArrayList<>();
        Box box = bounds.copy();
        box.maxX = size * PIXEL_SIZE;
        box.maxY = size * PIXEL_SIZE;
        box.maxZ = size * PIXEL_SIZE;
        boxes.add(MicroblockBoxUtil.transformCornerMicroblock(box, slot));
        return new VoxelData(boxes.toArray(new Box[0])).withOffset(offsetX, offsetY, offsetZ);
    }

    @Override
    public Box getRenderBounds(MultipartSlot slot, int size, double offsetX, double offsetY, double offsetZ) {
        if(slot == null){
            slot = MultipartSlot.CORNER_BOT_NEG_X_NEG_Z;
        }
        return getShapeForSlot(slot, size, (int) offsetX, (int) offsetY, (int) offsetZ).getOffsetBoxes().get(0);
    }
}
