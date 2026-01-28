package net.danygames2014.microblocks.item;

import net.minecraft.block.Block;
import net.modificationstation.stationapi.api.util.Identifier;

public class PanelCornerMicroblockItem extends CornerMicroblockItem {
    public PanelCornerMicroblockItem(Identifier identifier, Block block) {
        super(identifier, block);
    }

    @Override
    public int getSize() {
        return 4;
    }

    @Override
    public String getTypeTranslationKey() {
        return "microblock.microblocks.panel_corner.name";
    }
}
