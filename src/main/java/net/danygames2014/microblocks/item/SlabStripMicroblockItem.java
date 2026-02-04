package net.danygames2014.microblocks.item;

import net.danygames2014.microblocks.item.base.EdgeMicroblockItem;
import net.minecraft.block.Block;
import net.modificationstation.stationapi.api.util.Identifier;

public class SlabStripMicroblockItem extends EdgeMicroblockItem {
    public SlabStripMicroblockItem(Identifier identifier, Block block, int meta) {
        super(identifier, block, meta);
    }

    @Override
    public int getSize() {
        return 8;
    }

    @Override
    public String getTypeTranslationKey() {
        return "microblock.microblocks.slab_strip.name";
    }
}
