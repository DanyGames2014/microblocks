package net.danygames2014.microblocks.client.render;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.danygames2014.microblocks.multipart.MicroblockMultipartComponent;
import net.danygames2014.microblocks.multipart.model.MicroblockModel;
import net.danygames2014.microblocks.util.ShrinkHelper;
import net.danygames2014.nyalib.multipart.MultipartComponent;
import net.minecraft.block.Block;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.util.math.Box;
import net.minecraft.world.BlockView;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlas;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases;
import org.lwjgl.opengl.GL11;

public class MicroblockRenderer {
    public static final MicroblockRenderer INSTANCE = new MicroblockRenderer();

    private boolean useAo;
    private float selfBrightness;
    private float northBrightness;
    private float bottomBrightness;
    private float eastBrightness;
    private float southBrightness;
    private float topBrightness;
    private float westBrightness;
    private float northEastBottomBrightness;
    private float northBottomBrightness;
    private float northWestBottomBrightness;
    private float eastBottomBrightness;
    private float westBottomBrightness;
    private float southEastBottomBrightness;
    private float southBottomBrightness;
    private float southWestBottomBrightness;
    private float northEastTopBrightness;
    private float northTopBrightness;
    private float northWestTopBrightness;
    private float eastTopBrightness;
    private float southEastTopBrightness;
    private float southTopBrightness;
    private float westTopBrightness;
    private float southWestTopBrightness;
    private float northEastBrightness;
    private float southEastBrightness;
    private float northWestBrightness;
    private float southWestBrightness;
    private int useSurroundingBrightness = 1;
    private float firstVertexRed;
    private float secondVertexRed;
    private float thirdVertexRed;
    private float fourthVertexRed;
    private float firstVertexGreen;
    private float secondVertexGreen;
    private float thirdVertexGreen;
    private float fourthVertexGreen;
    private float firstVertexBlue;
    private float secondVertexBlue;
    private float thirdVertexBlue;
    private float fourthVertexBlue;
    private boolean topNorthEdgeTranslucent;
    private boolean topEastEdgeTranslucent;
    private boolean topWestEdgeTranslucent;
    private boolean topSouthEdgeTranslucent;
    private boolean northWestEdgeTranslucent;
    private boolean southEastEdgeTranslucent;
    private boolean southWestEdgeTranslucent;
    private boolean northEastEdgeTranslucent;
    private boolean bottomNorthEdgeTranslucent;
    private boolean bottomEastEdgeTranslucent;
    private boolean bottomWestEdgeTranslucent;
    private boolean bottomSouthEdgeTranslucent;


    public void renderMicroblock(BlockView blockView, MicroblockMultipartComponent component, BlockRenderManager blockRenderManager){
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
//            component.block.setBoundingBox((float) (component.renderBoundsMinX - component.x), (float) (component.renderBoundsMinY - component.y), (float) (component.renderBoundsMinZ - component.z), (float) (component.renderBoundsMaxX - component.x), (float) (component.renderBoundsMaxY - component.y), (float) (component.renderBoundsMaxZ - component.z));
            Box b = Box.create((float) (component.renderBoundsMinX - component.x), (float) (component.renderBoundsMinY - component.y), (float) (component.renderBoundsMinZ - component.z), (float) (component.renderBoundsMaxX - component.x), (float) (component.renderBoundsMaxY - component.y), (float) (component.renderBoundsMaxZ - component.z));
            renderBox(blockView, component.block, b, component.x, component.y, component.z, 1f, 1f, 1f, new int[]{component.block.getTexture(0), component.block.getTexture(1), component.block.getTexture(2), component.block.getTexture(3), component.block.getTexture(4), component.block.getTexture(5)}, new int[]{0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF}, component.renderMask);
            //            if((component.renderMask & 1) == 0){
//                renderBottom(b, component.x, component.y, component.z, component.block.getTexture(0), 0xFFFFFF);
//            }
//
//            if((component.renderMask & 2) == 0){
//                renderTop(b, component.x, component.y, component.z, component.block.getTexture(0), 0xFFFFFF);
//            }
//
//            if((component.renderMask & 4) == 0){
//                renderEast(b, component.x, component.y, component.z, component.block.getTexture(0), 0xFFFFFF);
////                blockRenderManager.renderEastFace(component.block, component.x, component.y, component.z, component.block.getTexture(2));
//            }
//            if((component.renderMask & 8) == 0){
//                renderWest(b, component.x, component.y, component.z, component.block.getTexture(0), 0xFFFFFF);
////                blockRenderManager.renderWestFace(component.block, component.x, component.y, component.z, component.block.getTexture(3));
//            }
//            if((component.renderMask & 16) == 0){
//                renderNorth(b, component.x, component.y, component.z, component.block.getTexture(0), 0xFFFFFF);
//            }
//            if((component.renderMask & 32) == 0){
//                renderSouth(b, component.x, component.y, component.z, component.block.getTexture(0), 0xFFFFFF);
////                blockRenderManager.renderSouthFace(component.block, component.x, component.y, component.z, component.block.getTexture(5));
//            }


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
//        component.block.setBoundingBox(0f, 0f, 0f, 1f, 1f, 1f);
    }

