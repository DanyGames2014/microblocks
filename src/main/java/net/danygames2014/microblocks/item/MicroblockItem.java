package net.danygames2014.microblocks.item;

import net.danygames2014.nyalib.item.EnhancedPlacementContextItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
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

    @Environment(EnvType.CLIENT)
    public abstract void renderGrid(PlayerEntity player, int blockX, int blockY, int blockZ, Vec3d hit, Direction face, float tickDelta);

    public abstract int getSize();

    public abstract String getTypeTranslationKey();
}
