package net.danygames2014.microblocks.item.base;

import net.danygames2014.microblocks.multipart.CornerMicroblockMultipartComponent;
import net.danygames2014.microblocks.multipart.FaceMicroblockMultipartComponent;
import net.danygames2014.microblocks.multipart.PlacementSlot;
import net.danygames2014.microblocks.multipart.placement.FacePlacementHelper;
import net.danygames2014.microblocks.util.MathHelper;
import net.danygames2014.nyalib.multipart.MultipartState;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.math.Direction;
import org.lwjgl.input.Keyboard;

public abstract class FaceMicroblockItem extends MicroblockItem{
    private static final FacePlacementHelper placementHelper = new FacePlacementHelper();

    public FaceMicroblockItem(Identifier identifier, Block block) {
        super(identifier, block);
    }

    @Override
    public void renderGrid(PlayerEntity player, int blockX, int blockY, int blockZ, net.modificationstation.stationapi.api.util.math.Vec3d hit, Direction face, float tickDelta) {
        placementHelper.renderGrid(player, blockX, blockY, blockZ, hit, face, 1/4D, tickDelta);
    }

    @Override
    public boolean useOnBlock(ItemStack stack, PlayerEntity player, World world, int x, int y, int z, int side, Vec3d hitVec) {
        int size = getSize();

        Vec3d relativeHitVec = hitVec.add(-x, -y, -z);

        BlockPos placementPos = placementHelper.getPlacementPos(x, y, z, Direction.byId(side));
        MultipartState state = world.getMultipartState(x, y, z);
        if(state != null && MathHelper.getHitDepth(new net.modificationstation.stationapi.api.util.math.Vec3d(relativeHitVec.x, relativeHitVec.y, relativeHitVec.z), Direction.byId(side)) < 1){
            PlacementSlot slot = placementHelper.getSlot(x, y, z, Direction.byId(side), new net.modificationstation.stationapi.api.util.math.Vec3d(hitVec.x, hitVec.y, hitVec.z), 1/4D);
            if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)){
                slot = placementHelper.getOppositeSlot(slot, Direction.byId(side));
            }
            if(placementHelper.canPlace(world, x, y, z, slot, size, FaceMicroblockMultipartComponent.MODEL)){
                world.addMultipartComponent(x, y, z, new FaceMicroblockMultipartComponent(this.block, slot, size));
                return true;
            }
        }
        PlacementSlot slot = placementHelper.getSlot(placementPos.getX(), placementPos.getY(), placementPos.getZ(), Direction.byId(side), new net.modificationstation.stationapi.api.util.math.Vec3d(hitVec.x, hitVec.y, hitVec.z), 1/4D);
        if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)){
            slot = placementHelper.getOppositeSlot(slot, Direction.byId(side));
        }

        if(!placementHelper.canPlace(world, placementPos.getX(), placementPos.getY(), placementPos.getZ(), slot, size, FaceMicroblockMultipartComponent.MODEL)){
            return false;
        }

        world.addMultipartComponent(placementPos.getX(), placementPos.getY(), placementPos.getZ(), new FaceMicroblockMultipartComponent(this.block, slot, size));
        return true;
    }
}
