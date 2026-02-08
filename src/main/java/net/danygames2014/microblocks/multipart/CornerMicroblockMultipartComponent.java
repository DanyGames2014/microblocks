package net.danygames2014.microblocks.multipart;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.danygames2014.microblocks.item.MicroblockItemType;
import net.danygames2014.microblocks.multipart.model.CornerMicroblockModel;
import net.danygames2014.microblocks.multipart.model.MicroblockModel;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import javax.swing.*;

public class CornerMicroblockMultipartComponent extends MicroblockMultipartComponent{
    public static final CornerMicroblockModel MODEL = new CornerMicroblockModel();

    public CornerMicroblockMultipartComponent(){}

    public CornerMicroblockMultipartComponent(Block block, int meta, PlacementSlot slot, int size) {
        super(block, meta, slot, size);
    }

    @Override
    public int getMaxSize() {
        return 14;
    }

    @Override
    public ObjectArrayList<ItemStack> getDropList() {
        ObjectArrayList<ItemStack> drops = new ObjectArrayList<>();
        
        int sizeToDrop = this.getSize();
        while (sizeToDrop > 1) {
            if (sizeToDrop >= 8) {
                drops.add(createStack(MicroblockItemType.SLAB_CORNER));
                sizeToDrop -= 8;
            } else if (sizeToDrop >= 4) {
                drops.add(createStack(MicroblockItemType.PANEL_CORNER));
                sizeToDrop -= 4;
            } else {
                drops.add(createStack(MicroblockItemType.CORNER));
                sizeToDrop -= 2;
            }
        }
        
        return drops;
    }

    @Override
    public MicroblockItemType getClosestItemType() {
        return switch (this.getSize()) {
            case 1, 2, 3 -> MicroblockItemType.CORNER;
            case 4, 5, 6, 7 -> MicroblockItemType.PANEL_CORNER;
            default -> MicroblockItemType.SLAB_CORNER;
        };
    }

    @Override
    public MicroblockModel getMicroblockModel() {
        return MODEL;
    }
}
