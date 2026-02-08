package net.danygames2014.microblocks.multipart;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.danygames2014.microblocks.item.MicroblockItemType;
import net.danygames2014.microblocks.multipart.model.MicroblockModel;
import net.danygames2014.microblocks.multipart.model.PostMicroblockModel;
import net.danygames2014.microblocks.util.DirectionUtil;
import net.danygames2014.microblocks.util.ShrinkHelper;
import net.danygames2014.nyalib.multipart.MultipartComponent;
import net.minecraft.block.Block;
import net.minecraft.util.math.Box;
import net.modificationstation.stationapi.api.util.math.Direction;

public class PostMicroblockMultipartComponent extends MicroblockMultipartComponent{
    public boolean useSecondaryRenderbounds = false;
    public double secondaryRenderBoundsMinX;
    public double secondaryRenderBoundsMinY;
    public double secondaryRenderBoundsMinZ;
    public double secondaryRenderBoundsMaxX;
    public double secondaryRenderBoundsMaxY;
    public double secondaryRenderBoundsMaxZ;

    public static final PostMicroblockModel MODEL = new PostMicroblockModel();

    public PostMicroblockMultipartComponent(){}

    public PostMicroblockMultipartComponent(Block block, int meta, PlacementSlot slot, int size) {
        super(block, meta, slot, size);
    }

    @Override
    public int getMaxSize() {
        return 8;
    }

    public void setSecondaryRenderBounds(Box box){
        setSecondaryRenderBounds(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ);
    }

    public void setSecondaryRenderBounds(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        this.secondaryRenderBoundsMinX = minX;
        this.secondaryRenderBoundsMinY = minY;
        this.secondaryRenderBoundsMinZ = minZ;
        this.secondaryRenderBoundsMaxX = maxX;
        this.secondaryRenderBoundsMaxY = maxY;
        this.secondaryRenderBoundsMaxZ = maxZ;
    }

    public Box getSecondaryRenderBounds(){
        return Box.create(secondaryRenderBoundsMinX, secondaryRenderBoundsMinY, secondaryRenderBoundsMinZ, secondaryRenderBoundsMaxX, secondaryRenderBoundsMaxY, secondaryRenderBoundsMaxZ);
    }

    @Override
    public MicroblockModel getMicroblockModel() {
        return MODEL;
    }

    @Override
    public void refreshRenderState() {
        setRenderBounds(getMicroblockModel().getRenderBounds(slot, size, x, y, z).copy());
        renderMask = 0;
        useSecondaryRenderbounds = false;
        for(MultipartComponent component : state.components) {
            if(component instanceof FaceMicroblockMultipartComponent face){
                Direction faceDir = Direction.byId(face.slot.ordinal());
                Direction.Axis postAxis = DirectionUtil.postSlotToAxis(slot);
                if(faceDir.getAxis() == postAxis){
                    shrinkFace(face);
                }
            }
        }
        int biggestSize = 0;
        for(MultipartComponent component : state.components) {
            if(component instanceof PostMicroblockMultipartComponent post){
                if(post == this){
                    continue;
                }
                if(!shouldShrink(post)){
                    continue;
                }
                if(post.getSize() > biggestSize){
                    biggestSize = post.getSize();
                    shrinkPost(post);
                }
            }
        }

    }

    private void shrinkFace(MicroblockMultipartComponent other){
        this.setRenderBounds(ShrinkHelper.shrink(getRenderBounds(), other.getRenderBounds(), Direction.byId(other.slot.ordinal())));
    }

    private void shrinkPost(PostMicroblockMultipartComponent other) {
        if(!useSecondaryRenderbounds){
            useSecondaryRenderbounds = true;
            setSecondaryRenderBounds(getRenderBounds());
        }

        double halfWidth = (other.getSize() / 2.0) * MicroblockModel.PIXEL_SIZE;

        double centerX, centerY, centerZ;

        switch (DirectionUtil.postSlotToAxis(slot)){
            case X -> {
                centerX = Math.floor(renderBoundsMinX) + 0.5;
                renderBoundsMaxX = centerX - halfWidth;
                secondaryRenderBoundsMinX = centerX + halfWidth;
            }
            case Y -> {
                centerY = Math.floor(renderBoundsMinY) + 0.5;
                renderBoundsMaxY = centerY - halfWidth;
                secondaryRenderBoundsMinY = centerY + halfWidth;
            }
            case Z -> {
                centerZ = Math.floor(renderBoundsMinZ) + 0.5;
                renderBoundsMaxZ = centerZ - halfWidth;
                secondaryRenderBoundsMinZ = centerZ + halfWidth;
            }
        }
    }

    @Override
    public boolean shouldShrink(MicroblockMultipartComponent other) {
        if(size != other.size) return size < other.size;
        if(isTransparent() != other.isTransparent()) return isTransparent();
        return other.slot.ordinal() < slot.ordinal();
    }

    @Override
    public ObjectArrayList<Box> getClippedBoxes(ObjectArrayList<Box> boxes) {
        ObjectArrayList<Box> clippedList = new ObjectArrayList<>();

        Box[] allRenderBounds;
        if(useSecondaryRenderbounds){
           allRenderBounds = new Box[]{ getRenderBounds(), getSecondaryRenderBounds() };
        } else {
            allRenderBounds = new Box[]{ getRenderBounds() };
        }

        for (Box box : boxes) {
            for (Box bounds : allRenderBounds) {
                if (!box.intersects(bounds)) {
                    continue;
                }

                double newMinX = Math.max(box.minX, bounds.minX);
                double newMaxX = Math.min(box.maxX, bounds.maxX);

                double newMinY = Math.max(box.minY, bounds.minY);
                double newMaxY = Math.min(box.maxY, bounds.maxY);

                double newMinZ = Math.max(box.minZ, bounds.minZ);
                double newMaxZ = Math.min(box.maxZ, bounds.maxZ);

                clippedList.add(Box.create(newMinX, newMinY, newMinZ, newMaxX, newMaxY, newMaxZ));
            }
        }

        return clippedList;
    }
}
