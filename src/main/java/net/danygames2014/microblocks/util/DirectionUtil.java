package net.danygames2014.microblocks.util;

import net.danygames2014.microblocks.multipart.PlacementSlot;
import net.modificationstation.stationapi.api.util.math.Direction;

public class DirectionUtil {
    public static Direction faceSlotToDirection(PlacementSlot slot){
        return switch (slot) {
            case FACE_NEG_Y -> Direction.DOWN;
            case FACE_POS_Y -> Direction.UP;
            case FACE_NEG_Z -> Direction.NORTH;
            case FACE_POS_Z -> Direction.SOUTH;
            case FACE_NEG_X -> Direction.WEST; // West and east needs to be swapped because of what I assume is a bug with the BoxUtil class
            case FACE_POS_X -> Direction.EAST;
            default -> Direction.DOWN;
        };
    }
}
