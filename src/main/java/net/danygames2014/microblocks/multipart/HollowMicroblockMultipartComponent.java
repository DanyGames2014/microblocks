package net.danygames2014.microblocks.multipart;

import net.danygames2014.microblocks.multipart.model.HollowMicroblockModel;
import net.danygames2014.microblocks.multipart.model.MicroblockModel;
import net.minecraft.block.Block;

public class HollowMicroblockMultipartComponent extends MicroblockMultipartComponent{

    public static final HollowMicroblockModel MODEL = new HollowMicroblockModel();

    public HollowMicroblockMultipartComponent(){}

    public HollowMicroblockMultipartComponent(Block block, int meta, PlacementSlot slot, int size) {
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
