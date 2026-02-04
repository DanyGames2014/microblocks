package net.danygames2014.microblocks.multipart;

import net.danygames2014.microblocks.multipart.model.EdgeMicroblockModel;
import net.danygames2014.microblocks.multipart.model.MicroblockModel;
import net.minecraft.block.Block;

public class EdgeMicroblockMultipartComponent extends MicroblockMultipartComponent{
    public static final EdgeMicroblockModel MODEL = new EdgeMicroblockModel();

    public EdgeMicroblockMultipartComponent(){}

    public EdgeMicroblockMultipartComponent(Block block, int meta, PlacementSlot slot, int size) {
        super(block, meta, slot, size);
    }

    @Override
    public int getMaxSize() {
        return 8;
    }

    @Override
    public MicroblockModel getMicroblockModel() {
        return MODEL;
    }
}
