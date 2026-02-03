package net.danygames2014.microblocks.multipart;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.danygames2014.microblocks.multipart.model.MicroblockModel;
import net.danygames2014.microblocks.multipart.model.PostMicroblockModel;
import net.danygames2014.microblocks.util.DirectionUtil;
import net.danygames2014.microblocks.util.MicroblockBoxUtil;
import net.danygames2014.microblocks.util.ShrinkHelper;
import net.danygames2014.nyalib.multipart.MultipartComponent;
import net.danygames2014.nyalib.util.BoxUtil;
import net.minecraft.block.Block;
import net.minecraft.util.math.Box;
import net.modificationstation.stationapi.api.util.math.Direction;

public class PostMicroblockMultipartComponent extends MicroblockMultipartComponent{
    public static final PostMicroblockModel MODEL = new PostMicroblockModel();

    public PostMicroblockMultipartComponent(){}

    public PostMicroblockMultipartComponent(Block block, PlacementSlot slot, int size) {
        super(block, slot, size);
    }

    @Override
    public int getMaxSize() {
        return 8;
    }

    @Override
    public MicroblockModel getMicroblockModel() {
        return MODEL;
    }

    @Override
    public void refreshRenderState() {
        setRenderBounds(getMicroblockModel().getRenderBounds(slot, size, x, y, z).copy());
        renderMask = 0;
        for(MultipartComponent component : state.components) {
            if(component instanceof FaceMicroblockMultipartComponent face){
                Direction faceDir = Direction.byId(face.slot.ordinal());
                Direction.Axis postAxis = DirectionUtil.postSlotToAxis(slot);
                if(faceDir.getAxis() == postAxis){
                    shrinkFace(face);
                }
            }
        }
    }

    private void shrinkFace(MicroblockMultipartComponent other){
        this.setRenderBounds(ShrinkHelper.shrink(getRenderBounds(), other.getRenderBounds(), Direction.byId(other.slot.ordinal())));
    }

    @Override
    public boolean shouldShrink(MicroblockMultipartComponent other) {
        if(size != other.size) return size < other.size;
        if(isTransparent() != other.isTransparent()) return isTransparent();
        return other.slot.ordinal() < 6;
    }
}
