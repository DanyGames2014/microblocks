package net.danygames2014.microblocks.item.base;

import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class HandSawItem extends TemplateItem {
    public int durability;

    public HandSawItem(Identifier identifier, int durability) {
        super(identifier);
        this.durability = durability;
        setMaxCount(1);
        setHasSubtypes(false);
        setMaxDamage(durability);
        this.setCraftingReturnItem(this);
    }
}
