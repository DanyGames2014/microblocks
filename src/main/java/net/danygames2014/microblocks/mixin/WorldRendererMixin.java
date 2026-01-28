package net.danygames2014.microblocks.mixin;

import net.danygames2014.microblocks.item.MicroblockItem;
import net.danygames2014.microblocks.util.RenderHelper;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitResultType;
import net.modificationstation.stationapi.api.util.math.Direction;
import net.modificationstation.stationapi.api.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @Inject(method = "renderBlockOutline", at = @At("HEAD"), cancellable = true)
    void renderGrid(PlayerEntity player, HitResult hitResult, int i, ItemStack handStack, float tickDelta, CallbackInfo ci){
        if(i == 0 && hitResult.type == HitResultType.BLOCK && player.getHand() != null && player.getHand().getItem() instanceof MicroblockItem){
            GL11.glPushMatrix();

            RenderHelper.translateToFace(player, hitResult.blockX, hitResult.blockY, hitResult.blockZ, new Vec3d(hitResult.pos.x, hitResult.pos.y, hitResult.pos.z), Direction.byId(hitResult.side), tickDelta);

            double size = 1/4D;

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

            ci.cancel();
        }
    }
}
