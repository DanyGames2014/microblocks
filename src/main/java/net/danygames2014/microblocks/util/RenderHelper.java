package net.danygames2014.microblocks.util;

import net.danygames2014.nyalib.util.PlayerUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.modificationstation.stationapi.api.util.math.Direction;
import net.modificationstation.stationapi.api.util.math.Quaternion;
import net.modificationstation.stationapi.api.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class RenderHelper {
    public static void rotateToDirection(Direction direction) {
        switch (direction) {
            case DOWN -> {
                return;
            }
            case UP -> {
                GL11.glRotatef(180f, 1f, 0f, 0f);
            }
            case EAST -> {
                GL11.glRotatef(90f, 1f, 0f, 0f);
            }
            case WEST -> {
                GL11.glRotatef(-90f, 1f, 0f, 0f);
            }
            case NORTH -> {
                GL11.glRotatef(90f, 0f, 0f, 1f);
            }
            case SOUTH -> {
                GL11.glRotatef(-90f, 0f, 0f, 1f);
            }
        }
    }

    public static void translateToFace(PlayerEntity player, int blockX, int blockY, int blockZ, Vec3d hit, Direction face, float tickDelta) {
        BlockPos pos = new BlockPos(blockX, blockY, blockZ);
        Vec3d playerPos = PlayerUtil.getRenderPosition(player, tickDelta);

        GL11.glTranslated((pos.getX() + 0.5D) - playerPos.getX(), (pos.getY() + 0.5D) - playerPos.getY(), (pos.getZ() + 0.5D) - playerPos.getZ());
        rotateToDirection(face);

        Vec3d vec = new Vec3d(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D).subtract(hit);
        Quaternion quat = MathHelper.getQuaternionForSide(Direction.byId(face.ordinal()^1));
        quat.conjugate();
        vec = MathHelper.rotate(vec, quat);

        GL11.glTranslated(0, face.getAxis() != Direction.Axis.X ? vec.y - 0.003 : vec.y + 0.003, 0);
    }
}
