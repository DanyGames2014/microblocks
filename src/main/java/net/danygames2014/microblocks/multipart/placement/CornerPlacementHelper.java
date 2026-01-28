package net.danygames2014.microblocks.multipart.placement;

import net.danygames2014.microblocks.client.render.grid.CornerGridRenderer;
import net.danygames2014.microblocks.client.render.grid.GridRenderer;
import net.danygames2014.microblocks.multipart.PlacementSlot;
import net.danygames2014.microblocks.util.MathHelper;
import net.modificationstation.stationapi.api.util.math.Direction;
import net.modificationstation.stationapi.api.util.math.Vec3d;
import net.modificationstation.stationapi.api.util.math.Vec3i;

public class CornerPlacementHelper extends PlacementHelper {

    @Override
    public PlacementSlot getSlot(int x, int y, int z, Direction face, Vec3d hit, double size) {
        System.out.println(face);
        Vec3d relativeHit = getRelativeHitVec(x, y, z, face, hit);

        int s1 = ((face.ordinal() & 6) + 3) % 6;
        int s2 = ((face.ordinal() & 6) + 5) % 6;

        Vec3i v1 = Direction.byId(s1).getVector();
        Vec3i v2 = Direction.byId(s2).getVector();

        Vec3d offset = relativeHit.add(-0.5, -0.5, -0.5);

        double u = MathHelper.scalarProject(offset, new Vec3d(v1.getX(), v1.getY(), v1.getZ()));
        double v = MathHelper.scalarProject(offset, new Vec3d(v2.getX(), v2.getY(), v2.getZ()));

        int bu = (u >= 0) ? 1 : 0;
        int bv = (v >= 0) ? 1 : 0;
        int bw = (face.ordinal() & 1) ^ 1;

        return PlacementSlot.fromOrdinal(6 + (bw<<(face.ordinal()>>1)| bu<<(s1>>1)| bv<<(s2>>1)));
    }

    @Override
    protected GridRenderer getGridRenderer() {
        return CornerGridRenderer.INSTANCE;
    }
}
