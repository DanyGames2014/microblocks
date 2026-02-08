package net.danygames2014.microblocks.item;

import net.danygames2014.microblocks.item.base.FaceMicroblockItem;
import net.minecraft.block.Block;
import net.modificationstation.stationapi.api.util.Identifier;

public class SlabMicroblockItem extends FaceMicroblockItem {
    public SlabMicroblockItem(Identifier identifier, Block block, int meta) {
        super(identifier, block, meta);
    }

    @Override
    public int getSize() {
        return 8;
    }

    @Override
    public String getTypeTranslationKey() {
        return "microblock.microblocks.slab.name";
    }
    @Override
    public MicroblockItemType getType() {
        return MicroblockItemType.SLAB;
    }
}
