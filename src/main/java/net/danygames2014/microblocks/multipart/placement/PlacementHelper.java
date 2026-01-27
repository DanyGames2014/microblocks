package net.danygames2014.microblocks.multipart.placement;

import net.danygames2014.microblocks.multipart.PlacementSlot;
import net.minecraft.util.math.BlockPos;
import net.modificationstation.stationapi.api.util.math.Direction;
import net.modificationstation.stationapi.api.util.math.Vec3d;

public abstract class PlacementHelper {
    public abstract PlacementSlot getSlot(int x, int y, int z, Direction face, Vec3d hit, double size);

    public BlockPos getPlacementPos(int x, int y, int z, Direction face){
        return new BlockPos(x + face.getOffsetX(), y + face.getOffsetY(), z + face.getOffsetZ());
    }

    public Vec3d getRelativeHitVec(int x, int y, int z, Direction face, Vec3d hit) {
        BlockPos placementPos = getPlacementPos(x, y, z, face);
        return hit.add(-placementPos.getX(), -placementPos.getY(), -placementPos.getZ());
    }
}
