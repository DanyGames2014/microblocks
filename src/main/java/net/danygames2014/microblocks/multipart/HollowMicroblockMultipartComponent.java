package net.danygames2014.microblocks.multipart;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.danygames2014.microblocks.multipart.model.HollowMicroblockModel;
import net.danygames2014.microblocks.multipart.model.MicroblockModel;
import net.danygames2014.microblocks.util.DirectionUtil;
import net.danygames2014.microblocks.util.MicroblockBoxUtil;
import net.danygames2014.nyalib.util.BoxUtil;
import net.minecraft.block.Block;
import net.minecraft.util.math.Box;

public class HollowMicroblockMultipartComponent extends MicroblockMultipartComponent{

    public static final HollowMicroblockModel MODEL = new HollowMicroblockModel();

    public HollowMicroblockMultipartComponent(){}

    public HollowMicroblockMultipartComponent(Block block, PlacementSlot slot, int size) {
        super(block, slot, size);
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
