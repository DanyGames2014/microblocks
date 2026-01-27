package net.danygames2014.microblocks.multipart.placement;

import net.danygames2014.microblocks.multipart.PlacementSlot;
import net.danygames2014.microblocks.util.MathHelper;
import net.modificationstation.stationapi.api.util.math.Direction;
import net.modificationstation.stationapi.api.util.math.Vec3d;
import net.modificationstation.stationapi.api.util.math.Vec3i;

public class EdgePlacementHelper extends PlacementHelper{
    @Override
    public PlacementSlot getSlot(int x, int y, int z, Direction face, Vec3d hit, double size) {
        Vec3d relativeHit = getRelativeHitVec(x, y, z, face, hit);

        int s1 = (face.ordinal() + 2) % 6;
        int s2 = (face.ordinal() + 4) % 6;

        Vec3i v1 = Direction.byId(s1).getVector();
        Vec3i v2 = Direction.byId(s2).getVector();

        Vec3d offset = relativeHit.add(-0.5, -0.5, -0.5);

        double u = MathHelper.scalarProject(offset, new Vec3d(v1.getX(), v1.getY(), v1.getZ()));
        double v = MathHelper.scalarProject(offset, new Vec3d(v2.getX(), v2.getY(), v2.getZ()));

        if(Math.abs(u) < size && Math.abs(v) < size){
            return PlacementSlot.INVALID;
        }
        int b = face.ordinal()&1;
        if(Math.abs(u) > Math.abs(v))
        {
            if(u < 0) {
                b^=1;
            }
            return PlacementSlot.fromOrdinal(14+((s2&6)<<1 | b<<1 | face.ordinal()&1^1));
        }
        else {
            if(v < 0) {
                b^=1;
            }
            return PlacementSlot.fromOrdinal(14+((s1&6)<<1 | (face.ordinal()&1^1)<<1 | b));
        }
    }
}
