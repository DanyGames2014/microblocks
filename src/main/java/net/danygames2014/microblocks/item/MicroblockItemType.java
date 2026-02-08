package net.danygames2014.microblocks.item;

import net.modificationstation.stationapi.api.util.Identifier;

public enum MicroblockItemType {
    COVER("cover", CoverMicroblockItem::new),
    PANEL("panel", PanelMicroblockItem::new),
    SLAB("slab", SlabMicroblockItem::new),
    HOLLOW_COVER("hollow_cover", HollowCoverMicroblockItem::new),
    HOLLOW_PANEL("hollow_panel", HollowPanelMicroblockItem::new),
    HOLLOW_SLAB("hollow_slab", HollowSlabMicroblockItem::new),
    CORNER("corner", StripCornerMicroblockItem::new),
    PANEL_CORNER("panel_corner", PanelCornerMicroblockItem::new),
    SLAB_CORNER("slab_corner", SlabCornerMicroblockItem::new),
    STRIP("strip", StripMicroblockItem::new),
    PANEL_STRIP("panel_strip", PanelStripMicroblockItem::new),
    SLAB_STRIP("slab_strip", SlabStripMicroblockItem::new),;

    public final String microblockType;
    public final MicroblockItemFactory factory;
    
    MicroblockItemType(String microblockType, MicroblockItemFactory factory) {
        this.microblockType = microblockType;
        this.factory = factory;
    }
    
    public String constructIdentifier(Identifier blockIdentifier, int meta) {
        return blockIdentifier.namespace + "_" + blockIdentifier.path + "_" + microblockType + "_" + meta;
    }

    public boolean isFace() {
        return this == COVER || this == PANEL || this == SLAB;
    }

    public boolean isHollowFace() {
        return this == HOLLOW_COVER || this == HOLLOW_PANEL || this == HOLLOW_SLAB;
    }

    public boolean isStrip() {
        return this == STRIP || this == PANEL_STRIP || this == SLAB_STRIP;
    }
    
    public boolean isCorner() {
        return this == CORNER || this == PANEL_CORNER || this == SLAB_CORNER;
    }
}
