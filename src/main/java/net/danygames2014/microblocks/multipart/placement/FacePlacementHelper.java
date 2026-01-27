package net.danygames2014.microblocks.multipart.placement;

import net.danygames2014.microblocks.multipart.PlacementSlot;
import net.minecraft.util.math.Vec3d;
import net.modificationstation.stationapi.api.util.math.Direction;

public class FacePlacementHelper extends PlacementHelper{
    private static final double MIN = 0.20;
    private static final double MAX = 0.80;

    @Override
    public PlacementSlot getSlot(int x, int y, int z, Direction face, Vec3d hit) {

        System.out.println(face);
        Vec3d relativeHit = getRelativeHitVec(x, y, z, face, hit);

        return switch (face) {
            case DOWN -> {
                if (inCenter(relativeHit.x, relativeHit.z)) yield PlacementSlot.FACE_NEG_Y;
                if (low(relativeHit.z)) yield PlacementSlot.FACE_POS_Z;
                if (high(relativeHit.z)) yield PlacementSlot.FACE_NEG_Z;
                if (low(relativeHit.x)) yield PlacementSlot.FACE_POS_X;
                if (high(relativeHit.x)) yield PlacementSlot.FACE_NEG_X;
                yield null;
            }
            case UP -> {
                if (inCenter(relativeHit.x, relativeHit.z)) yield PlacementSlot.FACE_POS_Y;
                if (low(relativeHit.z)) yield PlacementSlot.FACE_POS_Z;
                if (high(relativeHit.z)) yield PlacementSlot.FACE_NEG_Z;
                if (low(relativeHit.x)) yield PlacementSlot.FACE_POS_X;
                if (high(relativeHit.x)) yield PlacementSlot.FACE_NEG_X;
                yield null;
            }
            case NORTH -> {
                if (inCenter(relativeHit.z, relativeHit.y)) yield PlacementSlot.FACE_NEG_X;
                if (low(relativeHit.y)) yield PlacementSlot.FACE_POS_Y;
                if (high(relativeHit.y)) yield PlacementSlot.FACE_NEG_Y;
                if (low(relativeHit.z)) yield PlacementSlot.FACE_POS_Z;
                if (high(relativeHit.z)) yield PlacementSlot.FACE_NEG_Z;
                yield null;
            }
            case SOUTH -> {
                if (inCenter(relativeHit.z, relativeHit.y)) yield PlacementSlot.FACE_POS_X;
                if (low(relativeHit.y)) yield PlacementSlot.FACE_POS_Y;
                if (high(relativeHit.y)) yield PlacementSlot.FACE_NEG_Y;
                if (low(relativeHit.z)) yield PlacementSlot.FACE_POS_Z;
                if (high(relativeHit.z)) yield PlacementSlot.FACE_NEG_Z;
                yield null;
            }
            case EAST -> {
                if (inCenter(relativeHit.x, relativeHit.y)) yield PlacementSlot.FACE_NEG_Z;
                if (low(relativeHit.y)) yield PlacementSlot.FACE_POS_Y;
                if (high(relativeHit.y)) yield PlacementSlot.FACE_NEG_Y;
                if (low(relativeHit.x)) yield PlacementSlot.FACE_POS_X;
                if (high(relativeHit.x)) yield PlacementSlot.FACE_NEG_X;
                yield null;
            }
            case WEST -> {
                if (inCenter(relativeHit.x, relativeHit.y)) yield PlacementSlot.FACE_POS_Z;
                if (low(relativeHit.y)) yield PlacementSlot.FACE_POS_Y;
                if (high(relativeHit.y)) yield PlacementSlot.FACE_NEG_Y;
                if (low(relativeHit.x)) yield PlacementSlot.FACE_POS_X;
                if (high(relativeHit.x)) yield PlacementSlot.FACE_NEG_X;
                yield null;
            }
        };
    }

    private boolean inCenter(double a, double b) {
        return a > MIN && a < MAX && b > MIN && b < MAX;
    }

    private static boolean low(double v) { return v < MIN; }
    private static boolean high(double v) { return v > MAX; }
}
