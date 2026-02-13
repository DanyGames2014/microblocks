package net.danygames2014.microblocks.item.base;

import net.danygames2014.microblocks.client.render.MicroblockRenderer;
import net.danygames2014.microblocks.item.MicroblockItemType;
import net.danygames2014.microblocks.multipart.*;
import net.danygames2014.microblocks.multipart.model.MicroblockModel;
import net.danygames2014.microblocks.multipart.placement.EdgePlacementHelper;
import net.danygames2014.microblocks.multipart.placement.PlacementHelper;
import net.danygames2014.microblocks.multipart.placement.PostPlacementHelper;
import net.danygames2014.nyalib.util.PlayerUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.math.Direction;
import net.modificationstation.stationapi.api.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public abstract class EdgeMicroblockItem extends MicroblockItem {
    private static final EdgePlacementHelper placementHelper = new EdgePlacementHelper();
    private static final PostPlacementHelper postPlacementHelper = new PostPlacementHelper();
    
    public EdgeMicroblockItem(Identifier identifier, Block block, int meta) {
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

        if (slot != PlacementSlot.INVALID) {
            if(placementHelper.canPlace(world, x, y, z, getType(), slot, size, EdgeMicroblockMultipartComponent.MODEL)) {
                world.addMultipartComponent(x, y, z, new EdgeMicroblockMultipartComponent(this.block, meta, slot, size));
                return true;
            }
        } else {
            slot = postPlacementHelper.getSlot(x, y, z, dir, vec, placementHelper.getGridCenterSize());
            if (placementHelper.canPlace(world, x, y, z, getType(), slot, size, PostMicroblockMultipartComponent.MODEL)) {
                world.addMultipartComponent(x, y, z, new PostMicroblockMultipartComponent(this.block, meta, slot, size));
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean tryRenderPreview(World world, int x, int y, int z, Direction dir, Vec3d vec, int size, MicroblockModel microblockModel, Block block, int meta, PlacementHelper placementHelper, PlayerEntity player, float tickDelta){
        PlacementSlot placementSlot = placementHelper.getSlot(x, y, z, dir, vec, placementHelper.getGridCenterSize());

        MicroblockModel model = microblockModel;
        if(placementSlot == PlacementSlot.INVALID){
            placementSlot = postPlacementHelper.getSlot(x, y, z, dir, vec, placementHelper.getGridCenterSize());
            model = PostMicroblockMultipartComponent.MODEL;
        }

        if (player.isSneaking()) {
            placementSlot = placementHelper.getOppositeSlot(placementSlot, dir);
        }

        if(placementHelper.canPlace(world, x, y, z, getType(), placementSlot, size, model)){
            MicroblockRenderer renderer = MicroblockRenderer.INSTANCE;
            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

            Vec3d playerPos = PlayerUtil.getRenderPosition(player, tickDelta);
            GL11.glTranslated(x - playerPos.x, y - playerPos.y, z - playerPos.z);

            renderer.renderMicroblockPreview(model, placementSlot, block, meta, size, 0, 0, 0);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glPopMatrix();
            return true;
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
