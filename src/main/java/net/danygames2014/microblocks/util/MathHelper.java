package net.danygames2014.microblocks.util;


import net.minecraft.util.math.BlockPos;
import net.modificationstation.stationapi.api.util.math.*;

public class MathHelper {
    public static double scalarProject(Vec3d vec, Vec3d other){
        double length = other.length();
        return length == 0.0D ? 0.0D : vec.dotProduct(other) / length;
    }

    public static Vec3d rotate(Vec3d v, Quaternion q) {
        double qx = q.getX();
        double qy = q.getY();
        double qz = q.getZ();
        double qw = q.getW();

        double tx = 2 * (qy * v.getZ() - qz * v.getY());
        double ty = 2 * (qz * v.getX() - qx * v.getZ());
        double tz = 2 * (qx * v.getY() - qy * v.getX());

        return new Vec3d(
            v.x + qw * tx + (qy * tz - qz * ty),
            v.y + qw * ty + (qz * tx - qx * tz),
            v.z + qw * tz + (qx * ty - qy * tx)
        );
    }

    public static Quaternion getQuaternionForSide(Direction side) {
        return switch (side) {
            case DOWN -> Quaternion.IDENTITY;
            case UP -> Vec3f.POSITIVE_X.getDegreesQuaternion(180.0F);
            case EAST -> Vec3f.POSITIVE_X.getDegreesQuaternion(90.0F);
            case WEST -> Vec3f.POSITIVE_X.getDegreesQuaternion(-90.0F);
            case NORTH -> Vec3f.POSITIVE_Z.getDegreesQuaternion(90.0F);
            case SOUTH -> Vec3f.POSITIVE_Z.getDegreesQuaternion(-90.0F);
        };
    }

    public static double getHitDepth(Vec3d hit, Direction side) {
        Vec3i sideVec = side.getVector();
        return scalarProject(hit, new Vec3d(sideVec.getX(), sideVec.getY(), sideVec.getZ())) + (side.ordinal()%2^1);
    }

    public static BlockPos getPlacementPos(int x, int y, int z, Direction face){
        return new BlockPos(x + face.getOffsetX(), y + face.getOffsetY(), z + face.getOffsetZ());
    }
    
    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}