    public void renderBox(BlockView blockView, Block block, Box box, int x, int y, int z, float red, float green, float blue, int[] textures, int[] sideColors, int renderMask){
        this.useAo = true;
        float var9;
        float var10;
        float var11;
        float var12;
        boolean var13 = true;
        boolean var14 = true;
        boolean var15 = true;
        boolean var16 = true;
        boolean var17 = true;
        boolean var18 = true;
        this.selfBrightness = block.getLuminance(blockView, x, y, z);
        this.northBrightness = block.getLuminance(blockView, x - 1, y, z);
        this.bottomBrightness = block.getLuminance(blockView, x, y - 1, z);
        this.eastBrightness = block.getLuminance(blockView, x, y, z - 1);
        this.southBrightness = block.getLuminance(blockView, x + 1, y, z);
        this.topBrightness = block.getLuminance(blockView, x, y + 1, z);
        this.westBrightness = block.getLuminance(blockView, x, y, z + 1);
        this.topEastEdgeTranslucent = Block.BLOCKS_ALLOW_VISION[blockView.getBlockId(x + 1, y + 1, z)];
        this.bottomEastEdgeTranslucent = Block.BLOCKS_ALLOW_VISION[blockView.getBlockId(x + 1, y - 1, z)];
        this.southEastEdgeTranslucent = Block.BLOCKS_ALLOW_VISION[blockView.getBlockId(x + 1, y, z + 1)];
        this.northEastEdgeTranslucent = Block.BLOCKS_ALLOW_VISION[blockView.getBlockId(x + 1, y, z - 1)];
        this.topWestEdgeTranslucent = Block.BLOCKS_ALLOW_VISION[blockView.getBlockId(x - 1, y + 1, z)];
        this.bottomWestEdgeTranslucent = Block.BLOCKS_ALLOW_VISION[blockView.getBlockId(x - 1, y - 1, z)];
        this.northWestEdgeTranslucent = Block.BLOCKS_ALLOW_VISION[blockView.getBlockId(x - 1, y, z - 1)];
        this.southWestEdgeTranslucent = Block.BLOCKS_ALLOW_VISION[blockView.getBlockId(x - 1, y, z + 1)];
        this.topSouthEdgeTranslucent = Block.BLOCKS_ALLOW_VISION[blockView.getBlockId(x, y + 1, z + 1)];
        this.topNorthEdgeTranslucent = Block.BLOCKS_ALLOW_VISION[blockView.getBlockId(x, y + 1, z - 1)];
        this.bottomSouthEdgeTranslucent = Block.BLOCKS_ALLOW_VISION[blockView.getBlockId(x, y - 1, z + 1)];
        this.bottomNorthEdgeTranslucent = Block.BLOCKS_ALLOW_VISION[blockView.getBlockId(x, y - 1, z - 1)];

        // Bottom
        if((renderMask & 1) == 0){
            if (this.useSurroundingBrightness <= 0) {
                var9 = var10 = var11 = var12 = this.bottomBrightness;
            } else {
                --y;
                this.northBottomBrightness = block.getLuminance(blockView, x - 1, y, z);
                this.eastBottomBrightness = block.getLuminance(blockView, x, y, z - 1);
                this.westBottomBrightness = block.getLuminance(blockView, x, y, z + 1);
                this.southBottomBrightness = block.getLuminance(blockView, x + 1, y, z);
                if (!this.bottomNorthEdgeTranslucent && !this.bottomWestEdgeTranslucent) {
                    this.northEastBottomBrightness = this.northBottomBrightness;
                } else {
                    this.northEastBottomBrightness = block.getLuminance(blockView, x - 1, y, z - 1);
                }

                if (!this.bottomSouthEdgeTranslucent && !this.bottomWestEdgeTranslucent) {
                    this.northWestBottomBrightness = this.northBottomBrightness;
                } else {
                    this.northWestBottomBrightness = block.getLuminance(blockView, x - 1, y, z + 1);
                }

                if (!this.bottomNorthEdgeTranslucent && !this.bottomEastEdgeTranslucent) {
                    this.southEastBottomBrightness = this.southBottomBrightness;
                } else {
                    this.southEastBottomBrightness = block.getLuminance(blockView, x + 1, y, z - 1);
                }

                if (!this.bottomSouthEdgeTranslucent && !this.bottomEastEdgeTranslucent) {
                    this.southWestBottomBrightness = this.southBottomBrightness;
                } else {
                    this.southWestBottomBrightness = block.getLuminance(blockView, x + 1, y, z + 1);
                }

                ++y;
                var9 = (this.northWestBottomBrightness + this.northBottomBrightness + this.westBottomBrightness + this.bottomBrightness) / 4.0F;
                var12 = (this.westBottomBrightness + this.bottomBrightness + this.southWestBottomBrightness + this.southBottomBrightness) / 4.0F;
                var11 = (this.bottomBrightness + this.eastBottomBrightness + this.southBottomBrightness + this.southEastBottomBrightness) / 4.0F;
                var10 = (this.northBottomBrightness + this.northEastBottomBrightness + this.bottomBrightness + this.eastBottomBrightness) / 4.0F;
            }

            this.firstVertexRed = this.secondVertexRed = this.thirdVertexRed = this.fourthVertexRed = (var13 ? red : 1.0F) * 0.5F;
            this.firstVertexGreen = this.secondVertexGreen = this.thirdVertexGreen = this.fourthVertexGreen = (var13 ? green : 1.0F) * 0.5F;
            this.firstVertexBlue = this.secondVertexBlue = this.thirdVertexBlue = this.fourthVertexBlue = (var13 ? blue : 1.0F) * 0.5F;
            this.firstVertexRed *= var9;
            this.firstVertexGreen *= var9;
            this.firstVertexBlue *= var9;
            this.secondVertexRed *= var10;
            this.secondVertexGreen *= var10;
            this.secondVertexBlue *= var10;
            this.thirdVertexRed *= var11;
            this.thirdVertexGreen *= var11;
            this.thirdVertexBlue *= var11;
            this.fourthVertexRed *= var12;
            this.fourthVertexGreen *= var12;
            this.fourthVertexBlue *= var12;
            renderBottom(box, x, y, z, textures[0], 0xFFFFFF);
        }

        // Top
        if((renderMask & 2) == 0){
            if (this.useSurroundingBrightness <= 0) {
                var9 = var10 = var11 = var12 = this.topBrightness;
            } else {
                ++y;
                this.northTopBrightness = block.getLuminance(blockView, x - 1, y, z);
                this.southTopBrightness = block.getLuminance(blockView, x + 1, y, z);
                this.eastTopBrightness = block.getLuminance(blockView, x, y, z - 1);
                this.westTopBrightness = block.getLuminance(blockView, x, y, z + 1);
                if (!this.topNorthEdgeTranslucent && !this.topWestEdgeTranslucent) {
                    this.northEastTopBrightness = this.northTopBrightness;
                } else {
                    this.northEastTopBrightness = block.getLuminance(blockView, x - 1, y, z - 1);
                }

                if (!this.topNorthEdgeTranslucent && !this.topEastEdgeTranslucent) {
                    this.southEastTopBrightness = this.southTopBrightness;
                } else {
                    this.southEastTopBrightness = block.getLuminance(blockView, x + 1, y, z - 1);
                }

                if (!this.topSouthEdgeTranslucent && !this.topWestEdgeTranslucent) {
                    this.northWestTopBrightness = this.northTopBrightness;
                } else {
                    this.northWestTopBrightness = block.getLuminance(blockView, x - 1, y, z + 1);
                }

                if (!this.topSouthEdgeTranslucent && !this.topEastEdgeTranslucent) {
                    this.southWestTopBrightness = this.southTopBrightness;
                } else {
                    this.southWestTopBrightness = block.getLuminance(blockView, x + 1, y, z + 1);
                }

                --y;
                var12 = (this.northWestTopBrightness + this.northTopBrightness + this.westTopBrightness + this.topBrightness) / 4.0F;
                var9 = (this.westTopBrightness + this.topBrightness + this.southWestTopBrightness + this.southTopBrightness) / 4.0F;
                var10 = (this.topBrightness + this.eastTopBrightness + this.southTopBrightness + this.southEastTopBrightness) / 4.0F;
                var11 = (this.northTopBrightness + this.northEastTopBrightness + this.topBrightness + this.eastTopBrightness) / 4.0F;
            }

            this.firstVertexRed = this.secondVertexRed = this.thirdVertexRed = this.fourthVertexRed = var14 ? red : 1.0F;
            this.firstVertexGreen = this.secondVertexGreen = this.thirdVertexGreen = this.fourthVertexGreen = var14 ? green : 1.0F;
            this.firstVertexBlue = this.secondVertexBlue = this.thirdVertexBlue = this.fourthVertexBlue = var14 ? blue : 1.0F;
            this.firstVertexRed *= var9;
            this.firstVertexGreen *= var9;
            this.firstVertexBlue *= var9;
            this.secondVertexRed *= var10;
            this.secondVertexGreen *= var10;
            this.secondVertexBlue *= var10;
            this.thirdVertexRed *= var11;
            this.thirdVertexGreen *= var11;
            this.thirdVertexBlue *= var11;
            this.fourthVertexRed *= var12;
            this.fourthVertexGreen *= var12;
            this.fourthVertexBlue *= var12;
            renderTop(box, x, y, z, textures[1], 0xFFFFFF);
        }

        // East
        if((renderMask & 4) == 0){
            if (this.useSurroundingBrightness <= 0) {
                var9 = var10 = var11 = var12 = this.eastBrightness;
            } else {
                --z;
                this.northEastBrightness = block.getLuminance(blockView, x - 1, y, z);
                this.eastBottomBrightness = block.getLuminance(blockView, x, y - 1, z);
                this.eastTopBrightness = block.getLuminance(blockView, x, y + 1, z);
                this.southEastBrightness = block.getLuminance(blockView, x + 1, y, z);
                if (!this.northWestEdgeTranslucent && !this.bottomNorthEdgeTranslucent) {
                    this.northEastBottomBrightness = this.northEastBrightness;
                } else {
                    this.northEastBottomBrightness = block.getLuminance(blockView, x - 1, y - 1, z);
                }

                if (!this.northWestEdgeTranslucent && !this.topNorthEdgeTranslucent) {
                    this.northEastTopBrightness = this.northEastBrightness;
                } else {
                    this.northEastTopBrightness = block.getLuminance(blockView, x - 1, y + 1, z);
                }

                if (!this.northEastEdgeTranslucent && !this.bottomNorthEdgeTranslucent) {
                    this.southEastBottomBrightness = this.southEastBrightness;
                } else {
                    this.southEastBottomBrightness = block.getLuminance(blockView, x + 1, y - 1, z);
                }

                if (!this.northEastEdgeTranslucent && !this.topNorthEdgeTranslucent) {
                    this.southEastTopBrightness = this.southEastBrightness;
                } else {
                    this.southEastTopBrightness = block.getLuminance(blockView, x + 1, y + 1, z);
                }

                ++z;
                var9 = (this.northEastBrightness + this.northEastTopBrightness + this.eastBrightness + this.eastTopBrightness) / 4.0F;
                var10 = (this.eastBrightness + this.eastTopBrightness + this.southEastBrightness + this.southEastTopBrightness) / 4.0F;
                var11 = (this.eastBottomBrightness + this.eastBrightness + this.southEastBottomBrightness + this.southEastBrightness) / 4.0F;
                var12 = (this.northEastBottomBrightness + this.northEastBrightness + this.eastBottomBrightness + this.eastBrightness) / 4.0F;
            }

            this.firstVertexRed = this.secondVertexRed = this.thirdVertexRed = this.fourthVertexRed = (var15 ? red : 1.0F) * 0.8F;
            this.firstVertexGreen = this.secondVertexGreen = this.thirdVertexGreen = this.fourthVertexGreen = (var15 ? green : 1.0F) * 0.8F;
            this.firstVertexBlue = this.secondVertexBlue = this.thirdVertexBlue = this.fourthVertexBlue = (var15 ? blue : 1.0F) * 0.8F;
            this.firstVertexRed *= var9;
            this.firstVertexGreen *= var9;
            this.firstVertexBlue *= var9;
            this.secondVertexRed *= var10;
            this.secondVertexGreen *= var10;
            this.secondVertexBlue *= var10;
            this.thirdVertexRed *= var11;
            this.thirdVertexGreen *= var11;
            this.thirdVertexBlue *= var11;
            this.fourthVertexRed *= var12;
            this.fourthVertexGreen *= var12;
            this.fourthVertexBlue *= var12;
            renderEast(box, x, y, z, textures[2], 0xFFFFFF);
        }

        // West
        if(((renderMask & 8) == 0)) {
            if (this.useSurroundingBrightness <= 0) {
                var9 = var10 = var11 = var12 = this.westBrightness;
            } else {
                ++z;
                this.northWestBrightness = block.getLuminance(blockView, x - 1, y, z);
                this.southWestBrightness = block.getLuminance(blockView, x + 1, y, z);
                this.westBottomBrightness = block.getLuminance(blockView, x, y - 1, z);
                this.westTopBrightness = block.getLuminance(blockView, x, y + 1, z);
                if (!this.southWestEdgeTranslucent && !this.bottomSouthEdgeTranslucent) {
                    this.northWestBottomBrightness = this.northWestBrightness;
                } else {
                    this.northWestBottomBrightness = block.getLuminance(blockView, x - 1, y - 1, z);
                }

                if (!this.southWestEdgeTranslucent && !this.topSouthEdgeTranslucent) {
                    this.northWestTopBrightness = this.northWestBrightness;
                } else {
                    this.northWestTopBrightness = block.getLuminance(blockView, x - 1, y + 1, z);
                }

                if (!this.southEastEdgeTranslucent && !this.bottomSouthEdgeTranslucent) {
                    this.southWestBottomBrightness = this.southWestBrightness;
                } else {
                    this.southWestBottomBrightness = block.getLuminance(blockView, x + 1, y - 1, z);
                }

                if (!this.southEastEdgeTranslucent && !this.topSouthEdgeTranslucent) {
                    this.southWestTopBrightness = this.southWestBrightness;
                } else {
                    this.southWestTopBrightness = block.getLuminance(blockView, x + 1, y + 1, z);
                }

                --z;
                var9 = (this.northWestBrightness + this.northWestTopBrightness + this.westBrightness + this.westTopBrightness) / 4.0F;
                var12 = (this.westBrightness + this.westTopBrightness + this.southWestBrightness + this.southWestTopBrightness) / 4.0F;
                var11 = (this.westBottomBrightness + this.westBrightness + this.southWestBottomBrightness + this.southWestBrightness) / 4.0F;
                var10 = (this.northWestBottomBrightness + this.northWestBrightness + this.westBottomBrightness + this.westBrightness) / 4.0F;
            }

            this.firstVertexRed = this.secondVertexRed = this.thirdVertexRed = this.fourthVertexRed = (var16 ? red : 1.0F) * 0.8F;
            this.firstVertexGreen = this.secondVertexGreen = this.thirdVertexGreen = this.fourthVertexGreen = (var16 ? green : 1.0F) * 0.8F;
            this.firstVertexBlue = this.secondVertexBlue = this.thirdVertexBlue = this.fourthVertexBlue = (var16 ? blue : 1.0F) * 0.8F;
            this.firstVertexRed *= var9;
            this.firstVertexGreen *= var9;
            this.firstVertexBlue *= var9;
            this.secondVertexRed *= var10;
            this.secondVertexGreen *= var10;
            this.secondVertexBlue *= var10;
            this.thirdVertexRed *= var11;
            this.thirdVertexGreen *= var11;
            this.thirdVertexBlue *= var11;
            this.fourthVertexRed *= var12;
            this.fourthVertexGreen *= var12;
            this.fourthVertexBlue *= var12;
            renderWest(box, x, y, z, textures[3], 0xFFFFFF);
        }

        // North
        if((renderMask & 16) == 0){
            if (this.useSurroundingBrightness <= 0) {
                var9 = var10 = var11 = var12 = this.northBrightness;
            } else {
                --x;
                this.northBottomBrightness = block.getLuminance(blockView, x, y - 1, z);
                this.northEastBrightness = block.getLuminance(blockView, x, y, z - 1);
                this.northWestBrightness = block.getLuminance(blockView, x, y, z + 1);
                this.northTopBrightness = block.getLuminance(blockView, x, y + 1, z);
                if (!this.northWestEdgeTranslucent && !this.bottomWestEdgeTranslucent) {
                    this.northEastBottomBrightness = this.northEastBrightness;
                } else {
                    this.northEastBottomBrightness = block.getLuminance(blockView, x, y - 1, z - 1);
                }

                if (!this.southWestEdgeTranslucent && !this.bottomWestEdgeTranslucent) {
                    this.northWestBottomBrightness = this.northWestBrightness;
                } else {
                    this.northWestBottomBrightness = block.getLuminance(blockView, x, y - 1, z + 1);
                }

                if (!this.northWestEdgeTranslucent && !this.topWestEdgeTranslucent) {
                    this.northEastTopBrightness = this.northEastBrightness;
                } else {
                    this.northEastTopBrightness = block.getLuminance(blockView, x, y + 1, z - 1);
                }

                if (!this.southWestEdgeTranslucent && !this.topWestEdgeTranslucent) {
                    this.northWestTopBrightness = this.northWestBrightness;
                } else {
                    this.northWestTopBrightness = block.getLuminance(blockView, x, y + 1, z + 1);
                }

                ++x;
                var12 = (this.northBottomBrightness + this.northWestBottomBrightness + this.northBrightness + this.northWestBrightness) / 4.0F;
                var9 = (this.northBrightness + this.northWestBrightness + this.northTopBrightness + this.northWestTopBrightness) / 4.0F;
                var10 = (this.northEastBrightness + this.northBrightness + this.northEastTopBrightness + this.northTopBrightness) / 4.0F;
                var11 = (this.northEastBottomBrightness + this.northBottomBrightness + this.northEastBrightness + this.northBrightness) / 4.0F;
            }

            this.firstVertexRed = this.secondVertexRed = this.thirdVertexRed = this.fourthVertexRed = (var17 ? red : 1.0F) * 0.6F;
            this.firstVertexGreen = this.secondVertexGreen = this.thirdVertexGreen = this.fourthVertexGreen = (var17 ? green : 1.0F) * 0.6F;
            this.firstVertexBlue = this.secondVertexBlue = this.thirdVertexBlue = this.fourthVertexBlue = (var17 ? blue : 1.0F) * 0.6F;
            this.firstVertexRed *= var9;
            this.firstVertexGreen *= var9;
            this.firstVertexBlue *= var9;
            this.secondVertexRed *= var10;
            this.secondVertexGreen *= var10;
            this.secondVertexBlue *= var10;
            this.thirdVertexRed *= var11;
            this.thirdVertexGreen *= var11;
            this.thirdVertexBlue *= var11;
            this.fourthVertexRed *= var12;
            this.fourthVertexGreen *= var12;
            this.fourthVertexBlue *= var12;
            renderNorth(box, x, y, z, textures[4], 0xFFFFFF);
        }

        if ((renderMask & 32) == 0) {
            if (this.useSurroundingBrightness <= 0) {
                var9 = var10 = var11 = var12 = this.southBrightness;
            } else {
                ++x;
                this.southBottomBrightness = block.getLuminance(blockView, x, y - 1, z);
                this.southEastBrightness = block.getLuminance(blockView, x, y, z - 1);
                this.southWestBrightness = block.getLuminance(blockView, x, y, z + 1);
                this.southTopBrightness = block.getLuminance(blockView, x, y + 1, z);
                if (!this.bottomEastEdgeTranslucent && !this.northEastEdgeTranslucent) {
                    this.southEastBottomBrightness = this.southEastBrightness;
                } else {
                    this.southEastBottomBrightness = block.getLuminance(blockView, x, y - 1, z - 1);
                }

                if (!this.bottomEastEdgeTranslucent && !this.southEastEdgeTranslucent) {
                    this.southWestBottomBrightness = this.southWestBrightness;
                } else {
                    this.southWestBottomBrightness = block.getLuminance(blockView, x, y - 1, z + 1);
                }

                if (!this.topEastEdgeTranslucent && !this.northEastEdgeTranslucent) {
                    this.southEastTopBrightness = this.southEastBrightness;
                } else {
                    this.southEastTopBrightness = block.getLuminance(blockView, x, y + 1, z - 1);
                }

                if (!this.topEastEdgeTranslucent && !this.southEastEdgeTranslucent) {
                    this.southWestTopBrightness = this.southWestBrightness;
                } else {
                    this.southWestTopBrightness = block.getLuminance(blockView, x, y + 1, z + 1);
                }

                --x;
                var9 = (this.southBottomBrightness + this.southWestBottomBrightness + this.southBrightness + this.southWestBrightness) / 4.0F;
                var12 = (this.southBrightness + this.southWestBrightness + this.southTopBrightness + this.southWestTopBrightness) / 4.0F;
                var11 = (this.southEastBrightness + this.southBrightness + this.southEastTopBrightness + this.southTopBrightness) / 4.0F;
                var10 = (this.southEastBottomBrightness + this.southBottomBrightness + this.southEastBrightness + this.southBrightness) / 4.0F;
            }

            this.firstVertexRed = this.secondVertexRed = this.thirdVertexRed = this.fourthVertexRed = (var18 ? red : 1.0F) * 0.6F;
            this.firstVertexGreen = this.secondVertexGreen = this.thirdVertexGreen = this.fourthVertexGreen = (var18 ? green : 1.0F) * 0.6F;
            this.firstVertexBlue = this.secondVertexBlue = this.thirdVertexBlue = this.fourthVertexBlue = (var18 ? blue : 1.0F) * 0.6F;
            this.firstVertexRed *= var9;
            this.firstVertexGreen *= var9;
            this.firstVertexBlue *= var9;
            this.secondVertexRed *= var10;
            this.secondVertexGreen *= var10;
            this.secondVertexBlue *= var10;
            this.thirdVertexRed *= var11;
            this.thirdVertexGreen *= var11;
            this.thirdVertexBlue *= var11;
            this.fourthVertexRed *= var12;
            this.fourthVertexGreen *= var12;
            this.fourthVertexBlue *= var12;
            renderSouth(box, x, y, z, textures[5], 0xFFFFFF);
        }
    }

