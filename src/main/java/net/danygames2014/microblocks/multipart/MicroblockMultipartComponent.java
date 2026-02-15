package net.danygames2014.microblocks.multipart;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.danygames2014.microblocks.Microblocks;
import net.danygames2014.microblocks.client.render.MicroblockRenderer;
import net.danygames2014.microblocks.item.MicroblockItemType;
import net.danygames2014.microblocks.item.base.MicroblockItem;
import net.danygames2014.microblocks.multipart.model.MicroblockModel;
import net.danygames2014.microblocks.util.MathHelper;
import net.danygames2014.microblocks.util.ShrinkHelper;
import net.danygames2014.nyalib.multipart.MultipartComponent;
import net.danygames2014.nyalib.multipart.MultipartState;
import net.danygames2014.nyalib.sound.SoundHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.BlockParticle;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.modificationstation.stationapi.api.registry.BlockRegistry;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.SideUtil;
import net.modificationstation.stationapi.api.util.math.Direction;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public abstract class MicroblockMultipartComponent extends MultipartComponent {
    public Random random = new Random();
    public Block block;
    public int meta;
    public PlacementSlot slot;
    public double renderBoundsMinX;
    public double renderBoundsMinY;
    public double renderBoundsMinZ;
    public double renderBoundsMaxX;
    public double renderBoundsMaxY;
    public double renderBoundsMaxZ;
    public int renderMask;
    int size = 1;

    public MicroblockMultipartComponent() {

    }

    public MicroblockMultipartComponent(Block block, int meta, PlacementSlot slot, int size) {
        this.block = block;
        this.meta = meta;
        this.slot = slot;
        this.size = size;

        this.hardness = block.getHardness();
    }

    @Override
    public boolean isHandHarvestable() {
        return block.material.isHandHarvestable();
    }

    public ItemStack createStack(MicroblockItemType type) {
        return new ItemStack(Microblocks.microblockItems.get(type).get(this.block).get(this.meta));
    }

    public abstract MicroblockItemType getClosestItemType();

    @Override
    public float getBlastResistance(Entity source) {
        return block.getBlastResistance(source);
    }

    @Override
    public BlockSoundGroup getSoundGroup() {
        return block.soundGroup;
    }

    public int getPriority() {
        return slot.getPriority();
    }

    public boolean isTransparent() {
        return !block.isOpaque();
    }

    @Override
    public boolean render(Tessellator tessellator, BlockRenderManager blockRenderManager, int renderLayer) {
        if (block == null) {
            return false;
        }
        if (renderLayer == block.getRenderLayer()) {
            MicroblockRenderer.INSTANCE.renderMicroblock(world, this, blockRenderManager);
            return true;
        }
        return false;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        if (nbt.contains("blockId")) {
            this.block = BlockRegistry.INSTANCE.get(Identifier.of(nbt.getString("blockId")));
            this.hardness = block == null ? 1.0F : block.getHardness();
        } else {
            throw new IllegalStateException();
        }

        if (nbt.contains("meta")) {
            meta = nbt.getInt("meta");
        } else {
            meta = 0;
        }

        if (nbt.contains("slot")) {
            this.slot = PlacementSlot.fromOrdinal(nbt.getInt("slot"));
        } else {
            throw new IllegalStateException();
        }

        this.size = nbt.getInt("size");
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        Identifier blockId = BlockRegistry.INSTANCE.getId(block);
        if (blockId != null) {
            nbt.putString("blockId", blockId.toString());
        }

        nbt.putInt("meta", meta);

        if (slot != null) {
            nbt.putInt("slot", slot.ordinal());
        }

        nbt.putInt("size", size);
    }

    public void setRenderBounds(Box box) {
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

    public Box getRenderBounds() {
        return Box.create(renderBoundsMinX, renderBoundsMinY, renderBoundsMinZ, renderBoundsMaxX, renderBoundsMaxY, renderBoundsMaxZ);
    }

    @Override
    public void init() {
        refreshRenderState();
    }

    @Override
    public void onPlaced() {
        refreshRenderState();
    }

    @Override
    public void onStateUpdated(MultipartComponent updateSource, MultipartState.StateUpdateType updateType) {
        refreshRenderState();
    }

    @Override
    public void onBreak() {
        super.onBreak();

        SideUtil.run(this::spawnBreakingParticles, () -> {});
    }
    
    @Environment(EnvType.CLIENT)
    public void spawnBreakingParticles() {
        double volume = (renderBoundsMaxX - renderBoundsMinX) * (renderBoundsMaxY - renderBoundsMinY) * (renderBoundsMaxZ - renderBoundsMinZ);

        int steps = (int) MathHelper.clamp(volume * 16, 2, 4);
        
        for(int stepX = 0; stepX < steps; ++stepX) {
            for(int stepY = 0; stepY < steps; ++stepY) {
                for(int stepZ = 0; stepZ < steps; ++stepZ) {
                    double particleX = renderBoundsMinX + (renderBoundsMaxX - renderBoundsMinX) * ((double)stepX + 0.5D) / (double)steps;
                    double particleY = renderBoundsMinY + (renderBoundsMaxY - renderBoundsMinY) * ((double)stepY + 0.5D) / (double)steps;
                    double particleZ = renderBoundsMinZ + (renderBoundsMaxZ - renderBoundsMinZ) * ((double)stepZ + 0.5D) / (double)steps;
                    
                    int side = this.random.nextInt(6);
                    Minecraft.INSTANCE.particleManager.addParticle((new BlockParticle(this.world, particleX, particleY, particleZ, particleX - (double)x - (double)0.5F, particleY - (double)y - (double)0.5F, particleZ - (double)z - (double)0.5F, block, side, meta)).color(x, y, z));
                }
            }
        }
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void onBeingBroken(ClientPlayerEntity player, Vec3d hitVec, Direction face) {
        float var7 = 0.1F;
        double particleX = this.random.nextDouble() * (renderBoundsMaxX - renderBoundsMinX - (double)(var7 * 2.0F)) + (double)var7 + renderBoundsMinX;
        double particleY = this.random.nextDouble() * (renderBoundsMaxY - renderBoundsMinY - (double)(var7 * 2.0F)) + (double)var7 + renderBoundsMinY;
        double particleZ = this.random.nextDouble() * (renderBoundsMaxZ - renderBoundsMinZ - (double)(var7 * 2.0F)) + (double)var7 + renderBoundsMinZ;

        int side = face.getId();
        if (side == 0) {
            particleY = renderBoundsMinY - (double)var7;
        }

        if (side == 1) {
            particleY = renderBoundsMaxY + (double)var7;
        }

        if (side == 2) {
            particleZ = renderBoundsMinZ - (double)var7;
        }

        if (side == 3) {
            particleZ = renderBoundsMaxZ + (double)var7;
        }

        if (side == 4) {
            particleX = renderBoundsMinX - (double)var7;
        }

        if (side == 5) {
            particleX = renderBoundsMaxX + (double)var7;
        }
        
        Minecraft.INSTANCE.particleManager.addParticle(
                new BlockParticle(world, particleX, particleY, particleZ, 0.0F, 0.0F, 0.0F, block, face.getId(), meta).color(x, y, z).multiplyVelocity(0.2F).setScale(0.6F)
        );
    }

    public boolean canOverlap(MicroblockItemType type, PlacementSlot slot, int size) {
        return true;
    }

    public void refreshRenderState() {
        if (slot == null) {
            return;
        }

        setRenderBounds(getMicroblockModel().getRenderBounds(slot, size, x, y, z).copy());
        renderMask = 0;
        int maxSlot = (slot.ordinal() < 6) ? 6 : (slot.ordinal() < 15) ? 15 : 26;
        for (MultipartComponent component : state.components) {
            if (component == this) continue;
            if (component instanceof MicroblockMultipartComponent microblock) {
                if (microblock.slot == null) {
                    continue;
                }

                if (microblock.slot.ordinal() >= maxSlot) {
                    continue;
                }
                if (shouldShrink(microblock)) {
                    int side = ShrinkHelper.shrinkSide(slot, microblock.slot);
                    if (side != -1) {
                        setRenderBounds(ShrinkHelper.shrink(getRenderBounds(), microblock.getRenderBounds(), Direction.byId(side)));
                    }
                } else if (microblock.slot.ordinal() < 6 && !microblock.isTransparent()) {
                    this.renderMask |= ShrinkHelper.calculateCulling(microblock, getRenderBounds().offset(-x, -y, -z));
                } else {
                    this.renderMask |= 0;
                }
            }
        }
    }

    public boolean shouldShrink(MicroblockMultipartComponent other) {
        int componentPriority = this.getPriority();
        int otherPriority = other.getPriority();
        if (componentPriority != otherPriority) {
            return componentPriority < otherPriority;
        }
        if (this.slot.ordinal() < 6) {
            if (this.isTransparent() != other.isTransparent()) {
                return this.isTransparent();
            }
            if (this.getSize() != other.getSize()) {
                return this.getSize() < other.getSize();
            }
        } else {
            if (this.getSize() != other.getSize()) {
                return this.getSize() < other.getSize();
            }
            if (this.isTransparent() != other.isTransparent()) {
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

    @Override
    public int getLightLevel() {
        if (block == null) {
            return 0;
        }

        return Block.BLOCKS_LIGHT_LUMINANCE[block.id];
    }

    public boolean canUse(PlayerEntity player, Vec3d pos, Direction face, @Nullable PlacementSlot slotOverride) {
        ItemStack stack = player.getHand();
        if (!player.isSneaking() && stack != null && stack.getItem() instanceof MicroblockItem microblockItem) {
            if (microblockItem.block != block || microblockItem.meta != meta) {
                return false;
            }
            PlacementSlot placementSlot = slotOverride;
            if (slotOverride == null) {
                placementSlot = microblockItem.getPlacementHelper().getSlot(x, y, z, face, new net.modificationstation.stationapi.api.util.math.Vec3d(pos.x, pos.y, pos.z), microblockItem.getPlacementHelper().getGridCenterSize());
            }
            if (placementSlot != slot) {
                return false;
            }
            if (size + microblockItem.getSize() > getMaxSize()) {
                return false;
            }
            if (MathHelper.getHitDepth(new net.modificationstation.stationapi.api.util.math.Vec3d(pos.x - x, pos.y - y, pos.z - z), face) < 1) {
                return microblockItem.getPlacementHelper().canGrow(this, size + microblockItem.getSize());
            }
        }
        return false;
    }

    @Override
    public boolean onUse(PlayerEntity player, Vec3d pos, Direction face) {
        if (canUse(player, pos, face, null)) {
            ItemStack stack = player.getHand();
            if (stack.getItem() instanceof MicroblockItem microblockItem) {
                if (world.isRemote) {
                    return true;
                }
                size += microblockItem.getSize();
                stack.count--;
                markDirty();
                notifyNeighbors();
                refreshRenderState();
                SoundHelper.playSound(world, x + 0.5F, y + 0.5F, z + 0.5F, block.soundGroup.getSound(), (block.soundGroup.getVolume() + 1.0F) / 2.0F, block.soundGroup.getPitch() * 0.8F);
                return true;
            }
        }
        return false;
    }

    @Override
    public void neighborUpdate(MultipartComponent updateSource, Direction direction) {
        refreshRenderState();
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
