package net.danygames2014.microblocks.multipart.model;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.danygames2014.microblocks.multipart.PlacementSlot;
import net.minecraft.util.math.Box;
import org.jetbrains.annotations.Nullable;


public abstract class MicroblockModel {
    protected static double PIXEL_SIZE = 1D / 16D;
    public abstract ObjectArrayList<Box> getBoxesForSlot(@Nullable PlacementSlot slot, int size, double offsetX, double offsetY, double offsetZ);
    public abstract Box getRenderBounds(PlacementSlot slot, int size, double offsetX, double offsetY, double offsetZ);
}