    void renderBottom(Box box, int x, int y, int z, int texture, int color){
        Tessellator tessellator = Tessellator.INSTANCE;
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

        if(this.useAo) {
            tessellator.color(this.firstVertexRed, this.firstVertexGreen, this.firstVertexBlue);
            tessellator.vertex(vMinX, vMinY, vMaxZ, uMin, vMax);
            tessellator.color(this.secondVertexRed, this.secondVertexGreen, this.secondVertexBlue);
            tessellator.vertex(vMinX, vMinY, vMinZ, uMin, vMin);
            tessellator.color(this.thirdVertexRed, this.thirdVertexGreen, this.thirdVertexBlue);
            tessellator.vertex(vMaxX, vMinY, vMinZ, uMax, vMin);
            tessellator.color(this.fourthVertexRed, this.fourthVertexGreen, this.fourthVertexBlue);
            tessellator.vertex(vMaxX, vMinY, vMaxZ, uMax, vMax);
        } else {
            tessellator.vertex(vMinX, vMinY, vMaxZ, uMin, vMax);
            tessellator.vertex(vMinX, vMinY, vMinZ, uMin, vMin);
            tessellator.vertex(vMaxX, vMinY, vMinZ, uMax, vMin);
            tessellator.vertex(vMaxX, vMinY, vMaxZ, uMax, vMax);
        }
    }

