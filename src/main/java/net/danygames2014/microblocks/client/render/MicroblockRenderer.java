package net.danygames2014.microblocks.client.render;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.danygames2014.microblocks.multipart.MicroblockMultipartComponent;
import net.danygames2014.microblocks.multipart.model.MicroblockModel;
import net.danygames2014.microblocks.util.ShrinkHelper;
import net.danygames2014.nyalib.multipart.MultipartComponent;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.util.math.Box;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlas;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases;
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
            Box b = Box.create((float) (component.renderBoundsMinX - component.x), (float) (component.renderBoundsMinY - component.y), (float) (component.renderBoundsMinZ - component.z), (float) (component.renderBoundsMaxX - component.x), (float) (component.renderBoundsMaxY - component.y), (float) (component.renderBoundsMaxZ - component.z));
            if((component.renderMask & 1) == 0){
                renderBottom(b, component.x, component.y, component.z, component.block.getTexture(0), 0xFFFFFF);
            }

            if((component.renderMask & 2) == 0){
                renderTop(b, component.x, component.y, component.z, component.block.getTexture(0), 0xFFFFFF);
            }

            if((component.renderMask & 4) == 0){
                renderEast(b, component.x, component.y, component.z, component.block.getTexture(0), 0xFFFFFF);
//                blockRenderManager.renderEastFace(component.block, component.x, component.y, component.z, component.block.getTexture(2));
            }
            if((component.renderMask & 8) == 0){
                renderWest(b, component.x, component.y, component.z, component.block.getTexture(0), 0xFFFFFF);
//                blockRenderManager.renderWestFace(component.block, component.x, component.y, component.z, component.block.getTexture(3));
            }
            if((component.renderMask & 16) == 0){
                renderNorth(b, component.x, component.y, component.z, component.block.getTexture(0), 0xFFFFFF);
            }
            if((component.renderMask & 32) == 0){
                renderSouth(b, component.x, component.y, component.z, component.block.getTexture(0), 0xFFFFFF);
//                blockRenderManager.renderSouthFace(component.block, component.x, component.y, component.z, component.block.getTexture(5));
            }


