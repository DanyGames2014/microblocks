package net.danygames2014.microblocks.item;

import net.danygames2014.microblocks.multipart.PlacementSlot;
import net.danygames2014.microblocks.multipart.placement.CornerPlacementHelper;
import net.danygames2014.microblocks.multipart.placement.EdgePlacementHelper;
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
    public EdgeMicroblockItem(Identifier identifier) {
        super(identifier);
    }

    @Override
    public boolean useOnBlock(ItemStack stack, PlayerEntity player, World world, int x, int y, int z, int side, Vec3d hitVec) {
        Block block = getBlock(stack);
        int size = getSize(stack);
        PlacementSlot slot = placementHelper.getSlot(x, y, z, Direction.byId(side), new net.modificationstation.stationapi.api.util.math.Vec3d(hitVec.x, hitVec.y, hitVec.z), 1/4D);
        BlockPos placementPos = placementHelper.getPlacementPos(x, y, z, Direction.byId(side));

        System.out.println(slot);
        System.out.println(slot.ordinal());

//        world.addMultipartComponent(placementPos.getX(), placementPos.getY(), placementPos.getZ(), new CornerMicroblockMultipartComponent(block, slot, size));
        return true;
    }
}
