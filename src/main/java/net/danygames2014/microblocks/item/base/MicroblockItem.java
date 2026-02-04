package net.danygames2014.microblocks.item.base;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.danygames2014.microblocks.client.render.CustomItemRenderer;
import net.danygames2014.microblocks.client.render.MicroblockRenderer;
import net.danygames2014.microblocks.multipart.model.MicroblockModel;
import net.danygames2014.microblocks.multipart.placement.PlacementHelper;
import net.danygames2014.microblocks.util.MathHelper;
import net.danygames2014.microblocks.util.MicroblockBoxUtil;
import net.danygames2014.nyalib.item.EnhancedPlacementContextItem;
import net.danygames2014.nyalib.multipart.MultipartComponent;
import net.danygames2014.nyalib.multipart.MultipartState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.client.StationRenderAPI;
import net.modificationstation.stationapi.api.client.texture.Sprite;
import net.modificationstation.stationapi.api.client.texture.SpriteAtlasTexture;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.math.Direction;
import net.modificationstation.stationapi.api.util.math.Vec3d;
import net.modificationstation.stationapi.impl.client.arsenic.renderer.render.ArsenicItemRenderer;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.glTranslatef;

public abstract class MicroblockItem extends TemplateItem implements EnhancedPlacementContextItem, CustomItemRenderer {
    public Block block;
    public int meta;

    public MicroblockItem(Identifier identifier, Block block, int meta) {
        super(identifier);
        this.block = block;
        this.meta = meta;
    }

    @Override
    public boolean useOnMultipart(ItemStack stack, PlayerEntity player, World world, int x, int y, int z, Direction face, net.minecraft.util.math.Vec3d hitPos, MultipartComponent component) {
        if (this.useOnBlock(stack, player, world, x, y, z, face.getId(), hitPos)) {
            return true;
        }
        
        return super.useOnMultipart(stack, player, world, x, y, z, face, hitPos, component);
    }

    @Environment(EnvType.CLIENT)
    public abstract void renderGrid(PlayerEntity player, int blockX, int blockY, int blockZ, Vec3d hit, Direction face, float tickDelta);

    public abstract int getSize();

    public abstract String getTypeTranslationKey();

    public abstract MicroblockModel getMicroblockModel();

    public abstract PlacementHelper getPlacementHelper();

    @Override
    public boolean useOnBlock(ItemStack stack, PlayerEntity player, World world, int x, int y, int z, int side, net.minecraft.util.math.Vec3d hitVec) {
        if (world.isRemote) {
            return true;
        }
        
        int size = getSize();
        Direction direction = Direction.byId(side);
        net.modificationstation.stationapi.api.util.math.Vec3d stapiVec = new net.modificationstation.stationapi.api.util.math.Vec3d(hitVec.x, hitVec.y, hitVec.z);

        MultipartState state = world.getMultipartState(x, y, z);
        net.minecraft.util.math.Vec3d relativeHitVec = hitVec.add(-x, -y, -z);
        net.modificationstation.stationapi.api.util.math.Vec3d stapiRelativeVec = new net.modificationstation.stationapi.api.util.math.Vec3d(relativeHitVec.x, relativeHitVec.y, relativeHitVec.z);

        if (state != null && MathHelper.getHitDepth(stapiRelativeVec, direction) < 1) {
            if (tryPlace(world, x, y, z, direction, stapiVec, size, player)) {
                return true;
            }
        }

        BlockPos pPos = MathHelper.getPlacementPos(x, y, z, direction);
        return tryPlace(world, pPos.getX(), pPos.getY(), pPos.getZ(), direction, stapiVec, size, player);
    }

    protected abstract boolean tryPlace(World world, int x, int y, int z, Direction dir, net.modificationstation.stationapi.api.util.math.Vec3d vec, int size, PlayerEntity player);

    // Rendering

    @Override
    public void renderInGui(ArsenicItemRenderer arsenicItemRenderer, ItemRenderer itemRenderer, TextRenderer textRenderer, TextureManager textureManager, ItemStack stack, int x, int y) {
        SpriteAtlasTexture atlas = StationRenderAPI.getBakedModelManager().getAtlas(Atlases.GAME_ATLAS_TEXTURE);
        atlas.bindTexture();
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(x - 2), (float)(y + 3), -3.0F);
        GL11.glScalef(10.0F, 10.0F, 10.0F);
        GL11.glTranslatef(1.0F, 0.5F, 1.0F);
        GL11.glScalef(1.0F, 1.0F, -1.0F);
        GL11.glRotatef(210.0F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);

        GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);

        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

        Tessellator tessellator = Tessellator.INSTANCE;
        MicroblockRenderer microblockRenderer = MicroblockRenderer.INSTANCE;
        microblockRenderer.useAo = false;
        ObjectArrayList<Box> boxes = MicroblockBoxUtil.getCenteredBoxes(getMicroblockModel().getBoxesForSlot(null, getSize(), 0, 0, 0), getMicroblockModel().getRenderBounds(null, getSize(), 0, 0, 0));
        tessellator.startQuads();
        for(Box box : boxes){
            tessellator.normal(0.0F, -1.0F, 0.0F);
            microblockRenderer.renderBottom(box, 0, 0, 0, block.getTexture(0, meta), 0xFFFFFF);
            tessellator.draw();
            tessellator.startQuads();
            tessellator.normal(0.0F, 1.0F, 0.0F);
            microblockRenderer.renderTop(box, 0, 0, 0, block.getTexture(1, meta), 0xFFFFFF);
            tessellator.draw();
            tessellator.startQuads();
            tessellator.normal(0.0F, 0.0F, -1.0F);
            microblockRenderer.renderEast(box, 0, 0, 0, block.getTexture(2, meta), 0xFFFFFF);
            tessellator.draw();
            tessellator.startQuads();
            tessellator.normal(0.0F, 0.0F, 1.0F);
            microblockRenderer.renderWest(box, 0, 0, 0, block.getTexture(3, meta), 0xFFFFFF);
            tessellator.draw();
            tessellator.startQuads();
            tessellator.normal(-1.0F, 0.0F, 0.0F);
            microblockRenderer.renderNorth(box, 0, 0, 0, block.getTexture(4, meta), 0xFFFFFF);
            tessellator.draw();
            tessellator.startQuads();
            tessellator.normal(1.0F, 0.0F, 0.0F);
            microblockRenderer.renderSouth(box, 0, 0, 0, block.getTexture(5, meta), 0xFFFFFF);
        }
        tessellator.draw();
        GL11.glPopMatrix();
    }

    @Override
    public void renderInHand(SpriteAtlasTexture atlas, Sprite texture, Tessellator tessellator, LivingEntity entity, ItemStack stack) {
    }

    @Override
    public boolean renderInHandBlock(SpriteAtlasTexture atlas, Tessellator tessellator, LivingEntity entity, ItemStack stack) {
        MicroblockRenderer microblockRenderer = MicroblockRenderer.INSTANCE;
        microblockRenderer.useAo = false;
        ObjectArrayList<Box> boxes = MicroblockBoxUtil.getCenteredBoxes(getMicroblockModel().getBoxesForSlot(null, getSize(), 0, 0, 0), getMicroblockModel().getRenderBounds(null, getSize(), 0, 0, 0));

        GL11.glPushMatrix();
//        glTranslated(0, 3D / 16, -5D / 16);
//        glRotatef(20, 1, 0, 0);
//        glRotatef(45, 0, 1, 0);
//        glScalef(-1, -1, 1);


        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

        tessellator.startQuads();
        for(Box box : boxes){
            tessellator.normal(0.0F, -1.0F, 0.0F);
            microblockRenderer.renderBottom(box, 0, 0, 0, block.getTexture(0, meta), 0xFFFFFF);
            tessellator.draw();
            tessellator.startQuads();
            tessellator.normal(0.0F, 1.0F, 0.0F);
            microblockRenderer.renderTop(box, 0, 0, 0, block.getTexture(1, meta), 0xFFFFFF);
            tessellator.draw();
            tessellator.startQuads();
            tessellator.normal(0.0F, 0.0F, -1.0F);
            microblockRenderer.renderEast(box, 0, 0, 0, block.getTexture(2, meta), 0xFFFFFF);
            tessellator.draw();
            tessellator.startQuads();
            tessellator.normal(0.0F, 0.0F, 1.0F);
            microblockRenderer.renderWest(box, 0, 0, 0, block.getTexture(3, meta), 0xFFFFFF);
            tessellator.draw();
            tessellator.startQuads();
            tessellator.normal(-1.0F, 0.0F, 0.0F);
            microblockRenderer.renderNorth(box, 0, 0, 0, block.getTexture(4, meta), 0xFFFFFF);
            tessellator.draw();
            tessellator.startQuads();
            tessellator.normal(1.0F, 0.0F, 0.0F);
            microblockRenderer.renderSouth(box, 0, 0, 0, block.getTexture(5, meta), 0xFFFFFF);
        }
        tessellator.draw();

        GL11.glPopMatrix();
        return true;
    }

    @Override
    public boolean renderOnGround(ArsenicItemRenderer arsenicItemRenderer, ItemRenderer itemRenderer, Tessellator tessellator, ItemEntity itemEntity, float x, float y, float z, float delta, ItemStack stack, float yOffset, float angle, byte renderedAmount, SpriteAtlasTexture atlas) {
        return false;
    }

    @Override
    public boolean renderOnGroundBlock(ArsenicItemRenderer arsenicItemRenderer, ItemRenderer itemRenderer, Tessellator tessellator, ItemEntity itemEntity, float x, float y, float z, float delta, ItemStack stack, float yOffset, float angle, byte renderedAmount, SpriteAtlasTexture atlas) {
        MicroblockRenderer microblockRenderer = MicroblockRenderer.INSTANCE;
        microblockRenderer.useAo = false;
        ObjectArrayList<Box> boxes = MicroblockBoxUtil.getCenteredBoxes(getMicroblockModel().getBoxesForSlot(null, getSize(), 0, 0, 0), getMicroblockModel().getRenderBounds(null, getSize(), 0, 0, 0));

        GL11.glRotatef(angle, 0.0F, 1.0F, 0.0F);
        GL11.glScalef(0.25F, 0.25F, 0.25F);


        atlas.bindTexture();

        for(int i = 0; i < renderedAmount; i++) {
            GL11.glPushMatrix();

            if (i > 0) {
                float offsetX = (itemRenderer.random.nextFloat() * 2.0F - 1.0F) * 0.2F / 0.25F;
                float offsetY = (itemRenderer.random.nextFloat() * 2.0F - 1.0F) * 0.2F / 0.25F;
                float offsetZ = (itemRenderer.random.nextFloat() * 2.0F - 1.0F) * 0.2F / 0.25F;
                glTranslatef(offsetX, offsetY, offsetZ);
            }

            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
            tessellator.startQuads();
            for(Box box : boxes){
                tessellator.normal(0.0F, -1.0F, 0.0F);
                microblockRenderer.renderBottom(box, 0, 0, 0, block.getTexture(0, meta), 0xFFFFFF);
                tessellator.draw();
                tessellator.startQuads();
                tessellator.normal(0.0F, 1.0F, 0.0F);
                microblockRenderer.renderTop(box, 0, 0, 0, block.getTexture(1, meta), 0xFFFFFF);
                tessellator.draw();
                tessellator.startQuads();
                tessellator.normal(0.0F, 0.0F, -1.0F);
                microblockRenderer.renderEast(box, 0, 0, 0, block.getTexture(2, meta), 0xFFFFFF);
                tessellator.draw();
                tessellator.startQuads();
                tessellator.normal(0.0F, 0.0F, 1.0F);
                microblockRenderer.renderWest(box, 0, 0, 0, block.getTexture(3, meta), 0xFFFFFF);
                tessellator.draw();
                tessellator.startQuads();
                tessellator.normal(-1.0F, 0.0F, 0.0F);
                microblockRenderer.renderNorth(box, 0, 0, 0, block.getTexture(4, meta), 0xFFFFFF);
                tessellator.draw();
                tessellator.startQuads();
                tessellator.normal(1.0F, 0.0F, 0.0F);
                microblockRenderer.renderSouth(box, 0, 0, 0, block.getTexture(5, meta), 0xFFFFFF);
            }
            tessellator.draw();
            GL11.glPopMatrix();
        }

        return true;
    }
}
