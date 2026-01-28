package net.danygames2014.microblocks.item;

import net.danygames2014.nyalib.item.EnhancedPlacementContextItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
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

    public static int getSize(ItemStack stack) {
        if (stack.getStationNbt().contains("size")) {
            stack.getStationNbt().getInt("size");
        }
        return 2;
    }

    public static ItemStack setSize(ItemStack stack, int size) {
        stack.getStationNbt().putInt("size", size);
        return stack;
    }

    @Environment(EnvType.CLIENT)
    public abstract void renderGrid(PlayerEntity player, int blockX, int blockY, int blockZ, Vec3d hit, Direction face, float tickDelta);

    public String getTypeTranslationKey() {
        return "microblock.microblocks.invalid.name";
    }
}
