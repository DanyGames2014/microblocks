package net.danygames2014.microblocks.multipart;

import net.danygames2014.microblocks.multipart.model.FaceMicroblockModel;
import net.danygames2014.microblocks.multipart.model.MicroblockModel;
import net.minecraft.block.Block;

public class FaceMicroblockMultipartComponent extends MicroblockMultipartComponent{

    public static final FaceMicroblockModel MODEL = new FaceMicroblockModel();

    public FaceMicroblockMultipartComponent(){}

    public FaceMicroblockMultipartComponent(Block block, int meta, PlacementSlot slot, int size) {
        super(block, meta, slot, size);
    }

    @Override
    public int getMaxSize() {
        return 16;
    }

    @Override
    public MicroblockModel getMicroblockModel() {
        return MODEL;
    }
}
