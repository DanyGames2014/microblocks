package net.danygames2014.microblocks.item.base;

import net.danygames2014.microblocks.multipart.HollowMicroblockMultipartComponent;
import net.danygames2014.microblocks.multipart.PlacementSlot;
import net.danygames2014.microblocks.multipart.model.MicroblockModel;
import net.danygames2014.microblocks.multipart.placement.FacePlacementHelper;
import net.danygames2014.microblocks.multipart.placement.HollowPlacementHelper;
import net.danygames2014.microblocks.multipart.placement.PlacementHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.math.Direction;

public abstract class HollowFaceMicroblockItem extends MicroblockItem{
    private static final HollowPlacementHelper placementHelper = new HollowPlacementHelper();

    public HollowFaceMicroblockItem(Identifier identifier, Block block, int meta) {
        super(identifier, block, meta);
    }

    @Override
    public void renderGrid(PlayerEntity player, int blockX, int blockY, int blockZ, net.modificationstation.stationapi.api.util.math.Vec3d hit, Direction face, float tickDelta) {
        placementHelper.renderGrid(player, blockX, blockY, blockZ, hit, face, placementHelper.getGridCenterSize(), tickDelta);
    }

    protected boolean tryPlace(World world, int x, int y, int z, Direction dir, net.modificationstation.stationapi.api.util.math.Vec3d vec, int size, PlayerEntity player) {
        PlacementSlot slot = placementHelper.getSlot(x, y, z, dir, vec, placementHelper.getGridCenterSize());

        if (player != null && player.isSneaking()) {
            slot = placementHelper.getOppositeSlot(slot, dir);
        }

        if (placementHelper.canPlace(world, x, y, z, getType(), slot, size, HollowMicroblockMultipartComponent.MODEL)) {
            world.addMultipartComponent(x, y, z, new HollowMicroblockMultipartComponent(this.block, meta, slot, size));
            return true;
        }
        return false;
    }

    @Override
    public MicroblockModel getMicroblockModel() {
        return HollowMicroblockMultipartComponent.MODEL;
    }

    @Override
    public PlacementHelper getPlacementHelper() {
        return placementHelper;
    }
}
