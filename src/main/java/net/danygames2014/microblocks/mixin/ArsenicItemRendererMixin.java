package net.danygames2014.microblocks.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import net.danygames2014.microblocks.client.render.CustomItemRenderer;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.client.texture.SpriteAtlasTexture;
import net.modificationstation.stationapi.impl.client.arsenic.renderer.render.ArsenicItemRenderer;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArsenicItemRenderer.class)
public class ArsenicItemRendererMixin {
    @Shadow
    @Final
    private ItemRenderer itemRenderer;

    @Inject(
            method = "renderItemOnGui(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/client/texture/TextureManager;Lnet/minecraft/item/ItemStack;IILorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;",
                    shift = At.Shift.AFTER
            ),
            cancellable = true)
    public void renderInGui(TextRenderer textRenderer, TextureManager textureManager, ItemStack stack, int x, int y, CallbackInfo methodCi, CallbackInfo ci) {
        if (stack.getItem() instanceof CustomItemRenderer customItemRenderer) {
            customItemRenderer.renderInGui(ArsenicItemRenderer.class.cast(this), this.itemRenderer, textRenderer, textureManager, stack, x, y);
            methodCi.cancel();
            ci.cancel();
        }
    }

    @Unique
    boolean cancelVanillaVertex = false;

    @Inject(method = "renderVanilla", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Tessellator;normal(FFF)V", shift = At.Shift.AFTER))
    public void renderOnGround(ItemEntity itemEntity, float x, float y, float z, float delta, ItemStack stack, float yOffset, float angle, byte renderedAmount, SpriteAtlasTexture atlas, CallbackInfo ci, @Local(name = "var15") Tessellator tessellator) {
        cancelVanillaVertex = false;
        if (stack.getItem() instanceof CustomItemRenderer customItemRenderer) {
            cancelVanillaVertex = customItemRenderer.renderOnGround(ArsenicItemRenderer.class.cast(this), this.itemRenderer, tessellator, itemEntity, x, y, z, delta, stack, yOffset, angle, renderedAmount, atlas);
        }
    }

    @Inject(method = "renderVanilla", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glTranslatef(FFF)V", ordinal = 0, shift = At.Shift.AFTER), cancellable = true)
    public void renderOnGroundBlock(ItemEntity itemEntity, float x, float y, float z, float delta, ItemStack stack, float yOffset, float angle, byte renderedAmount, SpriteAtlasTexture atlas, CallbackInfo ci){
        if (stack.getItem() instanceof CustomItemRenderer customItemRenderer) {
            if(customItemRenderer.renderOnGroundBlock(ArsenicItemRenderer.class.cast(this), this.itemRenderer, Tessellator.INSTANCE, itemEntity, x, y, z, delta, stack, yOffset, angle, renderedAmount, atlas)){
                GL11.glPopMatrix();
                ci.cancel();
            }
        }
    }

    @WrapWithCondition(method = "renderVanilla", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Tessellator;vertex(DDDDD)V"))
    public boolean cancelVanillaVertex(Tessellator instance, double x, double y, double z, double u, double v) {
        return !cancelVanillaVertex;
    }

}
