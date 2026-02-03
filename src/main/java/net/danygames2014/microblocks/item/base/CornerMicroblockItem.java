package net.danygames2014.microblocks.item.base;

import net.danygames2014.microblocks.multipart.CornerMicroblockMultipartComponent;
import net.danygames2014.microblocks.multipart.FaceMicroblockMultipartComponent;
import net.danygames2014.microblocks.multipart.HollowMicroblockMultipartComponent;
import net.danygames2014.microblocks.multipart.PlacementSlot;
import net.danygames2014.microblocks.multipart.model.CornerMicroblockModel;
import net.danygames2014.microblocks.multipart.model.MicroblockModel;
import net.danygames2014.microblocks.multipart.placement.CornerPlacementHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.math.Direction;
import org.lwjgl.input.Keyboard;

public abstract class CornerMicroblockItem extends MicroblockItem {
    private static final CornerPlacementHelper placementHelper = new CornerPlacementHelper();
    
    public CornerMicroblockItem(Identifier identifier, Block block) {
        super(identifier, block);
    }

    @Override
    public void renderGrid(PlayerEntity player, int blockX, int blockY, int blockZ, net.modificationstation.stationapi.api.util.math.Vec3d hit, Direction face, float tickDelta) {
        placementHelper.renderGrid(player, blockX, blockY, blockZ, hit, face, 1/4D, tickDelta);
    }

    protected boolean tryPlace(World world, int x, int y, int z, Direction dir, net.modificationstation.stationapi.api.util.math.Vec3d vec, int size) {
        PlacementSlot slot = placementHelper.getSlot(x, y, z, dir, vec, 0.25D);

        if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
            slot = placementHelper.getOppositeSlot(slot, dir);
        }

        if (placementHelper.canPlace(world, x, y, z, slot, size, CornerMicroblockMultipartComponent.MODEL)) {
            world.addMultipartComponent(x, y, z, new CornerMicroblockMultipartComponent(this.block, slot, size));
            return true;
        }
        return false;
    }

    @Override
    public MicroblockModel getMicroblockModel() {
        return CornerMicroblockMultipartComponent.MODEL;
    }
}