//            for(int side = 0; side < 6; side++) {
//                if ((component.renderMask & (1 << side)) != 0) {
//                    continue;
//                }
//                switch (side) {
//                    case 0 -> blockRenderManager.renderTopFace(component.block, component.x, component.y, component.z, component.block.getTexture(0));
//                    case 1 -> blockRenderManager.renderBottomFace(component.block, component.x, component.y, component.z, component.block.getTexture(1));
//                    case 2 -> blockRenderManager.renderEastFace(component.block, component.x, component.y, component.z, component.block.getTexture(2));
//                    case 3 -> blockRenderManager.renderWestFace(component.block, component.x, component.y, component.z, component.block.getTexture(3));
//                    case 4 -> blockRenderManager.renderNorthFace(component.block, component.x, component.y, component.z, component.block.getTexture(4));
//                    case 5 -> blockRenderManager.renderSouthFace(component.block, component.x, component.y, component.z, component.block.getTexture(5));
//                }
//            }
        }
        component.block.setBoundingBox(0f, 0f, 0f, 1f, 1f, 1f);
    }

    void renderBottom(Box box, int x, int y, int z, int texture, int color){
        Tessellator tessellator = Tessellator.INSTANCE;
        tessellator.color(color);
        double vMinY = y + box.minY;

        double vMinX = x + box.minX;
        double vMaxX = x + box.maxX;
        double vMinZ = z + box.minZ;
        double vMaxZ = z + box.maxZ;

        Atlas.Sprite sprite = Atlases.getTerrain().getTexture(texture);
        double sMinU = sprite.getStartU();
        double sMaxU = sprite.getEndU();
        double sMinV = sprite.getStartV();
        double sMaxV = sprite.getEndV();

        double sWidth = sMaxU - sMinU;
        double sHeight = sMaxV - sMinV;

        double uMin  = sMinU + (box.minX * sWidth);
        double uMax = sMinU + (box.maxX * sWidth);
        double vMin = sMinV + (box.minZ * sHeight);
        double vMax = sMinV + (box.maxZ * sHeight);

        tessellator.vertex(vMinX, vMinY, vMaxZ, uMin, vMax);
        tessellator.vertex(vMinX, vMinY, vMinZ, uMin, vMin);
        tessellator.vertex(vMaxX, vMinY, vMinZ, uMax, vMin);
        tessellator.vertex(vMaxX, vMinY, vMaxZ, uMax, vMax);
    }

    void renderTop(Box box, int x, int y, int z, int texture, int color){
        Tessellator tessellator = Tessellator.INSTANCE;
        tessellator.color(color);

        double vMaxY = y + box.maxY;

        double vMinX = x + box.minX;
        double vMaxX = x + box.maxX;
        double vMinZ = z + box.minZ;
        double vMaxZ = z + box.maxZ;

        Atlas.Sprite sprite = Atlases.getTerrain().getTexture(texture);
        double sMinU = sprite.getStartU();
        double sMaxU = sprite.getEndU();
        double sMinV = sprite.getStartV();
        double sMaxV = sprite.getEndV();

        double sWidth = sMaxU - sMinU;
        double sHeight = sMaxV - sMinV;

        double uMin = sMinU + (box.minX * sWidth);
        double uMax = sMinU + (box.maxX * sWidth);
        double vMin = sMinV + (box.minZ * sHeight);
        double vMax = sMinV + (box.maxZ * sHeight);

        tessellator.vertex(vMaxX, vMaxY, vMaxZ, uMax, vMax);
        tessellator.vertex(vMaxX, vMaxY, vMinZ, uMax, vMin);
        tessellator.vertex(vMinX, vMaxY, vMinZ, uMin, vMin);
        tessellator.vertex(vMinX, vMaxY, vMaxZ, uMin, vMax);
    }

    void renderEast(Box box, int x, int y, int z, int texture, int color) {
        Tessellator tessellator = Tessellator.INSTANCE;
        tessellator.color(color);

        double vMaxZ = z + box.maxZ;

        double vMinX = x + box.minX;
        double vMaxX = x + box.maxX;
        double vMinY = y + box.minY;
        double vMaxY = y + box.maxY;

        Atlas.Sprite sprite = Atlases.getTerrain().getTexture(texture);
        double sMinU = sprite.getStartU();
        double sMaxU = sprite.getEndU();
        double sMinV = sprite.getStartV();
        double sMaxV = sprite.getEndV();

        double sWidth = sMaxU - sMinU;
        double sHeight = sMaxV - sMinV;

        double uMin = sMinU + (box.minX * sWidth);
        double uMax = sMinU + (box.maxX * sWidth);

        double vMin = sMinV + ((1.0 - box.maxY) * sHeight);
        double vMax = sMinV + ((1.0 - box.minY) * sHeight);

        tessellator.vertex(vMaxX, vMaxY, vMaxZ, uMax, vMin);
        tessellator.vertex(vMinX, vMaxY, vMaxZ, uMin, vMin);
        tessellator.vertex(vMinX, vMinY, vMaxZ, uMin, vMax);
        tessellator.vertex(vMaxX, vMinY, vMaxZ, uMax, vMax);
    }

    void renderWest(Box box, int x, int y, int z, int texture, int color) {
        Tessellator tessellator = Tessellator.INSTANCE;
        tessellator.color(color);

        double vMinZ = z + box.minZ;

        double vMinX = x + box.minX;
        double vMaxX = x + box.maxX;
        double vMinY = y + box.minY;
        double vMaxY = y + box.maxY;

        Atlas.Sprite sprite = Atlases.getTerrain().getTexture(texture);
        double sMinU = sprite.getStartU();
        double sMaxU = sprite.getEndU();
        double sMinV = sprite.getStartV();
        double sMaxV = sprite.getEndV();

        double sWidth = sMaxU - sMinU;
        double sHeight = sMaxV - sMinV;

        double uMin = sMinU + ((1.0 - box.minX) * sWidth);
        double uMax = sMinU + ((1.0 - box.maxX) * sWidth);

        double vMin = sMinV + ((1.0 - box.maxY) * sHeight);
        double vMax = sMinV + ((1.0 - box.minY) * sHeight);

        tessellator.vertex(vMinX, vMaxY, vMinZ, uMin, vMin);
        tessellator.vertex(vMaxX, vMaxY, vMinZ, uMax, vMin);
        tessellator.vertex(vMaxX, vMinY, vMinZ, uMax, vMax);
        tessellator.vertex(vMinX, vMinY, vMinZ, uMin, vMax);
    }

    void renderNorth(Box box, int x, int y, int z, int texture, int color) {
        Tessellator tessellator = Tessellator.INSTANCE;
        tessellator.color(color);

        double vMaxX = x + box.maxX;

        double vMinY = y + box.minY;
        double vMaxY = y + box.maxY;
        double vMinZ = z + box.minZ;
        double vMaxZ = z + box.maxZ;

        Atlas.Sprite sprite = Atlases.getTerrain().getTexture(texture);
        double sMinU = sprite.getStartU();
        double sMaxU = sprite.getEndU();
        double sMinV = sprite.getStartV();
        double sMaxV = sprite.getEndV();

        double sWidth = sMaxU - sMinU;
        double sHeight = sMaxV - sMinV;

        double uMin = sMinU + ((1 - box.minZ) * sWidth);
        double uMax = sMinU + ((1 - box.maxZ) * sWidth);

        double vMin = sMinV + ( (1.0 - box.maxY) * sHeight);
        double vMax = sMinV + ( (1.0 - box.minY) * sHeight);

        tessellator.vertex(vMaxX, vMaxY, vMinZ, uMin, vMin);
        tessellator.vertex(vMaxX, vMaxY, vMaxZ, uMax, vMin);
        tessellator.vertex(vMaxX, vMinY, vMaxZ, uMax, vMax);
        tessellator.vertex(vMaxX, vMinY, vMinZ, uMin, vMax);
    }

    void renderSouth(Box box, int x, int y, int z, int texture, int color) {
        Tessellator tessellator = Tessellator.INSTANCE;
        tessellator.color(color);

        double vMinX = x + box.minX;

        double vMinY = y + box.minY;
        double vMaxY = y + box.maxY;
        double vMinZ = z + box.minZ;
        double vMaxZ = z + box.maxZ;

        Atlas.Sprite sprite = Atlases.getTerrain().getTexture(texture);
        double sMinU = sprite.getStartU();
        double sMaxU = sprite.getEndU();
        double sMinV = sprite.getStartV();
        double sMaxV = sprite.getEndV();

        double sWidth = sMaxU - sMinU;
        double sHeight = sMaxV - sMinV;

        double uMin = sMinU + (box.maxZ * sWidth);
        double uMax = sMinU + (box.minZ * sWidth);

        double vMin = sMinV + ((1.0 - box.maxY) * sHeight);
        double vMax = sMinV + ((1.0 - box.minY) * sHeight);

        tessellator.vertex(vMinX, vMaxY, vMaxZ, uMin, vMin);
        tessellator.vertex(vMinX, vMaxY, vMinZ, uMax, vMin);
        tessellator.vertex(vMinX, vMinY, vMinZ, uMax, vMax);
        tessellator.vertex(vMinX, vMinY, vMaxZ, uMin, vMax);
    }
}
