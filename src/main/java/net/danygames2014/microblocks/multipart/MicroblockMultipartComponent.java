package net.danygames2014.microblocks.multipart;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.danygames2014.microblocks.multipart.model.MicroblockModel;
import net.danygames2014.microblocks.multipart.placement.PlacementHelper;
import net.danygames2014.nyalib.multipart.MultipartComponent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.Box;
import net.modificationstation.stationapi.api.registry.BlockRegistry;
import net.modificationstation.stationapi.api.util.Identifier;
import org.lwjgl.input.Keyboard;

public abstract class MicroblockMultipartComponent extends MultipartComponent {

    public Block block;
    public PlacementSlot slot;
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
    public void render(Tessellator tessellator, BlockRenderManager blockRenderManager, int renderLayer) {
        if (renderLayer != 0) {
            return;
        }
        ObjectArrayList<Box> boxes = new ObjectArrayList<>();
        getCollisionBoxes(boxes);

        for(Box box : boxes){
            block.setBoundingBox((float) (box.minX - x), (float) (box.minY - y), (float) (box.minZ - z), (float) (box.maxX - x), (float) (box.maxY - y), (float) (box.maxZ - z));
            blockRenderManager.renderBlock(block, x, y + renderLayer, z);
        }
        block.setBoundingBox(0f, 0f, 0f, 1f, 1f, 1f);
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
    public void onBreakStart() {
        System.out.println(slot + " " + (slot.ordinal() - 14));
        if(Minecraft.INSTANCE.getMultipartCrosshairTarget() != null){
            System.out.println("side: " + Minecraft.INSTANCE.getMultipartCrosshairTarget().face.ordinal());
        }
        ObjectArrayList<Box> boxes = getBoundingBoxes();
        for(Box box : boxes) {
            System.out.println(box.offset(-x, -y, -z));
        }
        if(FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT && Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
            size++;
            if(size > getMaxSize()){
                size = 1;
            }
            markDirty();
        }
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
