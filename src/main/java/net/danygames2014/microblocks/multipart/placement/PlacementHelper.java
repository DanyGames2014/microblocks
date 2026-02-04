package net.danygames2014.microblocks.multipart.placement;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.danygames2014.microblocks.client.render.grid.GridRenderer;
import net.danygames2014.microblocks.multipart.MicroblockMultipartComponent;
import net.danygames2014.microblocks.multipart.PlacementSlot;
import net.danygames2014.microblocks.multipart.model.MicroblockModel;
import net.danygames2014.nyalib.multipart.MultipartComponent;
import net.danygames2014.nyalib.multipart.MultipartState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.math.Direction;
import net.modificationstation.stationapi.api.util.math.Vec3d;

public abstract class PlacementHelper {
    public abstract PlacementSlot getSlot(int x, int y, int z, Direction face, Vec3d hit, double size);
    public abstract PlacementSlot getOppositeSlot(PlacementSlot slot, Direction side);

    public Vec3d getRelativeHitVec(int x, int y, int z, Direction face, Vec3d hit) {
        return hit.add(-x, -y, -z);
    }

    public boolean canPlace(World world, int x, int y, int z, PlacementSlot slot, int size, MicroblockModel model) {
        if (y >= world.getTopY() - 1) {
            return false;
        }
        
        MultipartState state = world.getMultipartState(x, y, z);
        ObjectArrayList<Box> newBoxes = model.getBoxesForSlot(slot, size, x, y, z);

        for(Box newBox : newBoxes) {
            if (!world.getEntities(null, newBox).isEmpty()) {
                return false;
            }
        }
        
        if(state == null) {
            return true;
        }
        
        ObjectArrayList<Box> existingBoxes = new ObjectArrayList<>();

        for(MultipartComponent component : state.components) {
            if(component instanceof MicroblockMultipartComponent microblock) {
                if(microblock.slot == slot) return false;
                existingBoxes.addAll(microblock.getMicroblockModel().getBoxesForSlot(microblock.slot, microblock.getSize(), x, y, z));
            }
        }

        for(Box newBox : newBoxes) {
            if (!world.getEntities(null, newBox).isEmpty()) {
                return false;
            }
            
            if(isFullyCovered(newBox, existingBoxes)) {
                return false;
            }
        }

        for(MultipartComponent component : state.components) {
            if(component instanceof MicroblockMultipartComponent microblock) {
                ObjectArrayList<Box> currentExistingBoxes = microblock.getMicroblockModel().getBoxesForSlot(microblock.slot, microblock.getSize(), x, y, z);

                ObjectArrayList<Box> everyBox = new ObjectArrayList<>(newBoxes);
                for(MultipartComponent otherComponent : state.components) {
                    if(otherComponent instanceof MicroblockMultipartComponent otherMicro) {
                        if(otherMicro != microblock) {
                            everyBox.addAll(otherMicro.getMicroblockModel().getBoxesForSlot(otherMicro.slot, otherMicro.getSize(), x, y, z));
                        }
                    }
                }

                for(Box existingBox : currentExistingBoxes) {
                    if(isFullyCovered(existingBox, everyBox)){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean isFullyCovered(Box target, ObjectArrayList<Box> covers) {
        ObjectArrayList<Box> remainingPieces = new ObjectArrayList<>();
        remainingPieces.add(target);

        for (Box cover : covers) {
            ObjectArrayList<Box> nextLevelPieces = new ObjectArrayList<>();
            for (Box piece : remainingPieces) {
                nextLevelPieces.addAll(subtract(piece, cover));
            }
            remainingPieces = nextLevelPieces;
            if (remainingPieces.isEmpty()) return true;
        }

        return remainingPieces.isEmpty();
    }

    public ObjectArrayList<Box> subtract(Box box, Box other) {
        ObjectArrayList<Box> result = new ObjectArrayList<>();

        if (!box.intersects(other)) {
            result.add(box);
            return result;
        }

        double curMinX = box.minX, curMaxX = box.maxX;
        double curMinY = box.minY, curMaxY = box.maxY;
        double curMinZ = box.minZ, curMaxZ = box.maxZ;

        if (other.minX > curMinX) {
            result.add(Box.create(curMinX, curMinY, curMinZ, other.minX, curMaxY, curMaxZ));
            curMinX = other.minX;
        }
        if (other.maxX < curMaxX) {
            result.add(Box.create(other.maxX, curMinY, curMinZ, curMaxX, curMaxY, curMaxZ));
            curMaxX = other.maxX;
        }

        if (other.minY > curMinY) {
            result.add(Box.create(curMinX, curMinY, curMinZ, curMaxX, other.minY, curMaxZ));
            curMinY = other.minY;
        }
        if (other.maxY < curMaxY) {
            result.add(Box.create(curMinX, other.maxY, curMinZ, curMaxX, curMaxY, curMaxZ));
            curMaxY = other.maxY;
        }

        if (other.minZ > curMinZ) {
            result.add(Box.create(curMinX, curMinY, curMinZ, curMaxX, curMaxY, other.minZ));
        }
        if (other.maxZ < curMaxZ) {
            result.add(Box.create(curMinX, curMinY, other.maxZ, curMaxX, curMaxY, curMaxZ));
        }

        return result;
    }

    private boolean isInBox(Box box, Box other) {
        return other.minX >= box.minX &&
               other.minY >= box.minY &&
               other.minZ >= box.minZ &&
               other.maxX <= box.maxX &&
               other.maxY <= box.maxY &&
               other.maxZ <= box.maxZ;
    }

    @Environment(EnvType.CLIENT)
    public void renderGrid(PlayerEntity player, int blockX, int blockY, int blockZ, Vec3d hit, Direction face, double size, float tickDelta){
        getGridRenderer().render(player, blockX, blockY, blockZ, hit, face, size, tickDelta);
    }

    @Environment(EnvType.CLIENT)
    protected GridRenderer getGridRenderer(){
        return GridRenderer.INSTANCE;
    }
}
