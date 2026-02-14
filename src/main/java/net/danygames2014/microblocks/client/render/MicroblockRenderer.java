package net.danygames2014.microblocks.client.render;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.danygames2014.microblocks.multipart.MicroblockMultipartComponent;
import net.danygames2014.microblocks.multipart.PlacementSlot;
import net.danygames2014.microblocks.multipart.model.MicroblockModel;
import net.danygames2014.microblocks.util.ShrinkHelper;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.util.math.Box;
import net.minecraft.world.BlockView;
import net.modificationstation.stationapi.api.client.StationRenderAPI;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlas;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases;
import org.lwjgl.opengl.GL11;

public class MicroblockRenderer {
    public static final MicroblockRenderer INSTANCE = new MicroblockRenderer();

    public boolean useAo;
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

    private double overrideMinX = 0.0F;
    private double overrideMaxX = 0.0F;
    private double overrideMinY = 0.0F;
    private double overrideMaxY = 0.0F;
    private double overrideMinZ = 0.0F;
    private double overrideMaxZ = 0.0F;
    private boolean overrideBottom = false;
    private boolean overrideTop = false;
    private boolean overrideEast = false;
    private boolean overrideWest = false;
    private boolean overrideNorth = false;
    private boolean overrideSouth = false;


    public void renderMicroblock(BlockView blockView, MicroblockMultipartComponent component, BlockRenderManager blockRenderManager){
        MicroblockModel model = component.getMicroblockModel();
        Tessellator.INSTANCE.color(1f, 1f, 1f, 1f);
        ObjectArrayList<Box> boxes = model.getBoxesForSlot(component.slot, component.getSize(), component.x, component.y, component.z);
        ObjectArrayList<Box> clippedBoxes = component.getClippedBoxes(boxes);

        for(int i = 0; i < clippedBoxes.size(); i++){
            int localMask = 0;
            Box localBox = clippedBoxes.get(i).offset(-component.x, -component.y, -component.z);

            overrideBottom = false;
            overrideTop = false;
            overrideEast = false;
            overrideWest = false;
            overrideNorth = false;
            overrideSouth = false;

            if(clippedBoxes.size() == boxes.size()){
                setOverrideBox(boxes.get(i).offset(-component.x, -component.y, -component.z));
            }

            if(component.slot.ordinal() < 6 && !component.isTransparent()){
                switch (component.slot){
                    case FACE_POS_Y -> overrideBottom = true;
                    case FACE_NEG_Y -> overrideTop = true;
                    case FACE_POS_Z -> overrideEast = true;
                    case FACE_NEG_Z -> overrideWest = true;
                    case FACE_POS_X -> overrideNorth = true;
                    case FACE_NEG_X -> overrideSouth = true;
                }
            }

            if ((component.renderMask & 1) != 0 && localBox.minY <= 0.0) localMask |= 1;  // Bottom
            if ((component.renderMask & 2) != 0 && localBox.maxY >= 1.0) localMask |= 2;  // Top
            if ((component.renderMask & 4) != 0 && localBox.minZ <= 0.0) localMask |= 4;  // East
            if ((component.renderMask & 8) != 0 && localBox.maxZ >= 1.0) localMask |= 8;  // West
            if ((component.renderMask & 16) != 0 && localBox.minX <= 0.0) localMask |= 16; // North
            if ((component.renderMask & 32) != 0 && localBox.maxX >= 1.0) localMask |= 32; // South

            int color = component.block.getColorMultiplier(blockView, component.x, component.y, component.z);

            float r = (float)(color >> 16 & 255) / 255.0F;
            float g = (float)(color >> 8 & 255) / 255.0F;
            float b = (float)(color & 255) / 255.0F;
            float a = (float)(color >> 24 & 255) / 255.0F;

            renderBox(blockView, component.block, localBox, component.x, component.y, component.z, r, g, b, new int[]{component.block.getTexture(0, component.meta), component.block.getTexture(1, component.meta), component.block.getTexture(2, component.meta), component.block.getTexture(3, component.meta), component.block.getTexture(4, component.meta), component.block.getTexture(5, component.meta)}, new int[]{0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF}, localMask);
        }
        overrideBottom = false;
        overrideTop = false;
        overrideEast = false;
        overrideWest = false;
        overrideNorth = false;
        overrideSouth = false;
    }

