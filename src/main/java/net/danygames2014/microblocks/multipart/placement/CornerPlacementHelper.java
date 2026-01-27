package net.danygames2014.microblocks.multipart.placement;

import net.danygames2014.microblocks.multipart.PlacementSlot;
import net.minecraft.util.math.Vec3d;
import net.modificationstation.stationapi.api.util.math.Direction;

public class CornerPlacementHelper extends PlacementHelper {

    @Override
    public PlacementSlot getSlot(int x, int y, int z, Direction face, Vec3d hit) {
        return PlacementSlot.CORNER_BOT_NEG_X_NEG_Z;
    }
}
