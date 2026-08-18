package net.danygames2014.microblocks.multipart.placement;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.danygames2014.microblocks.client.render.grid.GridRenderer;
import net.danygames2014.microblocks.multipart.MicroblockMultipartComponent;
import net.danygames2014.nyalib.multipart.MultipartSlot;
import net.danygames2014.nyalib.multipart.MultipartState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.math.Direction;
import net.modificationstation.stationapi.api.util.math.Vec3d;

import java.util.List;

public abstract class PlacementHelper {
    public abstract MultipartSlot getSlot(int x, int y, int z, Direction face, Vec3d hit, double size);
    public abstract MultipartSlot getOppositeSlot(MultipartSlot slot, Direction side);

    public Vec3d getRelativeHitVec(int x, int y, int z, Direction face, Vec3d hit) {
        return hit.add(-x, -y, -z);
    }

    public boolean canPlace(World world, int x, int y, int z, MicroblockMultipartComponent component) {
        List<Box> boxes = component.getMicroblockModel().getShapeForSlot(component.slot, component.getSize(), x, y, z).getOffsetBoxes();

        for(Box box : boxes) {
            var entitiesInBox = world.getEntities(null, box);

            for (var entity : entitiesInBox) {
                if (!(entity instanceof ItemEntity)) {
                    return false;
                }
            }
        }

        MultipartState state = world.getMultipartState(x, y, z);
        if(state == null) {
            return true;
        }

        return state.canAddComponent(component);
    }

    @Environment(EnvType.CLIENT)
    public void renderGrid(PlayerEntity player, int blockX, int blockY, int blockZ, Vec3d hit, Direction face, double size, float tickDelta){
        getGridRenderer().render(player, blockX, blockY, blockZ, hit, face, size, tickDelta);
    }

    @Environment(EnvType.CLIENT)
    protected GridRenderer getGridRenderer(){
        return GridRenderer.INSTANCE;
    }

    public double getGridCenterSize(){
        return 1/4D;
    }
}
