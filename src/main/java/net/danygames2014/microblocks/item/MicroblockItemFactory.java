package net.danygames2014.microblocks.item;

import net.danygames2014.microblocks.item.base.MicroblockItem;
import net.minecraft.block.Block;
import net.modificationstation.stationapi.api.util.Identifier;

public interface MicroblockItemFactory {
    MicroblockItem create(Identifier identifier, Block block, int meta);
}
