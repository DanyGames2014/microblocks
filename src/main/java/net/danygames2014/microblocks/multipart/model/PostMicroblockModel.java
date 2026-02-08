package net.danygames2014.microblocks.multipart.model;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.danygames2014.microblocks.item.MicroblockItemType;
import net.danygames2014.microblocks.multipart.PlacementSlot;
import net.danygames2014.microblocks.util.MicroblockBoxUtil;
import net.minecraft.util.math.Box;

public class PostMicroblockModel extends MicroblockModel{
    public Box bounds = Box.create(0.5D, 0.5D, 0D, 0.5D, 0.5D, 1D);

    @Override
    public ObjectArrayList<Box> getBoxesForSlot(PlacementSlot slot, int size, double offsetX, double offsetY, double offsetZ) {
        if(slot == null){
            slot = PlacementSlot.POST_Y;
        }
        ObjectArrayList<Box> boxes = new ObjectArrayList<>();
        Box box = bounds.copy();
        box.minX -= ((float)size * 0.5f) * PIXEL_SIZE;
        box.minY -= ((float)size * 0.5f) * PIXEL_SIZE;
        box.maxX += ((float)size * 0.5f) * PIXEL_SIZE;
        box.maxY += ((float)size * 0.5f) * PIXEL_SIZE;
        boxes.add(MicroblockBoxUtil.transformPostMicroblock(box, slot).offset(offsetX, offsetY, offsetZ));
        return boxes;
    }

    @Override
    public Box getRenderBounds(PlacementSlot slot, int size, double offsetX, double offsetY, double offsetZ) {
        if(slot == null){
            slot = PlacementSlot.POST_Y;
        }
        return getBoxesForSlot(slot, size, offsetX, offsetY, offsetZ).get(0);
    }

    @Override
    public boolean canOverlap(MicroblockItemType type, PlacementSlot slot) {
        return type.isFace() || slot.ordinal() > 25;
    }
}
