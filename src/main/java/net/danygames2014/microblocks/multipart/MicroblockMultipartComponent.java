package net.danygames2014.microblocks.multipart;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.danygames2014.microblocks.client.render.MicroblockRenderer;
import net.danygames2014.microblocks.multipart.model.MicroblockModel;
import net.danygames2014.microblocks.util.ShrinkHelper;
import net.danygames2014.nyalib.multipart.MultipartComponent;
import net.danygames2014.nyalib.multipart.MultipartState;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.Box;
import net.modificationstation.stationapi.api.registry.BlockRegistry;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.math.Direction;
import org.lwjgl.input.Keyboard;

public abstract class MicroblockMultipartComponent extends MultipartComponent {

    public Block block;
    public PlacementSlot slot;
    public double renderBoundsMinX;
    public double renderBoundsMinY;
    public double renderBoundsMinZ;
    public double renderBoundsMaxX;
    public double renderBoundsMaxY;
    public double renderBoundsMaxZ;
    public int renderMask;
    int size = 1;

    public MicroblockMultipartComponent() {}
    public MicroblockMultipartComponent(Block block, PlacementSlot slot, int size) {
        this.block = block;
        this.slot = slot;
        this.size = size;
        
        this.hardness = block.getHardness();
    }

    @Override
    public boolean isHandHarvestable() {
        return block.material.isHandHarvestable();
    }

    @Override
    public float getBlastResistance(Entity source) {
        return block.getBlastResistance(source);
    }

    @Override
    public BlockSoundGroup getSoundGroup() {
        return block.soundGroup;
    }

    public int getPriority(){
        return slot.getPriority();
    }

    public boolean isTransparent(){
        return !block.isOpaque();
    }

