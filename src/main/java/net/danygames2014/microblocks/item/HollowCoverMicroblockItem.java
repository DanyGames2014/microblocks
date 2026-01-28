package net.danygames2014.microblocks.item;

import net.minecraft.block.Block;
import net.modificationstation.stationapi.api.util.Identifier;

public class HollowCoverMicroblockItem extends HollowFaceMicroblockItem {
    public HollowCoverMicroblockItem(Identifier identifier, Block block) {
        super(identifier, block);
    }

    @Override
    public int getSize() {
        return 2;
    }

    @Override
    public String getTypeTranslationKey() {
        return "microblock.microblocks.hollow_cover.name";
    }
}
