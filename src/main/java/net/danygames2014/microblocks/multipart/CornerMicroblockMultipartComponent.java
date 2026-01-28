package net.danygames2014.microblocks.multipart;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.danygames2014.microblocks.multipart.model.CornerMicroblockModel;
import net.danygames2014.microblocks.multipart.model.MicroblockModel;
import net.danygames2014.microblocks.util.MicroblockBoxUtil;
import net.minecraft.block.Block;
import net.minecraft.util.math.Box;

public class CornerMicroblockMultipartComponent extends MicroblockMultipartComponent{
    private static final CornerMicroblockModel MODEL = new CornerMicroblockModel();

    public CornerMicroblockMultipartComponent(){}

    public CornerMicroblockMultipartComponent(Block block, PlacementSlot slot, int size) {
        super(block, slot, size);
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
