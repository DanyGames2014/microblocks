package net.danygames2014.microblocks.util;


import net.modificationstation.stationapi.api.util.math.Vec3d;

public class MathHelper {
    public static double scalarProject(Vec3d vec, Vec3d other){
        double length = other.length();
        return length == 0.0D ? 0.0D : vec.dotProduct(other) / length;
    }
}
