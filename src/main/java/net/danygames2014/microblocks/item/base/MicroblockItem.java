package net.danygames2014.microblocks.item.base;

import net.danygames2014.microblocks.util.MathHelper;
import net.danygames2014.nyalib.item.EnhancedPlacementContextItem;
import net.danygames2014.nyalib.multipart.MultipartComponent;
import net.danygames2014.nyalib.multipart.MultipartState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.math.Direction;
import net.modificationstation.stationapi.api.util.math.Vec3d;

public abstract class MicroblockItem extends TemplateItem implements EnhancedPlacementContextItem {
    public Block block;

    public MicroblockItem(Identifier identifier, Block block) {
        super(identifier);
        this.block = block;
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

    @Override
    public boolean useOnBlock(ItemStack stack, PlayerEntity player, World world, int x, int y, int z, int side, net.minecraft.util.math.Vec3d hitVec) {
        int size = getSize();
        Direction direction = Direction.byId(side);
        net.modificationstation.stationapi.api.util.math.Vec3d stapiVec = new net.modificationstation.stationapi.api.util.math.Vec3d(hitVec.x, hitVec.y, hitVec.z);

        MultipartState state = world.getMultipartState(x, y, z);
        net.minecraft.util.math.Vec3d relativeHitVec = hitVec.add(-x, -y, -z);
        net.modificationstation.stationapi.api.util.math.Vec3d stapiRelativeVec = new net.modificationstation.stationapi.api.util.math.Vec3d(relativeHitVec.x, relativeHitVec.y, relativeHitVec.z);

        if (state != null && MathHelper.getHitDepth(stapiRelativeVec, direction) < 1) {
            if (tryPlace(world, x, y, z, direction, stapiVec, size)) {
                return true;
            }
        }

        BlockPos pPos = MathHelper.getPlacementPos(x, y, z, direction);
        return tryPlace(world, pPos.getX(), pPos.getY(), pPos.getZ(), direction, stapiVec, size);
    }

    protected abstract boolean tryPlace(World world, int x, int y, int z, Direction dir, net.modificationstation.stationapi.api.util.math.Vec3d vec, int size);
}