    public void renderMicroblockPreview(MicroblockModel model, PlacementSlot slot, Block block, int meta, int size, int x, int y, int z){
        Tessellator tessellator = Tessellator.INSTANCE;

        tessellator.startQuads();

        StationRenderAPI.getBakedModelManager().getAtlas(Atlases.GAME_ATLAS_TEXTURE).bindTexture();

        int color = block.getColor(meta);

        float r = (float)(color >> 16 & 255) / 255.0F;
        float g = (float)(color >> 8 & 255) / 255.0F;
        float b = (float)(color & 255) / 255.0F;

        tessellator.color(r, g, b, 0.4F);
        this.useAo = false;

        GL11.glPolygonOffset(-1.0F, -1.0F);
        GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);

        ObjectArrayList<Box> boxes = model.getBoxesForSlot(slot, size, 0, 0, 0);
        for(Box box : boxes){
            renderBottom(box, x, y, z, block.getTexture(0, meta), 0xFFFFFF);
            renderTop(box, x, y, z, block.getTexture(1, meta), 0xFFFFFF);
            renderEast(box, x, y, z, block.getTexture(2, meta), 0xFFFFFF);
            renderWest(box, x, y, z, block.getTexture(3, meta), 0xFFFFFF);
            renderNorth(box, x, y, z, block.getTexture(4, meta), 0xFFFFFF);
            renderSouth(box, x, y, z, block.getTexture(5, meta), 0xFFFFFF);
        }

        tessellator.draw();

