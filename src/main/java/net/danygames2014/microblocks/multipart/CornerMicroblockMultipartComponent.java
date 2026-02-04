package net.danygames2014.microblocks.multipart;

import net.danygames2014.microblocks.multipart.model.CornerMicroblockModel;
import net.danygames2014.microblocks.multipart.model.MicroblockModel;
import net.minecraft.block.Block;

public class CornerMicroblockMultipartComponent extends MicroblockMultipartComponent{
    public static final CornerMicroblockModel MODEL = new CornerMicroblockModel();

    public CornerMicroblockMultipartComponent(){}

    public CornerMicroblockMultipartComponent(Block block, int meta, PlacementSlot slot, int size) {
        super(block, meta, slot, size);
    }

    @Override
    public int getMaxSize() {
        return 14;
    }

    @Override
    public MicroblockModel getMicroblockModel() {
        return MODEL;
    }
}
