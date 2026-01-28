package net.danygames2014.microblocks.multipart;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.danygames2014.microblocks.multipart.model.EdgeMicroblockModel;
import net.danygames2014.microblocks.multipart.model.MicroblockModel;
import net.danygames2014.microblocks.util.MicroblockBoxUtil;
import net.minecraft.block.Block;
import net.minecraft.util.math.Box;

public class EdgeMicroblockMultipartComponent extends MicroblockMultipartComponent{
    private static final EdgeMicroblockModel MODEL = new EdgeMicroblockModel();

    public EdgeMicroblockMultipartComponent(){}

    public EdgeMicroblockMultipartComponent(Block block, PlacementSlot slot, int size) {
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
}
