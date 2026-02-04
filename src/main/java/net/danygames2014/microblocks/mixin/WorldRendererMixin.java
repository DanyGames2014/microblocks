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
            microblockItem.renderOutline(player, new BlockPos(hitResult.blockX, hitResult.blockY, hitResult.blockZ), new Vec3d(hitResult.pos.x, hitResult.pos.y, hitResult.pos.z), Direction.byId(hitResult.side), tickDelta);
            ci.cancel();
        }
    }
}
