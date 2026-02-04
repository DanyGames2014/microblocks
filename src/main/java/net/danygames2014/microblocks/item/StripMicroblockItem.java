package net.danygames2014.microblocks.item;

import net.danygames2014.microblocks.item.base.EdgeMicroblockItem;
import net.minecraft.block.Block;
import net.modificationstation.stationapi.api.util.Identifier;

public class StripMicroblockItem extends EdgeMicroblockItem {
    public StripMicroblockItem(Identifier identifier, Block block, int meta) {
        super(identifier, block, meta);
    }

    @Override
    public int getSize() {
        return 2;
    }

    @Override
    public String getTypeTranslationKey() {
        return "microblock.microblocks.strip.name";
    }
}
