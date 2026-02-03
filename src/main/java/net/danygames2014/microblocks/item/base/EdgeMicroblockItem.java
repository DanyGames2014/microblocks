package net.danygames2014.microblocks.item.base;

import net.danygames2014.microblocks.multipart.*;
import net.danygames2014.microblocks.multipart.model.MicroblockModel;
import net.danygames2014.microblocks.multipart.placement.EdgePlacementHelper;
import net.danygames2014.microblocks.multipart.placement.PlacementHelper;
import net.danygames2014.microblocks.multipart.placement.PostPlacementHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.math.Direction;
import org.lwjgl.input.Keyboard;

public abstract class EdgeMicroblockItem extends MicroblockItem {
    private static final EdgePlacementHelper placementHelper = new EdgePlacementHelper();
    private static final PostPlacementHelper postPlacementHelper = new PostPlacementHelper();
    
    public EdgeMicroblockItem(Identifier identifier, Block block) {
        super(identifier, block);
    }

    @Override
    public void renderGrid(PlayerEntity player, int blockX, int blockY, int blockZ, net.modificationstation.stationapi.api.util.math.Vec3d hit, Direction face, float tickDelta) {
        placementHelper.renderGrid(player, blockX, blockY, blockZ, hit, face, 1/4D, tickDelta);
    }

    protected boolean tryPlace(World world, int x, int y, int z, Direction dir, net.modificationstation.stationapi.api.util.math.Vec3d vec, int size, PlayerEntity player) {
        PlacementSlot slot = placementHelper.getSlot(x, y, z, dir, vec, 0.25D);

        if (player != null && player.isSneaking()) {
            slot = placementHelper.getOppositeSlot(slot, dir);
        }

        if (slot != PlacementSlot.INVALID) {
            if(placementHelper.canPlace(world, x, y, z, slot, size, EdgeMicroblockMultipartComponent.MODEL)) {
                world.addMultipartComponent(x, y, z, new EdgeMicroblockMultipartComponent(this.block, slot, size));
                return true;
            }
        } else {
            slot = postPlacementHelper.getSlot(x, y, z, dir, vec, 0.25D);
            if (placementHelper.canPlace(world, x, y, z, slot, size, PostMicroblockMultipartComponent.MODEL)) {
                world.addMultipartComponent(x, y, z, new PostMicroblockMultipartComponent(this.block, slot, size));
                return true;
            }
        }
        return false;
    }

    @Override
    public MicroblockModel getMicroblockModel() {
        return EdgeMicroblockMultipartComponent.MODEL;
    }

    @Override
    public PlacementHelper getPlacementHelper() {
        return placementHelper;
    }
}
