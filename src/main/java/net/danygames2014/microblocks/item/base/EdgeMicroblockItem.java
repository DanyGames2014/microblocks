package net.danygames2014.microblocks.item.base;

import net.danygames2014.microblocks.client.render.MicroblockRenderer;
import net.danygames2014.microblocks.multipart.*;
import net.danygames2014.microblocks.multipart.model.MicroblockModel;
import net.danygames2014.microblocks.multipart.model.PostMicroblockModel;
import net.danygames2014.microblocks.multipart.placement.EdgePlacementHelper;
import net.danygames2014.microblocks.multipart.placement.PlacementHelper;
import net.danygames2014.nyalib.multipart.MultipartSlot;
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
    
    public EdgeMicroblockItem(Identifier identifier, Block block, int meta) {
        super(identifier, block, meta);
    }

    @Override
    public void renderGrid(PlayerEntity player, int blockX, int blockY, int blockZ, net.modificationstation.stationapi.api.util.math.Vec3d hit, Direction face, float tickDelta) {
        placementHelper.renderGrid(player, blockX, blockY, blockZ, hit, face, placementHelper.getGridCenterSize(), tickDelta);
    }

    protected boolean tryPlace(World world, int x, int y, int z, Direction dir, net.modificationstation.stationapi.api.util.math.Vec3d vec, int size, PlayerEntity player) {
        MultipartSlot slot = placementHelper.getSlot(x, y, z, dir, vec, placementHelper.getGridCenterSize());

        boolean sneaking = player != null && player.isSneaking();

        if(sneaking && slot != MultipartSlot.CUSTOM){
            slot = placementHelper.getOppositeSlot(slot, dir);
        }

        if (slot != MultipartSlot.CUSTOM) {
            return super.tryPlace(world, x, y, z, dir, vec, size, player);
        } else {
            PostMicroblockMultipartComponent component = new PostMicroblockMultipartComponent(this.block, meta, slot, dir.getAxis(), size);
            component.x = x;
            component.y = y;
            component.z = z;
            if (placementHelper.canPlace(world, x, y, z, component)) {
                world.addMultipartComponent(x, y, z, component);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean tryRenderPreview(World world, int x, int y, int z, Direction dir, Vec3d vec, int size, MicroblockModel microblockModel, Block block, int meta, PlacementHelper placementHelper, PlayerEntity player, float tickDelta){
        MultipartSlot placementSlot = placementHelper.getSlot(x, y, z, dir, vec, placementHelper.getGridCenterSize());

        if(placementSlot != MultipartSlot.CUSTOM){
            return super.tryRenderPreview(world, x, y, z, dir, vec, size, microblockModel, block, meta, placementHelper, player, tickDelta);
        }

        MicroblockMultipartComponent component = new PostMicroblockMultipartComponent(block, meta, placementSlot, dir.getAxis(), size);
        component.x = x;
        component.y = y;
        component.z = z;

        if(placementHelper.canPlace(world, x, y, z, component)){
            MicroblockRenderer renderer = MicroblockRenderer.INSTANCE;
            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

            Vec3d playerPos = PlayerUtil.getRenderPosition(player, tickDelta);
            GL11.glTranslated(x - playerPos.x, y - playerPos.y, z - playerPos.z);

            renderer.renderMicroblockPreview(component.getMicroblockModel(), placementSlot, block, meta, size, 0, 0, 0);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glPopMatrix();
            return true;
        }

//        if(!player.isSneaking()){
//            MultipartSlot oppositeSlot = placementHelper.getOppositeSlot(placementSlot, dir);
//            if(placementHelper.canPlace(world, x, y, z, dir, getType(), oppositeSlot, size, model)){
//                MicroblockRenderer renderer = MicroblockRenderer.INSTANCE;
//                GL11.glPushMatrix();
//                GL11.glEnable(GL11.GL_BLEND);
//                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//
//                Vec3d playerPos = PlayerUtil.getRenderPosition(player, tickDelta);
//                GL11.glTranslated(x - playerPos.x, y - playerPos.y, z - playerPos.z);
//
//                renderer.renderMicroblockPreview(model, oppositeSlot, block, meta, size, 0, 0, 0);
//                GL11.glDisable(GL11.GL_BLEND);
//                GL11.glPopMatrix();
//                return true;
//            }
//        }
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

    @Override
    public MicroblockFactory getMicroblockFactory() {
        return EdgeMicroblockMultipartComponent::new;
    }
}
