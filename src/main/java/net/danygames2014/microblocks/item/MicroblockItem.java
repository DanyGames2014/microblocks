package net.danygames2014.microblocks.item;

import net.danygames2014.nyalib.item.EnhancedPlacementContextItem;
import net.minecraft.block.Block;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.client.item.CustomTooltipProvider;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;
import org.jetbrains.annotations.NotNull;

public abstract class MicroblockItem extends TemplateItem implements EnhancedPlacementContextItem {
    public Block block;

    public MicroblockItem(Identifier identifier, Block block) {
        super(identifier);
        this.block = block;
    }

    public static int getSize(ItemStack stack) {
        if (stack.getStationNbt().contains("size")) {
            stack.getStationNbt().getInt("size");
        }
        return 2;
    }

    public static ItemStack setSize(ItemStack stack, int size) {
        stack.getStationNbt().putInt("size", size);
        return stack;
    }

    public String getTypeTranslationKey() {
        return "microblock.microblocks.invalid.name";
    }
}