    void renderTop(Box box, int x, int y, int z, int texture, int color){
        Tessellator tessellator = Tessellator.INSTANCE;

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

        if(this.useAo){
            tessellator.color(this.firstVertexRed, this.firstVertexGreen, this.firstVertexBlue);
            tessellator.vertex(vMaxX, vMaxY, vMaxZ, uMax, vMax);
            tessellator.color(this.secondVertexRed, this.secondVertexGreen, this.secondVertexBlue);
            tessellator.vertex(vMaxX, vMaxY, vMinZ, uMax, vMin);
            tessellator.color(this.thirdVertexRed, this.thirdVertexGreen, this.thirdVertexBlue);
            tessellator.vertex(vMinX, vMaxY, vMinZ, uMin, vMin);
            tessellator.color(this.fourthVertexRed, this.fourthVertexGreen, this.fourthVertexBlue);
            tessellator.vertex(vMinX, vMaxY, vMaxZ, uMin, vMax);
        } else {
            tessellator.vertex(vMaxX, vMaxY, vMaxZ, uMax, vMax);
            tessellator.vertex(vMaxX, vMaxY, vMinZ, uMax, vMin);
            tessellator.vertex(vMinX, vMaxY, vMinZ, uMin, vMin);
            tessellator.vertex(vMinX, vMaxY, vMaxZ, uMin, vMax);
        }
    }

