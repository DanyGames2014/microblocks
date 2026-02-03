package net.danygames2014.microblocks.multipart.model;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.danygames2014.microblocks.multipart.PlacementSlot;
import net.danygames2014.microblocks.util.DirectionUtil;
import net.danygames2014.microblocks.util.MicroblockBoxUtil;
import net.danygames2014.nyalib.util.BoxUtil;
import net.minecraft.util.math.Box;

public class FaceMicroblockModel extends MicroblockModel{
    public Box bounds = Box.create(0D, 0D, 0D, 0D, 1D, 1D);

    @Override
    public ObjectArrayList<Box> getBoxesForSlot(PlacementSlot slot, int size, double offsetX, double offsetY, double offsetZ) {
        if(slot == null){
            slot = PlacementSlot.FACE_NEG_Z;
        }
        ObjectArrayList<Box> boxes = new ObjectArrayList<>();
        Box box = MicroblockBoxUtil.copy(bounds);
        box.maxX = size * PIXEL_SIZE;
        boxes.add(MicroblockBoxUtil.offset(BoxUtil.rotate(box, DirectionUtil.faceSlotToDirection(slot)), offsetX, offsetY, offsetZ));
        return boxes;
    }

    @Override
    public Box getRenderBounds(PlacementSlot slot, int size, double offsetX, double offsetY, double offsetZ) {
        if(slot == null){
            slot = PlacementSlot.FACE_NEG_Z;
        }
        return getBoxesForSlot(slot, size, offsetX, offsetY, offsetZ).get(0);
    }
}
