package net.danygames2014.microblocks.multipart;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.danygames2014.microblocks.item.MicroblockItemType;
import net.danygames2014.microblocks.multipart.model.FaceMicroblockModel;
import net.danygames2014.microblocks.multipart.model.MicroblockModel;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class FaceMicroblockMultipartComponent extends MicroblockMultipartComponent{

    public static final FaceMicroblockModel MODEL = new FaceMicroblockModel();

    public FaceMicroblockMultipartComponent(){}

    public FaceMicroblockMultipartComponent(Block block, int meta, PlacementSlot slot, int size) {
        super(block, meta, slot, size);
    }

    @Override
    public int getMaxSize() {
        return 16;
    }

    @Override
    public ObjectArrayList<ItemStack> getDropList() {
        ObjectArrayList<ItemStack> drops = new ObjectArrayList<>();

        int sizeToDrop = this.getSize();
        while (sizeToDrop > 1) {
            if (sizeToDrop >= 8) {
                drops.add(createStack(MicroblockItemType.SLAB));
                sizeToDrop -= 8;
            } else if (sizeToDrop >= 4) {
                drops.add(createStack(MicroblockItemType.PANEL));
                sizeToDrop -= 4;
            } else {
                drops.add(createStack(MicroblockItemType.COVER));
                sizeToDrop -= 2;
            }
        }

        return drops;
    }

    @Override
    public MicroblockItemType getClosestItemType() {
        return switch (this.getSize()) {
            case 1, 2, 3 -> MicroblockItemType.COVER;
            case 4, 5, 6, 7 -> MicroblockItemType.PANEL;
            default -> MicroblockItemType.SLAB;
        };
    }

    @Override
    public MicroblockModel getMicroblockModel() {
        return MODEL;
    }
}