    void renderEast(Box box, int x, int y, int z, int texture, int color) {
        Tessellator tessellator = Tessellator.INSTANCE;

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

        if(this.useAo){
            tessellator.color(this.firstVertexRed, this.firstVertexGreen, this.firstVertexBlue);
            tessellator.vertex(vMinX, vMaxY, vMinZ, uMin, vMin);
            tessellator.color(this.secondVertexRed, this.secondVertexGreen, this.secondVertexBlue);
            tessellator.vertex(vMaxX, vMaxY, vMinZ, uMax, vMin);
            tessellator.color(this.thirdVertexRed, this.thirdVertexGreen, this.thirdVertexBlue);
            tessellator.vertex(vMaxX, vMinY, vMinZ, uMax, vMax);
            tessellator.color(this.fourthVertexRed, this.fourthVertexGreen, this.fourthVertexBlue);
            tessellator.vertex(vMinX, vMinY, vMinZ, uMin, vMax);
        } else {
            tessellator.vertex(vMinX, vMaxY, vMinZ, uMin, vMin);
            tessellator.vertex(vMaxX, vMaxY, vMinZ, uMax, vMin);
            tessellator.vertex(vMaxX, vMinY, vMinZ, uMax, vMax);
            tessellator.vertex(vMinX, vMinY, vMinZ, uMin, vMax);
        }
    }

