package net.danygames2014.microblocks.multipart.placement;

import net.danygames2014.microblocks.util.MathHelper;
import net.danygames2014.nyalib.multipart.MultipartSlot;
import net.modificationstation.stationapi.api.util.math.Direction;
import net.modificationstation.stationapi.api.util.math.Vec3d;
import net.modificationstation.stationapi.api.util.math.Vec3i;

public class FacePlacementHelper extends PlacementHelper{
    @Override
    public MultipartSlot getSlot(int x, int y, int z, Direction face, Vec3d hit, double size) {

        Vec3d relativeHit = getRelativeHitVec(x, y, z, face, hit);

        int s1 = (face.ordinal() + 2) % 6;
        int s2 = (face.ordinal() + 4) % 6;

        Vec3i v1 = Direction.byId(s1).getVector();
        Vec3i v2 = Direction.byId(s2).getVector();

        Vec3d offset = relativeHit.add(-0.5, -0.5, -0.5);

        double u = MathHelper.scalarProject(offset, new Vec3d(v1.getX(), v1.getY(), v1.getZ()));
        double v = MathHelper.scalarProject(offset, new Vec3d(v2.getX(), v2.getY(), v2.getZ()));

        if(Math.abs(u) < size && Math.abs(v) < size){
            return MultipartSlot.fromOrdinal(face.ordinal()^1);
        }
        if(Math.abs(u) > Math.abs(v)){
            return u > 0 ? MultipartSlot.fromOrdinal(s1) : MultipartSlot.fromOrdinal(s1^1);
        }
        else {
            return v > 0 ? MultipartSlot.fromOrdinal(s2) : MultipartSlot.fromOrdinal(s2^1);
        }
    }

    @Override
    public MultipartSlot getOppositeSlot(MultipartSlot slot, Direction side) {
        return MultipartSlot.fromOrdinal(slot.ordinal()^1);
    }
}
