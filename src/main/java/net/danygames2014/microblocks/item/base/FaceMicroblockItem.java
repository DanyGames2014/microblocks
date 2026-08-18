package net.danygames2014.microblocks.item.base;

import net.danygames2014.microblocks.multipart.FaceMicroblockMultipartComponent;;
import net.danygames2014.microblocks.multipart.model.MicroblockModel;
import net.danygames2014.microblocks.multipart.placement.FacePlacementHelper;
import net.danygames2014.microblocks.multipart.placement.PlacementHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.math.Direction;

public abstract class FaceMicroblockItem extends MicroblockItem {
    private static final FacePlacementHelper placementHelper = new FacePlacementHelper();

    public FaceMicroblockItem(Identifier identifier, Block block, int meta) {
        super(identifier, block, meta);
    }

    @Override
    public void renderGrid(PlayerEntity player, int blockX, int blockY, int blockZ, net.modificationstation.stationapi.api.util.math.Vec3d hit, Direction face, float tickDelta) {
        placementHelper.renderGrid(player, blockX, blockY, blockZ, hit, face, placementHelper.getGridCenterSize(), tickDelta);
    }

    @Override
    public MicroblockModel getMicroblockModel() {
        return FaceMicroblockMultipartComponent.MODEL;
    }

    @Override
    public PlacementHelper getPlacementHelper() {
        return placementHelper;
    }

    @Override
    public MicroblockFactory getMicroblockFactory() {
        return FaceMicroblockMultipartComponent::new;
    }
}
