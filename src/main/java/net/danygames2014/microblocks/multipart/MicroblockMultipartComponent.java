package net.danygames2014.microblocks.multipart;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.danygames2014.microblocks.multipart.placement.PlacementHelper;
import net.danygames2014.nyalib.multipart.MultipartComponent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Box;
import net.modificationstation.stationapi.api.registry.BlockRegistry;
import net.modificationstation.stationapi.api.util.Identifier;
import org.lwjgl.input.Keyboard;

public abstract class MicroblockMultipartComponent extends MultipartComponent {
    protected static double PIXEL_SIZE = 1D / 16D;
    public Block block;
    public PlacementSlot slot;
    int size = 1;

    public MicroblockMultipartComponent() {}
    public MicroblockMultipartComponent(Block block, PlacementSlot slot, int size) {
        this.block = block;
        this.slot = slot;
        this.size = size;
    }

    public int getPriority(){
        return slot.getPriority();
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
        System.out.println(slot);
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

    public abstract int getMaxSize();
}
