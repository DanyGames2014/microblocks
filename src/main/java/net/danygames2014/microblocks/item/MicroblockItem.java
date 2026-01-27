package net.danygames2014.microblocks.item;

import net.danygames2014.microblocks.multipart.placement.PlacementHelper;
import net.danygames2014.nyalib.item.EnhancedPlacementContextItem;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.registry.BlockRegistry;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

public abstract class MicroblockItem extends TemplateItem implements EnhancedPlacementContextItem {
    public MicroblockItem(Identifier identifier) {
        super(identifier);
    }

    public static Block getBlock(ItemStack stack) {
        if(stack.getStationNbt().contains("blockId")){
            return BlockRegistry.INSTANCE.get(Identifier.of(stack.getStationNbt().getString("blockId")));
        }
        return Block.STONE;
    }

    public static int getSize(ItemStack stack) {
        if(stack.getStationNbt().contains("size")){
            stack.getStationNbt().getInt("size");
        }
        return 1;
    }

    public static ItemStack setBlock(ItemStack stack, Block block) {
        Identifier blockId = BlockRegistry.INSTANCE.getId(block);
        if(blockId != null) {
            stack.getStationNbt().putString("blockId", blockId.toString());
        }
        return stack;
    }

    public static ItemStack setSize(ItemStack stack, int size) {
        stack.getStationNbt().putInt("size", size);
        return stack;
    }


}
