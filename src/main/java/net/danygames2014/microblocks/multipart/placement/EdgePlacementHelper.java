package net.danygames2014.microblocks.multipart.placement;

import net.danygames2014.microblocks.client.render.grid.EdgeGridRenderer;
import net.danygames2014.microblocks.client.render.grid.GridRenderer;
import net.danygames2014.microblocks.multipart.PlacementSlot;
import net.danygames2014.microblocks.util.MathHelper;
import net.modificationstation.stationapi.api.util.math.Direction;
import net.modificationstation.stationapi.api.util.math.Vec3d;
import net.modificationstation.stationapi.api.util.math.Vec3i;

public class EdgePlacementHelper extends PlacementHelper{
    private int[][] EDGE_OPPOSITE_MAP = {
            //0  1  2  3  4  5
            { 0, 0, 1, 1, 2, 2 },// 0
            { 1, 1, 0, 0, 3, 3 },// 1
            { 2, 2, 3, 3, 0, 0 },// 2
            { 3, 3, 2, 2, 1, 1 },// 3

            { 6, 6, 4, 4, 5, 5 },// 4
            { 7, 7, 5, 5, 4, 4 },// 5
            { 4, 4, 6, 6, 7, 7 },// 6
            { 5, 5, 7, 7, 6, 6 },// 7

            {10, 9, 8, 10, 8, 8 },// 8
            {8, 11, 9, 11, 9, 9 },// 9
            {11, 11, 8, 11, 10, 10 },// 10
            {10, 9, 9, 10, 11, 11 }//11
    };

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

        if (Math.abs(u) > size && Math.abs(v) > size) {
            int uSign = u > 0 ? 1 : 0;
            int vSign = v > 0 ? 1 : 0;
            return getCornerSlot(face, uSign, vSign);
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

    @Override
    public PlacementSlot getOppositeSlot(PlacementSlot slot, Direction side) {

        if(slot == PlacementSlot.INVALID || slot.ordinal() < 14){
            return slot;
        }
        int edgeIndex = slot.ordinal()-14;
        if (edgeIndex >= EDGE_OPPOSITE_MAP.length){
            return slot;
        }
        return PlacementSlot.fromOrdinal(14 + EDGE_OPPOSITE_MAP[edgeIndex][side.ordinal()]);
    }

    @Override
    protected GridRenderer getGridRenderer() {
        return EdgeGridRenderer.INSTANCE;
    }

    private PlacementSlot getCornerSlot(Direction face, int uSign, int vSign){
        if(face.getDirection() == Direction.AxisDirection.NEGATIVE){
            uSign = uSign == 0 ? 1 : 0;
            vSign = vSign == 0 ? 1 : 0;
        }

        switch (face.getAxis()){
            case X -> {
                if(uSign == 0 && vSign == 0){
                    return PlacementSlot.EDGE_BOT_NEG_Z;
                }
                if(uSign == 0 && vSign == 1){
                    return PlacementSlot.EDGE_BOT_POS_Z;
                }
                if(uSign == 1 && vSign == 0){
                    return PlacementSlot.EDGE_TOP_NEG_Z;
                }
                if(uSign == 1 && vSign == 1){
                    return PlacementSlot.EDGE_TOP_POS_Z;
                }
            }
            case Y -> {
                if(uSign == 0 && vSign == 0){
                    return PlacementSlot.EDGE_MID_NEG_X_NEG_Z;
                }
                if(uSign == 0 && vSign == 1){
                    return PlacementSlot.EDGE_MID_POS_X_NEG_Z;
                }
                if(uSign == 1 && vSign == 0){
                    return PlacementSlot.EDGE_MID_NEG_X_POS_Z;
                }
                if(uSign == 1 && vSign == 1){
                    return PlacementSlot.EDGE_MID_POS_X_POS_Z;
                }
            }
            case Z -> {
                if(uSign == 0 && vSign == 0){
                    return PlacementSlot.EDGE_BOT_NEG_X;
                }
                if(uSign == 0 && vSign == 1){
                    return PlacementSlot.EDGE_TOP_NEG_X;
                }
                if(uSign == 1 && vSign == 0){
                    return PlacementSlot.EDGE_BOT_POS_X;
                }
                if(uSign == 1 && vSign == 1){
                    return PlacementSlot.EDGE_TOP_POS_X;
                }
            }
        }
        return PlacementSlot.EDGE_BOT_NEG_X;
    }
}
