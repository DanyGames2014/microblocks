package net.danygames2014.microblocks.item;

import net.danygames2014.microblocks.multipart.EdgeMicroblockMultipartComponent;
import net.danygames2014.microblocks.multipart.PlacementSlot;
import net.danygames2014.microblocks.multipart.PostMicroblockMultipartComponent;
import net.danygames2014.microblocks.multipart.placement.CornerPlacementHelper;
import net.danygames2014.microblocks.multipart.placement.EdgePlacementHelper;
import net.danygames2014.microblocks.multipart.placement.PostPlacementHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.math.Direction;

public class EdgeMicroblockItem extends MicroblockItem {
    private static final EdgePlacementHelper placementHelper = new EdgePlacementHelper();
    private static final PostPlacementHelper postPlacementHelper = new PostPlacementHelper();
    public EdgeMicroblockItem(Identifier identifier) {
        super(identifier);
    }

    @Override
    public boolean useOnBlock(ItemStack stack, PlayerEntity player, World world, int x, int y, int z, int side, Vec3d hitVec) {
        Block block = getBlock(stack);
        int size = 4; //getSize(stack);
        PlacementSlot slot = placementHelper.getSlot(x, y, z, Direction.byId(side), new net.modificationstation.stationapi.api.util.math.Vec3d(hitVec.x, hitVec.y, hitVec.z), 1/4D);
        BlockPos placementPos = placementHelper.getPlacementPos(x, y, z, Direction.byId(side));

        System.out.println(slot);
        System.out.println(slot.ordinal());

        if(slot != PlacementSlot.INVALID){
            world.addMultipartComponent(placementPos.getX(), placementPos.getY(), placementPos.getZ(), new EdgeMicroblockMultipartComponent(block, slot, size));
            return true;
        } else if(size > 3){
            slot = postPlacementHelper.getSlot(x, y, z, Direction.byId(side), new net.modificationstation.stationapi.api.util.math.Vec3d(hitVec.x, hitVec.y, hitVec.z), 1/4D);
            world.addMultipartComponent(placementPos.getX(), placementPos.getY(), placementPos.getZ(), new PostMicroblockMultipartComponent(block, slot, size));
            return true;
        }
        return false;
    }
}
