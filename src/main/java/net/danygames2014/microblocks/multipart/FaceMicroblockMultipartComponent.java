package net.danygames2014.microblocks.multipart;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.danygames2014.microblocks.multipart.model.FaceMicroblockModel;
import net.danygames2014.microblocks.multipart.model.MicroblockModel;
import net.danygames2014.microblocks.util.DirectionUtil;
import net.danygames2014.nyalib.util.BoxUtil;
import net.minecraft.block.Block;
import net.minecraft.util.math.Box;
import net.modificationstation.stationapi.api.util.math.Direction;

public class FaceMicroblockMultipartComponent extends MicroblockMultipartComponent{

    private static final FaceMicroblockModel MODEL = new FaceMicroblockModel();

    public FaceMicroblockMultipartComponent(){}

    public FaceMicroblockMultipartComponent(Block block, PlacementSlot slot, int size) {
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
