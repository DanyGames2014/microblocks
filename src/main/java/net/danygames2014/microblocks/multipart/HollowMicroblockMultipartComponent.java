package net.danygames2014.microblocks.multipart;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.util.math.Box;

public class HollowMicroblockMultipartComponent extends MicroblockMultipartComponent{
    @Override
    public int getMaxSize() {
        return 16;
    }

    @Override
    public ObjectArrayList<Box> getBoundingBoxes() {
        return null;
    }
}