    void renderWest(Box box, int x, int y, int z, int texture, int color) {
        Tessellator tessellator = Tessellator.INSTANCE;

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

        if(this.useAo){
            tessellator.color(this.fourthVertexRed, this.fourthVertexGreen, this.fourthVertexBlue);
            tessellator.vertex(vMaxX, vMaxY, vMaxZ, uMax, vMin);
            tessellator.color(this.firstVertexRed, this.firstVertexGreen, this.firstVertexBlue);
            tessellator.vertex(vMinX, vMaxY, vMaxZ, uMin, vMin);
            tessellator.color(this.secondVertexRed, this.secondVertexGreen, this.secondVertexBlue);
            tessellator.vertex(vMinX, vMinY, vMaxZ, uMin, vMax);
            tessellator.color(this.thirdVertexRed, this.thirdVertexGreen, this.thirdVertexBlue);
            tessellator.vertex(vMaxX, vMinY, vMaxZ, uMax, vMax);
        } else {
            tessellator.vertex(vMaxX, vMaxY, vMaxZ, uMax, vMin);
            tessellator.vertex(vMinX, vMaxY, vMaxZ, uMin, vMin);
            tessellator.vertex(vMinX, vMinY, vMaxZ, uMin, vMax);
            tessellator.vertex(vMaxX, vMinY, vMaxZ, uMax, vMax);
        }
    }

