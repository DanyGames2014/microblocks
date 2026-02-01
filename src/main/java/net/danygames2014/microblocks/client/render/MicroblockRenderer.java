package net.danygames2014.microblocks.client.render;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.danygames2014.microblocks.multipart.MicroblockMultipartComponent;
import net.danygames2014.microblocks.multipart.model.MicroblockModel;
import net.danygames2014.microblocks.util.ShrinkHelper;
import net.danygames2014.nyalib.multipart.MultipartComponent;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.util.math.Box;
import org.lwjgl.opengl.GL11;

public class MicroblockRenderer {
    public static final MicroblockRenderer INSTANCE = new MicroblockRenderer();

    public void renderMicroblock(MicroblockMultipartComponent component, BlockRenderManager blockRenderManager){
        MicroblockModel model = component.getMicroblockModel();
        Tessellator.INSTANCE.color(1f, 1f, 1f, 1f);
        if(
                (component.renderMask & 1) != 0 ||
                (component.renderMask & 2) != 0 ||
                (component.renderMask & 4) != 0 ||
                (component.renderMask & 8) != 0 ||
                (component.renderMask & 16) != 0 ||
                (component.renderMask & 32) != 0
        ){
            Tessellator.INSTANCE.color(1f, 0f, 0f, 1f);
        }




        ObjectArrayList<Box> boxes = model.getBoxesForSlot(component.slot, component.getSize(), 0, 0, 0);
        for(Box box : boxes){
//            component.block.setBoundingBox((float) (box.minX), (float) (box.minY), (float) (box.minZ), (float) (box.maxX), (float) (box.maxY), (float) (box.maxZ));
//            Box b = component.renderBounds.offset(-component.x, -component.y, -component.z);
            component.block.setBoundingBox((float) (component.renderBoundsMinX - component.x), (float) (component.renderBoundsMinY - component.y), (float) (component.renderBoundsMinZ - component.z), (float) (component.renderBoundsMaxX - component.x), (float) (component.renderBoundsMaxY - component.y), (float) (component.renderBoundsMaxZ - component.z));
            if((component.renderMask & 1) == 0){
                blockRenderManager.renderBottomFace(component.block, component.x, component.y, component.z, component.block.getTexture(0));
            }

            if((component.renderMask & 2) == 0){
                blockRenderManager.renderTopFace(component.block, component.x, component.y, component.z, component.block.getTexture(1));
            }

            if((component.renderMask & 4) == 0){
                blockRenderManager.renderEastFace(component.block, component.x, component.y, component.z, component.block.getTexture(2));
            }
            if((component.renderMask & 8) == 0){
                blockRenderManager.renderWestFace(component.block, component.x, component.y, component.z, component.block.getTexture(3));
            }
            if((component.renderMask & 16) == 0){
                blockRenderManager.renderNorthFace(component.block, component.x, component.y, component.z, component.block.getTexture(4));
            }
            if((component.renderMask & 32) == 0){
                blockRenderManager.renderSouthFace(component.block, component.x, component.y, component.z, component.block.getTexture(5));
            }


//            for(int side = 0; side < 6; side++) {
//
//                if ((component.renderMask & (1 << side)) != 0) {
//                    continue;
//                }
//
//                switch (side) {
//                    case 0 -> blockRenderManager.renderTopFace(component.block, component.x, component.y, component.z, component.block.getTexture(0));
//                    case 1 -> blockRenderManager.renderBottomFace(component.block, component.x, component.y, component.z, component.block.getTexture(1));
//                    case 2 -> blockRenderManager.renderEastFace(component.block, component.x, component.y, component.z, component.block.getTexture(2));
//                    case 3 -> blockRenderManager.renderWestFace(component.block, component.x, component.y, component.z, component.block.getTexture(3));
//                    case 4 -> blockRenderManager.renderNorthFace(component.block, component.x, component.y, component.z, component.block.getTexture(4));
//                    case 5 -> blockRenderManager.renderSouthFace(component.block, component.x, component.y, component.z, component.block.getTexture(5));
//                }
//
//
//
//
//
//
//            }
        }
        component.block.setBoundingBox(0f, 0f, 0f, 1f, 1f, 1f);
    }
}
