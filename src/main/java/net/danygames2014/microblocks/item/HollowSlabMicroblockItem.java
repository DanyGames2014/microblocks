package net.danygames2014.microblocks.item;

import net.minecraft.block.Block;
import net.modificationstation.stationapi.api.util.Identifier;

public class HollowSlabMicroblockItem extends HollowFaceMicroblockItem {
    public HollowSlabMicroblockItem(Identifier identifier, Block block) {
        super(identifier, block);
    }

    @Override
    public int getSize() {
        return 8;
    }

    @Override
    public String getTypeTranslationKey() {
        return "microblock.microblocks.hollow_slab.name";
    }
}