    @Override
    public boolean render(Tessellator tessellator, BlockRenderManager blockRenderManager, int renderLayer) {
        if (renderLayer != 0) {
            return false;
        }
        MicroblockRenderer.INSTANCE.renderMicroblock(world, this, blockRenderManager);
        return true;
//        ObjectArrayList<Box> boxes = new ObjectArrayList<>();
//        getCollisionBoxes(boxes);
//
//        for(Box box : boxes){
//            block.setBoundingBox((float) (box.minX - x), (float) (box.minY - y), (float) (box.minZ - z), (float) (box.maxX - x), (float) (box.maxY - y), (float) (box.maxZ - z));
//            blockRenderManager.renderBlock(block, x, y + renderLayer, z);
//        }
//        block.setBoundingBox(0f, 0f, 0f, 1f, 1f, 1f);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        if (nbt.contains("blockId")) {
            this.block = BlockRegistry.INSTANCE.get(Identifier.of(nbt.getString("blockId")));
            this.hardness = block == null ? 1.0F : block.getHardness();
        }
        if(nbt.contains("slot")) {
            this.slot = PlacementSlot.fromOrdinal(nbt.getInt("slot"));
        }
        this.size = nbt.getInt("size");
        refreshRenderState();
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        Identifier blockId = BlockRegistry.INSTANCE.getId(block);
        if (blockId != null) {
            nbt.putString("blockId", blockId.toString());
        }
        if(slot != null){
            nbt.putInt("slot", slot.ordinal());
        }
        nbt.putInt("size", size);
    }

    @Override
    public void onBreakStart(PlayerEntity player) {
        System.out.println(slot + " " + (slot.ordinal() - 14));
        
        ObjectArrayList<Box> boxes = getBoundingBoxes();
        for(Box box : boxes) {
            System.out.println(box.offset(-x, -y, -z));
        }
//        System.out.println("renderbounds: " + renderBounds);
        if(FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT && Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
            size++;
            if(size > getMaxSize()){
                size = 1;
            }
            markDirty();
        }
    }

    public void setRenderBounds(Box box){
        setRenderBounds(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ);
    }

    public void setRenderBounds(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        this.renderBoundsMinX = minX;
        this.renderBoundsMinY = minY;
        this.renderBoundsMinZ = minZ;
        this.renderBoundsMaxX = maxX;
        this.renderBoundsMaxY = maxY;
        this.renderBoundsMaxZ = maxZ;
    }

    public Box getRenderBounds(){
        return Box.create(renderBoundsMinX, renderBoundsMinY, renderBoundsMinZ, renderBoundsMaxX, renderBoundsMaxY, renderBoundsMaxZ);
    }

    @Override
    public void onPlaced() {
        refreshRenderState();
    }

    @Override
    public void onStateUpdated(MultipartComponent updateSource, MultipartState.StateUpdateType updateType) {
        refreshRenderState();
    }

    public void refreshRenderState(){
        if (slot == null) {
            return;
        }
        
        setRenderBounds(getMicroblockModel().getRenderBounds(slot, size, x, y, z).copy());
        renderMask = 0;
        int maxSlot = (slot.ordinal() < 6) ? 6 : (slot.ordinal() < 15) ? 15 : 26;
        for(MultipartComponent component : state.components){
            if(component == this) continue;
            if(component instanceof MicroblockMultipartComponent microblock){
                if (microblock.slot == null) {
                    continue;
                }
                
                if(microblock.slot.ordinal() >= maxSlot){
                    continue;
                }
                if(shouldShrink(microblock)){
                   int side = ShrinkHelper.shrinkSide(slot, microblock.slot);
                   if(side != -1){
                       setRenderBounds(ShrinkHelper.shrink(getRenderBounds(), microblock.getRenderBounds(), Direction.byId(side)));
                   }
                }
                else if(microblock.slot.ordinal() < 6 && !microblock.isTransparent()){
                    this.renderMask |= ShrinkHelper.calculateCulling(microblock, getRenderBounds().offset(-x, -y, -z));
                } else {
                    this.renderMask |= 0;
                }
            }
        }
    }

    public boolean shouldShrink(MicroblockMultipartComponent other){
        int componentPriority = this.getPriority();
        int otherPriority = other.getPriority();
        if(componentPriority != otherPriority) {
            return componentPriority < otherPriority;
        }
        if(this.slot.ordinal() < 6) {
            if(this.isTransparent() != other.isTransparent()) {
                return this.isTransparent();
            }
            if(this.getSize() != other.getSize()) {
                return this.getSize() < other.getSize();
            }
        }
        else {
            if(this.getSize() != other.getSize()) {
                return this.getSize() < other.getSize();
            }
            if(this.isTransparent() != other.isTransparent()) {
                return this.isTransparent();
            }
        }
        return this.slot.ordinal() < other.slot.ordinal();
    }

    public ObjectArrayList<Box> getClippedBoxes(ObjectArrayList<Box> boxes) {
        ObjectArrayList<Box> clippedList = new ObjectArrayList<>();

        Box renderBounds = getRenderBounds();

        for (Box box : boxes) {
            if (box.maxX <= renderBounds.minX || box.minX >= renderBounds.maxX || box.maxY <= renderBounds.minY || box.minY >= renderBounds.maxY || box.maxZ <= renderBounds.minZ || box.minZ >= renderBounds.maxZ) {
                continue;
            }

            double newMinX = Math.max(box.minX, renderBounds.minX);
            double newMaxX = Math.min(box.maxX, renderBounds.maxX);

            double newMinY = Math.max(box.minY, renderBounds.minY);
            double newMaxY = Math.min(box.maxY, renderBounds.maxY);

            double newMinZ = Math.max(box.minZ, renderBounds.minZ);
            double newMaxZ = Math.min(box.maxZ, renderBounds.maxZ);

            clippedList.add(Box.create(newMinX, newMinY, newMinZ, newMaxX, newMaxY, newMaxZ));
        }

        return clippedList;
    }

    public abstract MicroblockModel getMicroblockModel();

    @Override
    public void getCollisionBoxes(ObjectArrayList<Box> boxes) {
        boxes.addAll(getMicroblockModel().getBoxesForSlot(slot, size, x, y, z));
    }

    @Override
    public ObjectArrayList<Box> getBoundingBoxes() {
        return getMicroblockModel().getBoxesForSlot(slot, size, x, y, z);
    }

    public abstract int getMaxSize();
    public int getSize() {
        return size;
    }
}
