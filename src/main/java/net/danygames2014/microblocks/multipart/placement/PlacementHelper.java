package net.danygames2014.microblocks.multipart.placement;

import net.danygames2014.microblocks.multipart.PlacementSlot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.modificationstation.stationapi.api.util.math.Direction;

public abstract class PlacementHelper {
    public abstract PlacementSlot getSlot(int x, int y, int z, Direction face, Vec3d hit);

    public BlockPos getPlacementPos(int x, int y, int z, Direction face){
        return new BlockPos(x + face.getOffsetX(), y + face.getOffsetY(), z + face.getOffsetZ());
    }

    public Vec3d getRelativeHitVec(int x, int y, int z, Direction face, Vec3d hit) {
        BlockPos placementPos = getPlacementPos(x, y, z, face);
        return Vec3d.create(hit.x - placementPos.getX(), hit.y - placementPos.getY(), hit.z - placementPos.getZ());
    }
}
