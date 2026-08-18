package net.danygames2014.microblocks.util;

import net.danygames2014.nyalib.multipart.MultipartSlot;
import net.modificationstation.stationapi.api.util.math.Direction;

public class DirectionUtil {
    // All these directions are wrong but BoxUtil is broken so it has to be like this
    public static Direction faceSlotToDirection(MultipartSlot slot){
        return switch (slot) {
            case FACE_NEG_Y -> Direction.UP;
            case FACE_POS_Y -> Direction.DOWN;
            case FACE_NEG_Z -> Direction.SOUTH;
            case FACE_POS_Z -> Direction.NORTH;
            case FACE_NEG_X -> Direction.EAST;
            case FACE_POS_X -> Direction.WEST;
            default -> Direction.DOWN;
        };
    }
}