    void renderNorth(Box box, int x, int y, int z, int texture, int color) {
        Tessellator tessellator = Tessellator.INSTANCE;

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

        if(this.useAo){
            tessellator.color(this.firstVertexRed, this.firstVertexGreen, this.firstVertexBlue);
            tessellator.vertex(vMinX, vMaxY, vMaxZ, uMin, vMin);
            tessellator.color(this.secondVertexRed, this.secondVertexGreen, this.secondVertexBlue);
            tessellator.vertex(vMinX, vMaxY, vMinZ, uMax, vMin);
            tessellator.color(this.thirdVertexRed, this.thirdVertexGreen, this.thirdVertexBlue);
            tessellator.vertex(vMinX, vMinY, vMinZ, uMax, vMax);
            tessellator.color(this.fourthVertexRed, this.fourthVertexGreen, this.fourthVertexBlue);
            tessellator.vertex(vMinX, vMinY, vMaxZ, uMin, vMax);
        } else {
            tessellator.vertex(vMinX, vMaxY, vMaxZ, uMin, vMin);
            tessellator.vertex(vMinX, vMaxY, vMinZ, uMax, vMin);
            tessellator.vertex(vMinX, vMinY, vMinZ, uMax, vMax);
            tessellator.vertex(vMinX, vMinY, vMaxZ, uMin, vMax);
        }
    }

