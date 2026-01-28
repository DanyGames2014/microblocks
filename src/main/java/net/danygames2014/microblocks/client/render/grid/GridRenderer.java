package net.danygames2014.microblocks.client.render.grid;

import net.danygames2014.microblocks.util.RenderHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.modificationstation.stationapi.api.util.math.Direction;
import net.modificationstation.stationapi.api.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class GridRenderer {
    public static GridRenderer INSTANCE = new GridRenderer();
    public void render(PlayerEntity player, int blockX, int blockY, int blockZ, Vec3d hit, Direction face, double size, float tickDelta){
        GL11.glPushMatrix();

        RenderHelper.translateToFace(player, blockX, blockY, blockZ, hit, face, tickDelta);

        GL11.glLineWidth(2);
        GL11.glColor4f(0, 0, 0, 1);
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex3d(-0.5, 0,-0.5);
        GL11.glVertex3d(-0.5, 0, 0.5);

        GL11.glVertex3d(-0.5, 0, 0.5);
        GL11.glVertex3d( 0.5, 0, 0.5);

        GL11.glVertex3d( 0.5, 0, 0.5);
        GL11.glVertex3d( 0.5, 0,-0.5);

        GL11.glVertex3d( 0.5, 0,-0.5);
        GL11.glVertex3d(-0.5, 0,-0.5);

        GL11.glVertex3d(0.5, 0, 0.5);
        GL11.glVertex3d(size, 0, size);

        GL11.glVertex3d(-0.5, 0, 0.5);
        GL11.glVertex3d(-size, 0, size);

        GL11.glVertex3d(0.5, 0, -0.5);
        GL11.glVertex3d(size, 0, -size);

        GL11.glVertex3d(-0.5, 0, -0.5);
        GL11.glVertex3d(-size, 0, -size);

        GL11.glVertex3d(-size, 0,-size);
        GL11.glVertex3d(-size, 0, size);

        GL11.glVertex3d(-size, 0, size);
        GL11.glVertex3d( size, 0, size);

        GL11.glVertex3d( size, 0, size);
        GL11.glVertex3d( size, 0,-size);

        GL11.glVertex3d( size, 0,-size);
        GL11.glVertex3d(-size, 0,-size);
        GL11.glEnd();
        GL11.glPopMatrix();
    }
}
