package net.danygames2014.microblocks.multipart.placement;

import net.danygames2014.microblocks.multipart.PlacementSlot;
import net.modificationstation.stationapi.api.util.math.Direction;
import net.modificationstation.stationapi.api.util.math.Vec3d;

public class PostPlacementHelper extends PlacementHelper{
    @Override
    public PlacementSlot getSlot(int x, int y, int z, Direction face, Vec3d hit, double size) {
        return switch (face.getAxis()) {
            case X -> PlacementSlot.POST_X;
            case Y -> PlacementSlot.POST_Y;
            case Z -> PlacementSlot.POST_Z;
        };
    }

    @Override
    public PlacementSlot getOppositeSlot(PlacementSlot slot, Direction side) {
        return slot;
    }
}