    void renderSouth(Box box, int x, int y, int z, int texture, int color) {
        Tessellator tessellator = Tessellator.INSTANCE;

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

        if(this.useAo){
            tessellator.color(this.thirdVertexRed, this.thirdVertexGreen, this.thirdVertexBlue);
            tessellator.vertex(vMaxX, vMaxY, vMinZ, uMin, vMin);
            tessellator.color(this.fourthVertexRed, this.fourthVertexGreen, this.fourthVertexBlue);
            tessellator.vertex(vMaxX, vMaxY, vMaxZ, uMax, vMin);
            tessellator.color(this.firstVertexRed, this.firstVertexGreen, this.firstVertexBlue);
            tessellator.vertex(vMaxX, vMinY, vMaxZ, uMax, vMax);
            tessellator.color(this.secondVertexRed, this.secondVertexGreen, this.secondVertexBlue);
            tessellator.vertex(vMaxX, vMinY, vMinZ, uMin, vMax);
        } else {
            tessellator.vertex(vMaxX, vMaxY, vMinZ, uMin, vMin);
            tessellator.vertex(vMaxX, vMaxY, vMaxZ, uMax, vMin);
            tessellator.vertex(vMaxX, vMinY, vMaxZ, uMax, vMax);
            tessellator.vertex(vMaxX, vMinY, vMinZ, uMin, vMax);
        }
    }
}
