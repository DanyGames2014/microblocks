package net.danygames2014.microblocks.multipart.model;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.danygames2014.microblocks.multipart.PlacementSlot;
import net.danygames2014.microblocks.util.DirectionUtil;
import net.danygames2014.nyalib.util.BoxUtil;
import net.minecraft.util.math.Box;

public class HollowMicroblockModel extends MicroblockModel{
    public Box bounds_top = Box.create(0D, 0.5D, 0D, 0D, 1D, 1D);
    public Box bounds_bottom = Box.create(0D, 0D, 0D, 0D, 0.5D, 1D);
    public Box bounds_left = Box.create(0D, 0.5D, 0D, 0D, 0.5D, 0.5);
    public Box bounds_right = Box.create(0D, 0.5D, 0.5, 0D, 0.5D, 1D);
    public int holeSize = 8;

    @Override
    public ObjectArrayList<Box> getBoxesForSlot(PlacementSlot slot, int size, double offsetX, double offsetY, double offsetZ) {
        ObjectArrayList<Box> boxes = new ObjectArrayList<>();
        Box box_top = bounds_top.copy();
        box_top.maxX = size * PIXEL_SIZE;
        box_top.minY += ((double) holeSize / 2) * PIXEL_SIZE;

        Box box_bottom = bounds_bottom.copy();
        box_bottom.maxX = size * PIXEL_SIZE;
        box_bottom.maxY -= ((double) holeSize / 2) * PIXEL_SIZE;

        Box box_left = bounds_left.copy();
        box_left.maxX = size * PIXEL_SIZE;
        box_left.maxZ -= ((double) holeSize / 2) * PIXEL_SIZE;
        box_left.minY = box_bottom.maxY;
        box_left.maxY = box_top.minY;

        Box box_right = bounds_right.copy();
        box_right.maxX = size * PIXEL_SIZE;
        box_right.minZ += ((double) holeSize / 2) * PIXEL_SIZE;
        box_right.minY = box_bottom.maxY;
        box_right.maxY = box_top.minY;

        boxes.add(BoxUtil.rotate(box_top, DirectionUtil.faceSlotToDirection(slot)).offset(offsetX, offsetY, offsetZ));
        boxes.add(BoxUtil.rotate(box_bottom, DirectionUtil.faceSlotToDirection(slot)).offset(offsetX, offsetY, offsetZ));
        boxes.add(BoxUtil.rotate(box_left, DirectionUtil.faceSlotToDirection(slot)).offset(offsetX, offsetY, offsetZ));
        boxes.add(BoxUtil.rotate(box_right, DirectionUtil.faceSlotToDirection(slot)).offset(offsetX, offsetY, offsetZ));

        return boxes;
    }

    @Override
    public Box getRenderBounds(PlacementSlot slot, int size, double offsetX, double offsetY, double offsetZ) {
        return Box.create(0D, 0D, 0D, size * PIXEL_SIZE, 1D, 1D);
    }
}
