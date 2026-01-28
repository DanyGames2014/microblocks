package net.danygames2014.microblocks.mixin;

import net.danygames2014.microblocks.multipart.MicroblockMultipartComponent;
import net.danygames2014.nyalib.multipart.MultipartComponent;
import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ToolItem.class)
public abstract class ToolItemMixin extends Item {
    @Shadow
    public abstract float getMiningSpeedMultiplier(ItemStack stack, Block block);

    @Shadow
    public abstract boolean postMine(ItemStack stack, int blockId, int x, int y, int z, LivingEntity miner);

    public ToolItemMixin(int id) {
        super(id);
    }

    // Will have to do this in a more comaptible way in the future, I dont have the willpower right now, sue me
    @Override
    public boolean isSuitableForMultipart(ItemStack stack, PlayerEntity player, World world, int x, int y, int z, MultipartComponent component) {
        if (component instanceof MicroblockMultipartComponent microblock) {
            return this.isSuitableFor(microblock.block);
        }
        
        return super.isSuitableForMultipart(stack, player, world, x, y, z, component);
    }

    @Override
    public float getMultipartMiningSpeedMultiplier(ItemStack stack, PlayerEntity player, World world, int x, int y, int z, MultipartComponent component) {
        if (component instanceof MicroblockMultipartComponent microblock) {
            return this.getMiningSpeedMultiplier(stack, microblock.block);
        }
        
        return super.getMultipartMiningSpeedMultiplier(stack, player, world, x, y, z, component);
    }

    @Override
    public boolean postMineMultipart(ItemStack stack, LivingEntity entity, World world, int x, int y, int z, MultipartComponent component) {
        if (component instanceof MicroblockMultipartComponent microblock) {
            this.postMine(stack, microblock.block.id, x, y, z, entity);
        }
        
        return super.postMineMultipart(stack, entity, world, x, y, z, component);
    }
}
