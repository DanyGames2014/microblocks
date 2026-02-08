package net.danygames2014.microblocks.item;

import net.danygames2014.microblocks.item.base.CornerMicroblockItem;
import net.minecraft.block.Block;
import net.modificationstation.stationapi.api.util.Identifier;

public class SlabCornerMicroblockItem extends CornerMicroblockItem {
    public SlabCornerMicroblockItem(Identifier identifier, Block block, int meta) {
        super(identifier, block, meta);
    }

    @Override
    public int getSize() {
        return 8;
    }

    @Override
    public String getTypeTranslationKey() {
        return "microblock.microblocks.slab_corner.name";
    }
    @Override
    public MicroblockItemType getType() {
        return MicroblockItemType.SLAB_CORNER;
    }
}
