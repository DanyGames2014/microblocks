package net.danygames2014.microblocks.multipart.model;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.danygames2014.microblocks.util.DirectionUtil;
import net.danygames2014.nyalib.block.voxelshape.VoxelData;
import net.danygames2014.nyalib.block.voxelshape.VoxelShape;
import net.danygames2014.nyalib.multipart.MultipartSlot;
import net.danygames2014.nyalib.util.BoxUtil;
import net.minecraft.util.math.Box;

public class HollowMicroblockModel extends MicroblockModel{
    public Box bounds_top = Box.create(0D, 0.5D, 0D, 0D, 1D, 1D);
    public Box bounds_bottom = Box.create(0D, 0D, 0D, 0D, 0.5D, 1D);
    public Box bounds_left = Box.create(0D, 0.5D, 0D, 0D, 0.5D, 0.5);
    public Box bounds_right = Box.create(0D, 0.5D, 0.5, 0D, 0.5D, 1D);
    public int holeSize = 8;
    public int ringWidth = 2;

    @Override
    public VoxelShape getShapeForSlot(MultipartSlot slot, int size, int offsetX, int offsetY, int offsetZ) {
        if(slot == null){
            slot = MultipartSlot.FACE_NEG_Z;
        }
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

        boxes.add(BoxUtil.rotate(box_top, DirectionUtil.faceSlotToDirection(slot)));
        boxes.add(BoxUtil.rotate(box_bottom, DirectionUtil.faceSlotToDirection(slot)));
        boxes.add(BoxUtil.rotate(box_left, DirectionUtil.faceSlotToDirection(slot)));
        boxes.add(BoxUtil.rotate(box_right, DirectionUtil.faceSlotToDirection(slot)));

        return new VoxelData(boxes.toArray(new Box[0])).withOffset(offsetX, offsetY, offsetZ);
    }

    public VoxelShape getRingShapeForSlot(MultipartSlot slot, int size, int offsetX, int offsetY, int offsetZ) {
        if (slot == null) {
            slot = MultipartSlot.FACE_NEG_Z;
        }
        ObjectArrayList<Box> boxes = new ObjectArrayList<>();

        double holeRadiusOffset = ((double) holeSize / 2) * PIXEL_SIZE;
        double ringOuterOffset = ((double) holeSize / 2 + ringWidth) * PIXEL_SIZE;

        double ringMaxX = size * PIXEL_SIZE;

        Box ring_top = bounds_top.copy();
        ring_top.maxX = ringMaxX;
        ring_top.minY = 0.5D + holeRadiusOffset;
        ring_top.maxY = 0.5D + ringOuterOffset;
        ring_top.minZ = 0.5D - ringOuterOffset;
        ring_top.maxZ = 0.5D + ringOuterOffset;

        Box ring_bottom = bounds_bottom.copy();
        ring_bottom.maxX = ringMaxX;
        ring_bottom.minY = 0.5D - ringOuterOffset;
        ring_bottom.maxY = 0.5D - holeRadiusOffset;
        ring_bottom.minZ = 0.5D - ringOuterOffset;
        ring_bottom.maxZ = 0.5D + ringOuterOffset;

        Box ring_left = bounds_left.copy();
        ring_left.maxX = ringMaxX;
        ring_left.minY = ring_bottom.maxY;
        ring_left.maxY = ring_top.minY;
        ring_left.minZ = 0.5D - ringOuterOffset;
        ring_left.maxZ = 0.5D - holeRadiusOffset;

        Box ring_right = bounds_right.copy();
        ring_right.maxX = ringMaxX;
        ring_right.minY = ring_bottom.maxY;
        ring_right.maxY = ring_top.minY;
        ring_right.minZ = 0.5D + holeRadiusOffset;
        ring_right.maxZ = 0.5D + ringOuterOffset;

        boxes.add(BoxUtil.rotate(ring_top, DirectionUtil.faceSlotToDirection(slot)));
        boxes.add(BoxUtil.rotate(ring_bottom, DirectionUtil.faceSlotToDirection(slot)));
        boxes.add(BoxUtil.rotate(ring_left, DirectionUtil.faceSlotToDirection(slot)));
        boxes.add(BoxUtil.rotate(ring_right, DirectionUtil.faceSlotToDirection(slot)));

        return new VoxelData(boxes.toArray(new Box[0])).withOffset(offsetX, offsetY, offsetZ);
    }

    @Override
    public Box getRenderBounds(MultipartSlot slot, int size, double offsetX, double offsetY, double offsetZ) {
        if(slot == null){
            slot = MultipartSlot.FACE_NEG_Z;
        }
        return BoxUtil.rotate(Box.create(0D, 0D, 0D, size * PIXEL_SIZE, 1D, 1D), DirectionUtil.faceSlotToDirection(slot)).offset(offsetX, offsetY, offsetZ);
    }
}
