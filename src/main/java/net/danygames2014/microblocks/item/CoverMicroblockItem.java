package net.danygames2014.microblocks.item;

import net.danygames2014.microblocks.item.base.FaceMicroblockItem;
import net.minecraft.block.Block;
import net.modificationstation.stationapi.api.util.Identifier;

public class CoverMicroblockItem extends FaceMicroblockItem {
    public CoverMicroblockItem(Identifier identifier, Block block) {
        super(identifier, block);
    }

    @Override
    public int getSize() {
        return 2;
    }

    @Override
    public String getTypeTranslationKey() {
        return "microblock.microblocks.cover.name";
    }
}
