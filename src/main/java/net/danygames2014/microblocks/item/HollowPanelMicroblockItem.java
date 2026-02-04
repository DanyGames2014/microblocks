package net.danygames2014.microblocks.item;

import net.danygames2014.microblocks.item.base.HollowFaceMicroblockItem;
import net.minecraft.block.Block;
import net.modificationstation.stationapi.api.util.Identifier;

public class HollowPanelMicroblockItem extends HollowFaceMicroblockItem {
    public HollowPanelMicroblockItem(Identifier identifier, Block block, int meta) {
        super(identifier, block, meta);
    }

    @Override
    public int getSize() {
        return 4;
    }

    @Override
    public String getTypeTranslationKey() {
        return "microblock.microblocks.hollow_panel.name";
    }
}