        GL11.glPolygonOffset(0.0F, 0.0F);
        GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
    }

    public void renderBox(BlockView blockView, Block block, Box box, int x, int y, int z, float red, float green, float blue, int[] textures, int[] sideColors, int renderMask){
        this.useAo = true;
        float var9;
        float var10;
        float var11;
        float var12;
        boolean bottomColor = textures[0] != 3;
        boolean topColor = textures[1] != 3;
        boolean eastColor = textures[2] != 3;
        boolean westColor = textures[3] != 3;
        boolean northColor = textures[4] != 3;
        boolean southColor = textures[5] != 3;

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

            this.firstVertexRed = this.secondVertexRed = this.thirdVertexRed = this.fourthVertexRed = (bottomColor ? red : 1.0F) * 0.5F;
            this.firstVertexGreen = this.secondVertexGreen = this.thirdVertexGreen = this.fourthVertexGreen = (bottomColor ? green : 1.0F) * 0.5F;
            this.firstVertexBlue = this.secondVertexBlue = this.thirdVertexBlue = this.fourthVertexBlue = (bottomColor ? blue : 1.0F) * 0.5F;
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

            this.firstVertexRed = this.secondVertexRed = this.thirdVertexRed = this.fourthVertexRed = topColor ? red : 1.0F;
            this.firstVertexGreen = this.secondVertexGreen = this.thirdVertexGreen = this.fourthVertexGreen = topColor ? green : 1.0F;
            this.firstVertexBlue = this.secondVertexBlue = this.thirdVertexBlue = this.fourthVertexBlue = topColor ? blue : 1.0F;
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

            this.firstVertexRed = this.secondVertexRed = this.thirdVertexRed = this.fourthVertexRed = (eastColor ? red : 1.0F) * 0.8F;
            this.firstVertexGreen = this.secondVertexGreen = this.thirdVertexGreen = this.fourthVertexGreen = (eastColor ? green : 1.0F) * 0.8F;
            this.firstVertexBlue = this.secondVertexBlue = this.thirdVertexBlue = this.fourthVertexBlue = (eastColor ? blue : 1.0F) * 0.8F;
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

            if (Minecraft.INSTANCE.options.fancyGraphics && textures[2] == 3) {
                this.firstVertexRed *= red;
                this.secondVertexRed *= red;
                this.thirdVertexRed *= red;
                this.fourthVertexRed *= red;
                this.firstVertexGreen *= green;
                this.secondVertexGreen *= green;
                this.thirdVertexGreen *= green;
                this.fourthVertexGreen *= green;
                this.firstVertexBlue *= blue;
                this.secondVertexBlue *= blue;
                this.thirdVertexBlue *= blue;
                this.fourthVertexBlue *= blue;
                renderEast(box, x, y, z, 38, 0xFFFFFF);
            }
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

            this.firstVertexRed = this.secondVertexRed = this.thirdVertexRed = this.fourthVertexRed = (westColor ? red : 1.0F) * 0.8F;
            this.firstVertexGreen = this.secondVertexGreen = this.thirdVertexGreen = this.fourthVertexGreen = (westColor ? green : 1.0F) * 0.8F;
            this.firstVertexBlue = this.secondVertexBlue = this.thirdVertexBlue = this.fourthVertexBlue = (westColor ? blue : 1.0F) * 0.8F;
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

            if (Minecraft.INSTANCE.options.fancyGraphics && textures[3] == 3) {
                this.firstVertexRed *= red;
                this.secondVertexRed *= red;
                this.thirdVertexRed *= red;
                this.fourthVertexRed *= red;
                this.firstVertexGreen *= green;
                this.secondVertexGreen *= green;
                this.thirdVertexGreen *= green;
                this.fourthVertexGreen *= green;
                this.firstVertexBlue *= blue;
                this.secondVertexBlue *= blue;
                this.thirdVertexBlue *= blue;
                this.fourthVertexBlue *= blue;
                renderWest(box, x, y, z, 38, 0xFFFFFF);
            }
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

            this.firstVertexRed = this.secondVertexRed = this.thirdVertexRed = this.fourthVertexRed = (northColor ? red : 1.0F) * 0.6F;
            this.firstVertexGreen = this.secondVertexGreen = this.thirdVertexGreen = this.fourthVertexGreen = (northColor ? green : 1.0F) * 0.6F;
            this.firstVertexBlue = this.secondVertexBlue = this.thirdVertexBlue = this.fourthVertexBlue = (northColor ? blue : 1.0F) * 0.6F;
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

            if (Minecraft.INSTANCE.options.fancyGraphics && textures[4] == 3) {
                this.firstVertexRed *= red;
                this.secondVertexRed *= red;
                this.thirdVertexRed *= red;
                this.fourthVertexRed *= red;
                this.firstVertexGreen *= green;
                this.secondVertexGreen *= green;
                this.thirdVertexGreen *= green;
                this.fourthVertexGreen *= green;
                this.firstVertexBlue *= blue;
                this.secondVertexBlue *= blue;
                this.thirdVertexBlue *= blue;
                this.fourthVertexBlue *= blue;
                renderNorth(box, x, y, z, 38, 0xFFFFFF);
            }
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

            this.firstVertexRed = this.secondVertexRed = this.thirdVertexRed = this.fourthVertexRed = (southColor ? red : 1.0F) * 0.6F;
            this.firstVertexGreen = this.secondVertexGreen = this.thirdVertexGreen = this.fourthVertexGreen = (southColor ? green : 1.0F) * 0.6F;
            this.firstVertexBlue = this.secondVertexBlue = this.thirdVertexBlue = this.fourthVertexBlue = (southColor ? blue : 1.0F) * 0.6F;
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

            if (Minecraft.INSTANCE.options.fancyGraphics && textures[5] == 3) {
                this.firstVertexRed *= red;
                this.secondVertexRed *= red;
                this.thirdVertexRed *= red;
                this.fourthVertexRed *= red;
                this.firstVertexGreen *= green;
                this.secondVertexGreen *= green;
                this.thirdVertexGreen *= green;
                this.fourthVertexGreen *= green;
                this.firstVertexBlue *= blue;
                this.secondVertexBlue *= blue;
                this.thirdVertexBlue *= blue;
                this.fourthVertexBlue *= blue;
                renderSouth(box, x, y, z, 38, 0xFFFFFF);
            }
        }
    }

    public void renderBottom(Box box, int x, int y, int z, int texture, int color){
        Tessellator tessellator = Tessellator.INSTANCE;

        double boxMinX = overrideBottom ? overrideMinX : box.minX;
        double boxMinY = overrideBottom ? overrideMinY : box.minY;
        double boxMinZ = overrideBottom ? overrideMinZ : box.minZ;
        double boxMaxX = overrideBottom ? overrideMaxX : box.maxX;
        double boxMaxY = overrideBottom ? overrideMaxY : box.maxY;
        double boxMaxZ = overrideBottom ? overrideMaxZ : box.maxZ;

        double vMinY = y + boxMinY;

        double vMinX = x + boxMinX;
        double vMaxX = x + boxMaxX;
        double vMinZ = z + boxMinZ;
        double vMaxZ = z + boxMaxZ;

        Atlas.Sprite sprite = Atlases.getTerrain().getTexture(Math.abs(texture));
        double sMinU = sprite.getStartU();
        double sMaxU = sprite.getEndU();
        double sMinV = sprite.getStartV();
        double sMaxV = sprite.getEndV();

        double sWidth = sMaxU - sMinU;
        double sHeight = sMaxV - sMinV;

        double uMin  = sMinU + (boxMinX * sWidth);
        double uMax = sMinU + (boxMaxX * sWidth);
        double vMin = sMinV + (boxMinZ * sHeight);
        double vMax = sMinV + (boxMaxZ * sHeight);

        if(this.useAo) {
            float minX = (float)boxMinX;
            float maxX = (float)boxMaxX;
            float minZ = (float)boxMinZ;
            float maxZ = (float)boxMaxZ;

            float[] c1 = interpolateBottomColor(minX, maxZ);
            tessellator.color(c1[0], c1[1], c1[2]);
            tessellator.vertex(vMinX, vMinY, vMaxZ, uMin, vMax);

            float[] c2 = interpolateBottomColor(minX, minZ);
            tessellator.color(c2[0], c2[1], c2[2]);
            tessellator.vertex(vMinX, vMinY, vMinZ, uMin, vMin);

            float[] c3 = interpolateBottomColor(maxX, minZ);
            tessellator.color(c3[0], c3[1], c3[2]);
            tessellator.vertex(vMaxX, vMinY, vMinZ, uMax, vMin);

            float[] c4 = interpolateBottomColor(maxX, maxZ);
            tessellator.color(c4[0], c4[1], c4[2]);
            tessellator.vertex(vMaxX, vMinY, vMaxZ, uMax, vMax);
        } else {
            tessellator.vertex(vMinX, vMinY, vMaxZ, uMin, vMax);
            tessellator.vertex(vMinX, vMinY, vMinZ, uMin, vMin);
            tessellator.vertex(vMaxX, vMinY, vMinZ, uMax, vMin);
            tessellator.vertex(vMaxX, vMinY, vMaxZ, uMax, vMax);
        }
    }

    public void renderTop(Box box, int x, int y, int z, int texture, int color){
        Tessellator tessellator = Tessellator.INSTANCE;

        double boxMinX = overrideTop ? overrideMinX : box.minX;
        double boxMinY = overrideTop ? overrideMinY : box.minY;
        double boxMinZ = overrideTop ? overrideMinZ : box.minZ;
        double boxMaxX = overrideTop ? overrideMaxX : box.maxX;
        double boxMaxY = overrideTop ? overrideMaxY : box.maxY;
        double boxMaxZ = overrideTop ? overrideMaxZ : box.maxZ;

        double vMaxY = y + boxMaxY;

        double vMinX = x + boxMinX;
        double vMaxX = x + boxMaxX;
        double vMinZ = z + boxMinZ;
        double vMaxZ = z + boxMaxZ;

        Atlas.Sprite sprite = Atlases.getTerrain().getTexture(Math.abs(texture));
        double sMinU = sprite.getStartU();
        double sMaxU = sprite.getEndU();
        double sMinV = sprite.getStartV();
        double sMaxV = sprite.getEndV();

        double sWidth = sMaxU - sMinU;
        double sHeight = sMaxV - sMinV;

        double uMin = sMinU + (boxMinX * sWidth);
        double uMax = sMinU + (boxMaxX * sWidth);
        double vMin = sMinV + (boxMinZ * sHeight);
        double vMax = sMinV + (boxMaxZ * sHeight);

        if(this.useAo){
            float minX = (float)boxMinX;
            float maxX = (float)boxMaxX;
            float minZ = (float)boxMinZ;
            float maxZ = (float)boxMaxZ;

            float[] c1 = interpolateTopColor(maxX, maxZ);
            tessellator.color(c1[0], c1[1], c1[2]);
            tessellator.vertex(vMaxX, vMaxY, vMaxZ, uMax, vMax);

            float[] c2 = interpolateTopColor(maxX, minZ);
            tessellator.color(c2[0], c2[1], c2[2]);
            tessellator.vertex(vMaxX, vMaxY, vMinZ, uMax, vMin);

            float[] c3 = interpolateTopColor(minX, minZ);
            tessellator.color(c3[0], c3[1], c3[2]);
            tessellator.vertex(vMinX, vMaxY, vMinZ, uMin, vMin);

            float[] c4 = interpolateTopColor(minX, maxZ);
            tessellator.color(c4[0], c4[1], c4[2]);
            tessellator.vertex(vMinX, vMaxY, vMaxZ, uMin, vMax);
        } else {
            tessellator.vertex(vMaxX, vMaxY, vMaxZ, uMax, vMax);
            tessellator.vertex(vMaxX, vMaxY, vMinZ, uMax, vMin);
            tessellator.vertex(vMinX, vMaxY, vMinZ, uMin, vMin);
            tessellator.vertex(vMinX, vMaxY, vMaxZ, uMin, vMax);
        }
    }

    public void renderEast(Box box, int x, int y, int z, int texture, int color) {
        Tessellator tessellator = Tessellator.INSTANCE;

        double boxMinX = overrideEast ? overrideMinX : box.minX;
        double boxMinY = overrideEast ? overrideMinY : box.minY;
        double boxMinZ = overrideEast ? overrideMinZ : box.minZ;
        double boxMaxX = overrideEast ? overrideMaxX : box.maxX;
        double boxMaxY = overrideEast ? overrideMaxY : box.maxY;
        double boxMaxZ = overrideEast ? overrideMaxZ : box.maxZ;

        double vMinZ = z + boxMinZ;

        double vMinX = x + boxMinX;
        double vMaxX = x + boxMaxX;
        double vMinY = y + boxMinY;
        double vMaxY = y + boxMaxY;

        Atlas.Sprite sprite = Atlases.getTerrain().getTexture(Math.abs(texture));
        double sMinU = sprite.getStartU();
        double sMaxU = sprite.getEndU();
        double sMinV = sprite.getStartV();
        double sMaxV = sprite.getEndV();

        double sWidth = sMaxU - sMinU;
        double sHeight = sMaxV - sMinV;

        double uMin = sMinU + ((1.0 - boxMinX) * sWidth);
        double uMax = sMinU + ((1.0 - boxMaxX) * sWidth);

        double vMin = sMinV + ((1.0 - boxMaxY) * sHeight);
        double vMax = sMinV + ((1.0 - boxMinY) * sHeight);

        if(this.useAo){
            float minX = (float)boxMinX;
            float maxX = (float)boxMaxX;
            float minY = (float)boxMinY;
            float maxY = (float)boxMaxY;

            float[] c1 = interpolateEastColor(minX, maxY);
            tessellator.color(c1[0], c1[1], c1[2]);
            tessellator.vertex(vMinX, vMaxY, vMinZ, uMin, vMin);

            float[] c2 = interpolateEastColor(maxX, maxY);
            tessellator.color(c2[0], c2[1], c2[2]);
            tessellator.vertex(vMaxX, vMaxY, vMinZ, uMax, vMin);

            float[] c3 = interpolateEastColor(maxX, minY);
            tessellator.color(c3[0], c3[1], c3[2]);
            tessellator.vertex(vMaxX, vMinY, vMinZ, uMax, vMax);

            float[] c4 = interpolateEastColor(minX, minY);
            tessellator.color(c4[0], c4[1], c4[2]);
            tessellator.vertex(vMinX, vMinY, vMinZ, uMin, vMax);
        } else {
            tessellator.vertex(vMinX, vMaxY, vMinZ, uMin, vMin);
            tessellator.vertex(vMaxX, vMaxY, vMinZ, uMax, vMin);
            tessellator.vertex(vMaxX, vMinY, vMinZ, uMax, vMax);
            tessellator.vertex(vMinX, vMinY, vMinZ, uMin, vMax);
        }
    }

    public void renderWest(Box box, int x, int y, int z, int texture, int color) {
        Tessellator tessellator = Tessellator.INSTANCE;

        double boxMinX = overrideWest ? overrideMinX : box.minX;
        double boxMinY = overrideWest ? overrideMinY : box.minY;
        double boxMinZ = overrideWest ? overrideMinZ : box.minZ;
        double boxMaxX = overrideWest ? overrideMaxX : box.maxX;
        double boxMaxY = overrideWest ? overrideMaxY : box.maxY;
        double boxMaxZ = overrideWest ? overrideMaxZ : box.maxZ;

        double vMaxZ = z + boxMaxZ;

        double vMinX = x + boxMinX;
        double vMaxX = x + boxMaxX;
        double vMinY = y + boxMinY;
        double vMaxY = y + boxMaxY;

        Atlas.Sprite sprite = Atlases.getTerrain().getTexture(Math.abs(texture));
        double sMinU = sprite.getStartU();
        double sMaxU = sprite.getEndU();
        double sMinV = sprite.getStartV();
        double sMaxV = sprite.getEndV();

        double sWidth = sMaxU - sMinU;
        double sHeight = sMaxV - sMinV;

        double uMin = sMinU + (boxMinX * sWidth);
        double uMax = sMinU + (boxMaxX * sWidth);

        double vMin = sMinV + ((1.0 - boxMaxY) * sHeight);
        double vMax = sMinV + ((1.0 - boxMinY) * sHeight);

        if(this.useAo){
            float minX = (float)boxMinX;
            float maxX = (float)boxMaxX;
            float minY = (float)boxMinY;
            float maxY = (float)boxMaxY;

            float[] c1 = interpolateWestColor(maxX, maxY);
            tessellator.color(c1[0], c1[1], c1[2]);
            tessellator.vertex(vMaxX, vMaxY, vMaxZ, uMax, vMin);

            float[] c2 = interpolateWestColor(minX, maxY);
            tessellator.color(c2[0], c2[1], c2[2]);
            tessellator.vertex(vMinX, vMaxY, vMaxZ, uMin, vMin);

            float[] c3 = interpolateWestColor(minX, minY);
            tessellator.color(c3[0], c3[1], c3[2]);
            tessellator.vertex(vMinX, vMinY, vMaxZ, uMin, vMax);

            float[] c4 = interpolateWestColor(maxX, minY);
            tessellator.color(c4[0], c4[1], c4[2]);
            tessellator.vertex(vMaxX, vMinY, vMaxZ, uMax, vMax);
        } else {
            tessellator.vertex(vMaxX, vMaxY, vMaxZ, uMax, vMin);
            tessellator.vertex(vMinX, vMaxY, vMaxZ, uMin, vMin);
            tessellator.vertex(vMinX, vMinY, vMaxZ, uMin, vMax);
            tessellator.vertex(vMaxX, vMinY, vMaxZ, uMax, vMax);
        }
    }

    public void renderNorth(Box box, int x, int y, int z, int texture, int color) {
        Tessellator tessellator = Tessellator.INSTANCE;

        double boxMinX = overrideNorth ? overrideMinX : box.minX;
        double boxMinY = overrideNorth ? overrideMinY : box.minY;
        double boxMinZ = overrideNorth ? overrideMinZ : box.minZ;
        double boxMaxX = overrideNorth ? overrideMaxX : box.maxX;
        double boxMaxY = overrideNorth ? overrideMaxY : box.maxY;
        double boxMaxZ = overrideNorth ? overrideMaxZ : box.maxZ;

        double vMinX = x + boxMinX;

        double vMinY = y + boxMinY;
        double vMaxY = y + boxMaxY;
        double vMinZ = z + boxMinZ;
        double vMaxZ = z + boxMaxZ;

        Atlas.Sprite sprite = Atlases.getTerrain().getTexture(Math.abs(texture));
        double sMinU = sprite.getStartU();
        double sMaxU = sprite.getEndU();
        double sMinV = sprite.getStartV();
        double sMaxV = sprite.getEndV();

        double sWidth = sMaxU - sMinU;
        double sHeight = sMaxV - sMinV;

        double uMin = sMinU + (boxMaxZ * sWidth);
        double uMax = sMinU + (boxMinZ * sWidth);

        double vMin = sMinV + ((1.0 - boxMaxY) * sHeight);
        double vMax = sMinV + ((1.0 - boxMinY) * sHeight);

        if(this.useAo){
            float minZ = (float)boxMinZ;
            float maxZ = (float)boxMaxZ;
            float minY = (float)boxMinY;
            float maxY = (float)boxMaxY;

            float[] c1 = interpolateNorthColor(maxZ, maxY);
            tessellator.color(c1[0], c1[1], c1[2]);
            tessellator.vertex(vMinX, vMaxY, vMaxZ, uMin, vMin);

            float[] c2 = interpolateNorthColor(minZ, maxY);
            tessellator.color(c2[0], c2[1], c2[2]);
            tessellator.vertex(vMinX, vMaxY, vMinZ, uMax, vMin);

            float[] c3 = interpolateNorthColor(minZ, minY);
            tessellator.color(c3[0], c3[1], c3[2]);
            tessellator.vertex(vMinX, vMinY, vMinZ, uMax, vMax);

            float[] c4 = interpolateNorthColor(maxZ, minY);
            tessellator.color(c4[0], c4[1], c4[2]);
            tessellator.vertex(vMinX, vMinY, vMaxZ, uMin, vMax);
        } else {
            tessellator.vertex(vMinX, vMaxY, vMaxZ, uMin, vMin);
            tessellator.vertex(vMinX, vMaxY, vMinZ, uMax, vMin);
            tessellator.vertex(vMinX, vMinY, vMinZ, uMax, vMax);
            tessellator.vertex(vMinX, vMinY, vMaxZ, uMin, vMax);
        }
    }

    public void renderSouth(Box box, int x, int y, int z, int texture, int color) {
        Tessellator tessellator = Tessellator.INSTANCE;

        double boxMinX = overrideSouth ? overrideMinX : box.minX;
        double boxMinY = overrideSouth ? overrideMinY : box.minY;
        double boxMinZ = overrideSouth ? overrideMinZ : box.minZ;
        double boxMaxX = overrideSouth ? overrideMaxX : box.maxX;
        double boxMaxY = overrideSouth ? overrideMaxY : box.maxY;
        double boxMaxZ = overrideSouth ? overrideMaxZ : box.maxZ;

        double vMaxX = x + boxMaxX;

        double vMinY = y + boxMinY;
        double vMaxY = y + boxMaxY;
        double vMinZ = z + boxMinZ;
        double vMaxZ = z + boxMaxZ;

        Atlas.Sprite sprite = Atlases.getTerrain().getTexture(Math.abs(texture));
        double sMinU = sprite.getStartU();
        double sMaxU = sprite.getEndU();
        double sMinV = sprite.getStartV();
        double sMaxV = sprite.getEndV();

        double sWidth = sMaxU - sMinU;
        double sHeight = sMaxV - sMinV;

        double uMin = sMinU + ((1 - boxMinZ) * sWidth);
        double uMax = sMinU + ((1 - boxMaxZ) * sWidth);

        double vMin = sMinV + ( (1.0 - boxMaxY) * sHeight);
        double vMax = sMinV + ( (1.0 - boxMinY) * sHeight);

        if(this.useAo){
            float minZ = (float)boxMinZ;
            float maxZ = (float)boxMaxZ;
            float minY = (float)boxMinY;
            float maxY = (float)boxMaxY;

            float[] c1 = interpolateSouthColor(minZ, maxY);
            tessellator.color(c1[0], c1[1], c1[2]);
            tessellator.vertex(vMaxX, vMaxY, vMinZ, uMin, vMin);

            float[] c2 = interpolateSouthColor(maxZ, maxY);
            tessellator.color(c2[0], c2[1], c2[2]);
            tessellator.vertex(vMaxX, vMaxY, vMaxZ, uMax, vMin);

            float[] c3 = interpolateSouthColor(maxZ, minY);
            tessellator.color(c3[0], c3[1], c3[2]);
            tessellator.vertex(vMaxX, vMinY, vMaxZ, uMax, vMax);

            float[] c4 = interpolateSouthColor(minZ, minY);
            tessellator.color(c4[0], c4[1], c4[2]);
            tessellator.vertex(vMaxX, vMinY, vMinZ, uMin, vMax);
        } else {
            tessellator.vertex(vMaxX, vMaxY, vMinZ, uMin, vMin);
            tessellator.vertex(vMaxX, vMaxY, vMaxZ, uMax, vMin);
            tessellator.vertex(vMaxX, vMinY, vMaxZ, uMax, vMax);
            tessellator.vertex(vMaxX, vMinY, vMinZ, uMin, vMax);
        }
    }

    private float[] interpolateTopColor(float x, float z) {
        float r = lerp2d(this.thirdVertexRed, this.secondVertexRed, this.fourthVertexRed, this.firstVertexRed, x, z);
        float g = lerp2d(this.thirdVertexGreen, this.secondVertexGreen, this.fourthVertexGreen, this.firstVertexGreen, x, z);
        float b = lerp2d(this.thirdVertexBlue, this.secondVertexBlue, this.fourthVertexBlue, this.firstVertexBlue, x, z);

        return new float[]{r, g, b};
    }

    private float[] interpolateBottomColor(float x, float z) {
        float r = lerp2d(this.secondVertexRed, this.thirdVertexRed, this.firstVertexRed, this.fourthVertexRed, x, z);
        float g = lerp2d(this.secondVertexGreen, this.thirdVertexGreen, this.firstVertexGreen, this.fourthVertexGreen, x, z);
        float b = lerp2d(this.secondVertexBlue, this.thirdVertexBlue, this.firstVertexBlue, this.fourthVertexBlue, x, z);

        return new float[]{r, g, b};
    }

    private float[] interpolateWestColor(float x, float y) {
        float r = lerp2d(this.secondVertexRed, this.thirdVertexRed, this.firstVertexRed, this.fourthVertexRed, x, y);
        float g = lerp2d(this.secondVertexGreen, this.thirdVertexGreen, this.firstVertexGreen, this.fourthVertexGreen, x, y);
        float b = lerp2d(this.secondVertexBlue, this.thirdVertexBlue, this.firstVertexBlue, this.fourthVertexBlue, x, y);

        return new float[]{r, g, b};
    }

    private float[] interpolateSouthColor(float z, float y) {
        float r = lerp2d(this.secondVertexRed, this.firstVertexRed, this.thirdVertexRed, this.fourthVertexRed, z, y);
        float g = lerp2d(this.secondVertexGreen, this.firstVertexGreen, this.thirdVertexGreen, this.fourthVertexGreen, z, y);
        float b = lerp2d(this.secondVertexBlue, this.firstVertexBlue, this.thirdVertexBlue, this.fourthVertexBlue, z, y);

        return new float[]{r, g, b};
    }

    private float[] interpolateNorthColor(float z, float y) {
        float r = lerp2d(this.thirdVertexRed, this.fourthVertexRed, this.secondVertexRed, this.firstVertexRed, z, y);
        float g = lerp2d(this.thirdVertexGreen, this.fourthVertexGreen, this.secondVertexGreen, this.firstVertexGreen, z, y);
        float b = lerp2d(this.thirdVertexBlue, this.fourthVertexBlue, this.secondVertexBlue, this.firstVertexBlue, z, y);

        return new float[]{r, g, b};
    }

    private float[] interpolateEastColor(float x, float y) {
        float r = lerp2d(this.fourthVertexRed, this.thirdVertexRed, this.firstVertexRed, this.secondVertexRed, x, y);
        float g = lerp2d(this.fourthVertexGreen, this.thirdVertexGreen, this.firstVertexGreen, this.secondVertexGreen, x, y);
        float b = lerp2d(this.fourthVertexBlue, this.thirdVertexBlue, this.firstVertexBlue, this.secondVertexBlue, x, y);

        return new float[]{r, g, b};
    }

    private float lerp2d(float c00, float c10, float c01, float c11, float tx, float tz) {
        // lerp horizontally across the bottom
        float x0 = c00 + (c10 - c00) * tx;
        // lerp horizontally across the top
        float x1 = c01 + (c11 - c01) * tx;
        // lerp vertically between the two results
        return x0 + (x1 - x0) * tz;
    }

    public void setOverrideBox(Box box){
        setOverrideBox(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ);
    }

    public void setOverrideBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        this.overrideMinX = minX;
        this.overrideMinY = minY;
        this.overrideMinZ = minZ;
        this.overrideMaxX = maxX;
        this.overrideMaxY = maxY;
        this.overrideMaxZ = maxZ;
    }
}
