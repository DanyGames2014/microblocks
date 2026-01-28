package net.danygames2014.microblocks.multipart.model;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.danygames2014.microblocks.multipart.PlacementSlot;
import net.danygames2014.microblocks.util.MicroblockBoxUtil;
import net.minecraft.util.math.Box;

public class EdgeMicroblockModel extends MicroblockModel{
    public Box bounds = Box.create(0D, 0D, 0D, 0D, 0D, 1D);
    @Override
    public ObjectArrayList<Box> getBoxesForSlot(PlacementSlot slot, int size, double offsetX, double offsetY, double offsetZ) {
        ObjectArrayList<Box> boxes = new ObjectArrayList<>();
        Box box = bounds.copy();
        box.maxX = size * PIXEL_SIZE;
        box.maxY = size * PIXEL_SIZE;
        boxes.add(MicroblockBoxUtil.transformEdgeMicroblock(box, slot).offset(offsetX, offsetY, offsetZ));
        return boxes;
    }
}
