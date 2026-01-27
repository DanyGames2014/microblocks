package net.danygames2014.microblocks.multipart;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.danygames2014.nyalib.util.BoxUtil;
import net.minecraft.block.Block;
import net.minecraft.util.math.Box;
import net.modificationstation.stationapi.api.util.math.Direction;

public class CornerMicroblockMultipartComponent extends MicroblockMultipartComponent{
    public Box bounds = Box.create(0D, 0D, 0D, 0D, 0D, 0D);

    public CornerMicroblockMultipartComponent(){}

    public CornerMicroblockMultipartComponent(Block block, PlacementSlot slot, int size) {
        super(block, slot, size);
    }

    @Override
    public int getMaxSize() {
        return 14;
    }

    @Override
    public ObjectArrayList<Box> getBoundingBoxes() {
        ObjectArrayList<Box> boxes = new ObjectArrayList<>();
        Box box = bounds.copy();
        box.maxX = size * PIXEL_SIZE;
        box.maxY = size * PIXEL_SIZE;
        box.maxZ = size * PIXEL_SIZE;
        boxes.add(box.offset(x, y, z));
        return boxes;
    }

    @Override
    public void getCollisionBoxes(ObjectArrayList<Box> boxes) {
        Box box = bounds.copy();
        box.maxX = size * PIXEL_SIZE;
        box.maxY = size * PIXEL_SIZE;
        box.maxZ = size * PIXEL_SIZE;
        boxes.add(box.offset(x, y, z));
    }
}
