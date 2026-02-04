package net.danygames2014.microblocks.mixin;

import net.danygames2014.microblocks.client.render.MicroblockRenderer;
import net.danygames2014.microblocks.item.base.MicroblockItem;
import net.danygames2014.microblocks.multipart.PlacementSlot;
import net.danygames2014.microblocks.multipart.model.MicroblockModel;
import net.danygames2014.microblocks.multipart.placement.PlacementHelper;
import net.danygames2014.microblocks.util.DirectionUtil;
import net.danygames2014.microblocks.util.MathHelper;
import net.danygames2014.nyalib.multipart.MultipartState;
import net.danygames2014.nyalib.util.PlayerUtil;
import net.minecraft.block.Block;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.math.Direction;
import net.modificationstation.stationapi.api.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @Shadow
    private World world;

    @Inject(method = "renderBlockOutline", at = @At("HEAD"), cancellable = true)
    void renderGrid(PlayerEntity player, HitResult hitResult, int i, ItemStack handStack, float tickDelta, CallbackInfo ci){
        if(i == 0 && hitResult.type == HitResultType.BLOCK && player.getHand() != null && player.getHand().getItem() instanceof MicroblockItem microblockItem){
            microblockItem.renderGrid(player, hitResult.blockX, hitResult.blockY, hitResult.blockZ, new Vec3d(hitResult.pos.x, hitResult.pos.y, hitResult.pos.z), Direction.byId(hitResult.side), tickDelta);
            int size = microblockItem.getSize();
            Direction direction = Direction.byId(hitResult.side);
            Vec3d stapiVec = new Vec3d(hitResult.pos.x, hitResult.pos.y, hitResult.pos.z);

            MultipartState state = world.getMultipartState(hitResult.blockX, hitResult.blockY, hitResult.blockZ);
            Vec3d relativeHitVec = stapiVec.add(-hitResult.blockX, -hitResult.blockY, -hitResult.blockZ);

            if(state != null && MathHelper.getHitDepth(relativeHitVec, direction) < 1){
                if(tryRenderPreview(world, hitResult.blockX, hitResult.blockY, hitResult.blockZ, direction, stapiVec, microblockItem.getSize(), microblockItem.getMicroblockModel(), microblockItem.block, microblockItem.meta, microblockItem.getPlacementHelper(), player, tickDelta)){
                    ci.cancel();
                    return;
                }
            }

            BlockPos pPos = MathHelper.getPlacementPos(hitResult.blockX, hitResult.blockY, hitResult.blockZ, direction);

            tryRenderPreview(world, pPos.getX(), pPos.getY(), pPos.getZ(), direction, stapiVec, microblockItem.getSize(), microblockItem.getMicroblockModel(), microblockItem.block, microblockItem.meta, microblockItem.getPlacementHelper(), player, tickDelta);

            ci.cancel();
        }
    }
    boolean tryRenderPreview(World world, int x, int y, int z, Direction dir, Vec3d vec, int size, MicroblockModel microblockModel, Block block, int meta, PlacementHelper placementHelper, PlayerEntity player, float tickDelta){
        PlacementSlot placementSlot = placementHelper.getSlot(x, y, z, dir, vec, 1/4D);
        if (player.isSneaking()) {
            placementSlot = placementHelper.getOppositeSlot(placementSlot, dir);
        }

        if(placementHelper.canPlace(world, x, y, z, placementSlot, size, microblockModel)){
            MicroblockRenderer renderer = MicroblockRenderer.INSTANCE;
            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

            Vec3d playerPos = PlayerUtil.getRenderPosition(player, tickDelta);
            GL11.glTranslated(x - playerPos.x, y - playerPos.y, z - playerPos.z);

            renderer.renderMicroblockPreview(microblockModel, placementSlot, block, meta, size, 0, 0, 0);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glPopMatrix();
            return true;
        }
        return false;
    }
}
