package net.danygames2014.microblocks.item.base;

import net.danygames2014.microblocks.item.MicroblockItemType;
import net.danygames2014.microblocks.multipart.CornerMicroblockMultipartComponent;
import net.danygames2014.microblocks.multipart.PlacementSlot;
import net.danygames2014.microblocks.multipart.model.MicroblockModel;
import net.danygames2014.microblocks.multipart.placement.CornerPlacementHelper;
import net.danygames2014.microblocks.multipart.placement.PlacementHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.math.Direction;

public abstract class CornerMicroblockItem extends MicroblockItem {
    private static final CornerPlacementHelper placementHelper = new CornerPlacementHelper();
    
    public CornerMicroblockItem(Identifier identifier, Block block, int meta) {
        super(identifier, block, meta);
    }

    @Override
    public void renderGrid(PlayerEntity player, int blockX, int blockY, int blockZ, net.modificationstation.stationapi.api.util.math.Vec3d hit, Direction face, float tickDelta) {
        placementHelper.renderGrid(player, blockX, blockY, blockZ, hit, face, placementHelper.getGridCenterSize(), tickDelta);
    }

    protected boolean tryPlace(World world, int x, int y, int z, Direction dir, net.modificationstation.stationapi.api.util.math.Vec3d vec, int size, PlayerEntity player) {
        PlacementSlot slot = placementHelper.getSlot(x, y, z, dir, vec, placementHelper.getGridCenterSize());

        boolean sneaking = player != null && player.isSneaking();

        if(sneaking){
            slot = placementHelper.getOppositeSlot(slot, dir);
        }

        if (placementHelper.canPlace(world, x, y, z, getType(), slot, size, CornerMicroblockMultipartComponent.MODEL)) {
            world.addMultipartComponent(x, y, z, new CornerMicroblockMultipartComponent(this.block, meta, slot, size));
            return true;
        }

        if(!sneaking) {
            PlacementSlot oppositeSlot = placementHelper.getOppositeSlot(slot, dir);
            if (placementHelper.canPlace(world, x, y, z, getType(), oppositeSlot, size, CornerMicroblockMultipartComponent.MODEL)) {
                world.addMultipartComponent(x, y, z, new CornerMicroblockMultipartComponent(this.block, meta, oppositeSlot, size));
                return true;
            }
        }
        return false;
    }

    @Override
    public MicroblockModel getMicroblockModel() {
        return CornerMicroblockMultipartComponent.MODEL;
    }

    @Override
    public PlacementHelper getPlacementHelper() {
        return placementHelper;
    }
}
