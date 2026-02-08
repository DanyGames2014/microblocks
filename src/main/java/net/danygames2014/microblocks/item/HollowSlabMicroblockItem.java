package net.danygames2014.microblocks.item;

import net.danygames2014.microblocks.item.base.HollowFaceMicroblockItem;
import net.minecraft.block.Block;
import net.modificationstation.stationapi.api.util.Identifier;

public class HollowSlabMicroblockItem extends HollowFaceMicroblockItem {
    public HollowSlabMicroblockItem(Identifier identifier, Block block, int meta) {
        super(identifier, block, meta);
    }

    @Override
    public int getSize() {
        return 8;
    }

    @Override
    public String getTypeTranslationKey() {
        return "microblock.microblocks.hollow_slab.name";
    }

    @Override
    public MicroblockItemType getType() {
        return MicroblockItemType.HOLLOW_SLAB;
    }
}
