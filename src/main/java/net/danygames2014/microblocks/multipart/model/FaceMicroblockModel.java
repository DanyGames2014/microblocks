package net.danygames2014.microblocks.multipart.model;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.danygames2014.microblocks.util.DirectionUtil;
import net.danygames2014.microblocks.util.MicroblockBoxUtil;
import net.danygames2014.nyalib.block.voxelshape.VoxelData;
import net.danygames2014.nyalib.block.voxelshape.VoxelShape;
import net.danygames2014.nyalib.multipart.MultipartSlot;
import net.danygames2014.nyalib.util.BoxUtil;
import net.minecraft.util.math.Box;

public class FaceMicroblockModel extends MicroblockModel{
    public Box bounds = Box.create(0D, 0D, 0D, 0D, 1D, 1D);

    @Override
    public VoxelShape getShapeForSlot(MultipartSlot slot, int size, int offsetX, int offsetY, int offsetZ) {
        if(slot == null){
            slot = MultipartSlot.FACE_NEG_Z;
        }
        ObjectArrayList<Box> boxes = new ObjectArrayList<>();
        Box box = MicroblockBoxUtil.copy(bounds);
        box.maxX = size * PIXEL_SIZE;
        boxes.add(BoxUtil.rotate(box, DirectionUtil.faceSlotToDirection(slot)));
        return new VoxelData(boxes.toArray(new Box[0])).withOffset(offsetX, offsetY, offsetZ);
    }

    @Override
    public Box getRenderBounds(MultipartSlot slot, int size, double offsetX, double offsetY, double offsetZ) {
        if(slot == null){
            slot = MultipartSlot.FACE_NEG_Z;
        }
        return getShapeForSlot(slot, size, (int) offsetX, (int) offsetY, (int) offsetZ).getOffsetBoxes().get(0);
    }
}
