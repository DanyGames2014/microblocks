package net.danygames2014.microblocks.item;

import net.danygames2014.microblocks.item.base.FaceMicroblockItem;
import net.minecraft.block.Block;
import net.modificationstation.stationapi.api.util.Identifier;

public class PanelMicroblockItem extends FaceMicroblockItem {
    public PanelMicroblockItem(Identifier identifier, Block block, int meta) {
        super(identifier, block, meta);
    }

    @Override
    public int getSize() {
        return 4;
    }

    @Override
    public String getTypeTranslationKey() {
        return "microblock.microblocks.panel.name";
    }
    @Override
    public MicroblockItemType getType() {
        return MicroblockItemType.PANEL;
    }
}
