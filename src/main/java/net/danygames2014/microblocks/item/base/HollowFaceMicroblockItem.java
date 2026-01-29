package net.danygames2014.microblocks.item.base;

import net.danygames2014.microblocks.multipart.CornerMicroblockMultipartComponent;
import net.danygames2014.microblocks.multipart.HollowMicroblockMultipartComponent;
import net.danygames2014.microblocks.multipart.PlacementSlot;
import net.danygames2014.microblocks.multipart.placement.FacePlacementHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.math.Direction;

public abstract class HollowFaceMicroblockItem extends MicroblockItem{
    private static final FacePlacementHelper placementHelper = new FacePlacementHelper();

    public HollowFaceMicroblockItem(Identifier identifier, Block block) {
        super(identifier, block);
    }

    @Override
    public void renderGrid(PlayerEntity player, int blockX, int blockY, int blockZ, net.modificationstation.stationapi.api.util.math.Vec3d hit, Direction face, float tickDelta) {
        placementHelper.renderGrid(player, blockX, blockY, blockZ, hit, face, 1/4D, tickDelta);
    }

    @Override
    public boolean useOnBlock(ItemStack stack, PlayerEntity player, World world, int x, int y, int z, int side, Vec3d hitVec) {
        int size = getSize();
        PlacementSlot slot = placementHelper.getSlot(x, y, z, Direction.byId(side), new net.modificationstation.stationapi.api.util.math.Vec3d(hitVec.x, hitVec.y, hitVec.z), 1/4D);
        System.out.println(slot.ordinal());
        BlockPos placementPos = placementHelper.getPlacementPos(x, y, z, Direction.byId(side));


        if(!placementHelper.canPlace(world, placementPos.getX(), placementPos.getY(), placementPos.getZ(), slot, size, HollowMicroblockMultipartComponent.MODEL)){
            return false;
        }

        world.addMultipartComponent(placementPos.getX(), placementPos.getY(), placementPos.getZ(), new HollowMicroblockMultipartComponent(this.block, slot, size));
        return true;
    }
}
