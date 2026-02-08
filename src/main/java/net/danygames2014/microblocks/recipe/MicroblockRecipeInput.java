package net.danygames2014.microblocks.recipe;

import net.danygames2014.microblocks.item.*;
import net.danygames2014.microblocks.item.base.HandSawItem;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public enum MicroblockRecipeInput {
    SAW,
    BLOCK,
    COVER,
    PANEL,
    SLAB,
    HOLLOW_COVER,
    HOLLOW_PANEL,
    HOLLOW_SLAB,
    CORNER,
    PANEL_CORNER,
    SLAB_CORNER,
    STRIP,
    PANEL_STRIP,
    SLAB_STRIP;
    
    public static MicroblockRecipeInput fromItem(Item item) {
        if (item instanceof HandSawItem) {
            return SAW;
        } else if (item instanceof BlockItem blockItem && blockItem.getBlock() instanceof Block) {
            return BLOCK;
        } else if (item instanceof CoverMicroblockItem) {
            return COVER;
        } else if (item instanceof PanelMicroblockItem) {
            return PANEL;
        } else if (item instanceof SlabMicroblockItem) {
            return SLAB;
        } else if (item instanceof HollowCoverMicroblockItem) {
            return HOLLOW_COVER;
        } else if (item instanceof HollowPanelMicroblockItem) {
            return HOLLOW_PANEL;
        } else if (item instanceof HollowSlabMicroblockItem) {
            return HOLLOW_SLAB;
        } else if (item instanceof StripCornerMicroblockItem) {
            return CORNER;
        } else if (item instanceof PanelCornerMicroblockItem) {
            return PANEL_CORNER;
        } else if (item instanceof SlabCornerMicroblockItem) {
            return SLAB_CORNER;
        } else if (item instanceof StripMicroblockItem) {
            return STRIP;
        } else if (item instanceof PanelStripMicroblockItem) {
            return PANEL_STRIP;
        } else if (item instanceof SlabStripMicroblockItem) {
            return SLAB_STRIP;
        }

        return null;
    }
    
    public static MicroblockRecipeInput fromMicroblockItemType(MicroblockItemType type) {
        return switch (type) {
            case COVER -> COVER;
            case PANEL -> PANEL;
            case SLAB -> SLAB;
            case HOLLOW_COVER -> HOLLOW_COVER;
            case HOLLOW_PANEL -> HOLLOW_PANEL;
            case HOLLOW_SLAB -> HOLLOW_SLAB;
            case CORNER -> CORNER;
            case PANEL_CORNER -> PANEL_CORNER;
            case SLAB_CORNER -> SLAB_CORNER;
            case STRIP -> STRIP;
            case PANEL_STRIP -> PANEL_STRIP;
            case SLAB_STRIP -> SLAB_STRIP;
        };
    }
}
