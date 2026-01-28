package net.danygames2014.microblocks.multipart;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.danygames2014.microblocks.util.DirectionUtil;
import net.danygames2014.microblocks.util.MicroblockBoxUtil;
import net.danygames2014.nyalib.util.BoxUtil;
import net.minecraft.block.Block;
import net.minecraft.util.math.Box;

public class PostMicroblockMultipartComponent extends MicroblockMultipartComponent{
    public Box bounds = Box.create(0.5D, 0.5D, 0D, 0.5D, 0.5D, 1D);

    public PostMicroblockMultipartComponent(){}

    public PostMicroblockMultipartComponent(Block block, PlacementSlot slot, int size) {
        super(block, slot, size);
    }

    @Override
    public int getMaxSize() {
        return 8;
    }

    @Override
    public ObjectArrayList<Box> getBoundingBoxes() {
        ObjectArrayList<Box> boxes = new ObjectArrayList<>();
        Box box = bounds.copy();
        box.minX -= ((float)size * 0.5f) * PIXEL_SIZE;
        box.minY -= ((float)size * 0.5f) * PIXEL_SIZE;
        box.maxX += ((float)size * 0.5f) * PIXEL_SIZE;
        box.maxY += ((float)size * 0.5f) * PIXEL_SIZE;
        boxes.add(MicroblockBoxUtil.transformPostMicroblock(box, slot).offset(x, y, z));
        return boxes;
    }

    @Override
    public void getCollisionBoxes(ObjectArrayList<Box> boxes) {
        Box box = bounds.copy();
        box.minX -= ((float)size * 0.5f) * PIXEL_SIZE;
        box.minY -= ((float)size * 0.5f) * PIXEL_SIZE;
        box.maxX += ((float)size * 0.5f) * PIXEL_SIZE;
        box.maxY += ((float)size * 0.5f) * PIXEL_SIZE;
        boxes.add(MicroblockBoxUtil.transformPostMicroblock(box, slot).offset(x, y, z));
    }
}
