package net.danygames2014.microblocks.item.base;

import net.danygames2014.nyalib.item.HasCraftingReturnStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class HandSawItem extends TemplateItem implements HasCraftingReturnStack {
    public int durability;

    public HandSawItem(Identifier identifier, int durability) {
        super(identifier);
        this.durability = durability;
        setMaxCount(1);
        setHasSubtypes(false);
        setMaxDamage(durability);
        this.setCraftingReturnItem(this);
    }

    @Override
    public ItemStack getCraftingReturnStack(ItemStack stack) {
        stack.setDamage(stack.getDamage() + 1);
        if (stack.getDamage() >= stack.getMaxDamage()) {
            return null;
        }
        return stack;
    }
}
